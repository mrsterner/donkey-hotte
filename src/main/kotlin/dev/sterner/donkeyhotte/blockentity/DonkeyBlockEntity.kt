package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import net.fabricmc.fabric.api.networking.v1.PlayerLookup.world
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.SectionPos.*
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.animal.horse.AbstractHorse
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3


abstract class DonkeyBlockEntity(blockEntityType: BlockEntityType<*>, blockPos: BlockPos, blockState: BlockState) : BlockEntity(blockEntityType,
    blockPos, blockState
) {

    var recipe: DonkeyProcessingRecipe? = null
    var processingTime: Int = 0
    var oldAngle: Double = 0.0
    var angle: Double = 0.0

    var genericTicker: Int = 0
    var initialized: Boolean = false
    var refreshRecipe: Boolean = false

    var connectedEntity: PathfinderMob? = null
    var connectedEntityId: Int? = null
    var running = false
    var wasRunning = false
    var origin: Int = -1
    var target: Int = origin
    var searchAreas: Array<AABB?> = arrayOfNulls<AABB>(8)
    var searchPos: List<BlockPos>? = null
    var valid: Boolean = false
    var validationTimer: Int = 0
    var locateHorseTimer: Int = 0

    var path: Array<DoubleArray> = arrayOf(
        doubleArrayOf(-1.5, -1.5),
        doubleArrayOf(0.0, -1.5),
        doubleArrayOf(1.0, -1.5),
        doubleArrayOf(1.0, 0.0),
        doubleArrayOf(1.0, 1.0),
        doubleArrayOf(0.0, 1.0),
        doubleArrayOf(-1.5, 1.0),
        doubleArrayOf(-1.5, 0.0)
    )

    fun hasHorse() : Boolean {
        return connectedEntity != null
    }

    open fun tick(level: Level, pos: BlockPos, state: BlockState) {
        if (!initialized) {
            initialized = true
            if (recipe == null) {
                recipe = checkForRecipe(level, this)
            }
        }

        if (recipe == null && refreshRecipe) {
            recipe = checkForRecipe(level, this)
            refreshRecipe = false
        }

        tickHorse()
        /*
        testcode
         */
        connectedEntity = EntityType.DONKEY.create(level)
        if (genericTicker < 20) {
            genericTicker++
            refreshRecipe = true
        } else {
            genericTicker = 0
        }

        if (connectedEntity != null && recipe != null) {
            process(this, level, pos, state, connectedEntity!!, recipe!!)
        }
        if (connectedEntity == null || recipe == null) {
            processingTime = 0
        }
    }

    fun setHorse(entity: PathfinderMob){
        connectedEntity = entity
        connectedEntityId = entity.id
        setChanged()
    }

    private fun tickHorse() {
        validationTimer--
        if (validationTimer <= 0) {
            valid = validateArea()
            if (valid) validationTimer = 220
            else validationTimer = 60
        }
        var flag = false

        if (!hasHorse()) locateHorseTimer--
        if (!hasHorse() && connectedEntityId != null && locateHorseTimer <= 0) {
            flag = findWorker()
        }
        if (locateHorseTimer <= 0) locateHorseTimer = 120

        if (!level!!.isClientSide && valid) {
            if (!running && canWork()) {
                running = true
            } else if (running && !canWork()) {
                running = false
            }

            if (running != wasRunning) {
                target = getClosestTarget()
                wasRunning = running
            }

            if (hasHorse()) {
                if (running) {
                    var pos: Vec3 = getPathPosition(target)
                    var x: Double = pos.x
                    var y: Double = pos.y
                    var z: Double = pos.z

                    if (searchAreas[target] == null) searchAreas[target] =
                        AABB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5)

                    if (connectedEntity!!.boundingBox.intersects(searchAreas[target]!!)) {
                        var next = target + 1
                        var previous = target - 1
                        if (next >= path.size) next = 0
                        if (previous < 0) previous = path.size - 1

                        if (origin !== target && target != previous) {
                            origin = target
                            flag = targetReached()
                        }
                        target = next
                    }

                    if (connectedEntity is AbstractHorse && (connectedEntity as AbstractHorse).isEating) {
                        (connectedEntity as AbstractHorse).isEating = false
                    }

                    if (target != -1 && connectedEntity!!.navigation.path == null) {
                        pos = getPathPosition(target)
                        x = pos.x
                        y = pos.y
                        z = pos.z

                        connectedEntity!!.navigation.moveTo(x, y, z, 1.0)
                    }
                }
            }
        }

        if (flag) {
            setChanged()
        }
    }

    abstract fun canWork(): Boolean

    abstract fun findWorker(): Boolean

    abstract fun validateArea(): Boolean

    private fun getPathPosition(i: Int): Vec3 {
        val x: Double = blockPos.x + path[i][0] * 2
        val y: Double = blockPos.y + getPositionOffset().toDouble()
        val z: Double = blockPos.z + path[i][1] * 2
        return Vec3(x, y, z)
    }

    private fun getClosestTarget(): Int {
        if (hasHorse()) {
            var dist = Double.MAX_VALUE
            var closest = 0

            for (i in path.indices) {
                val pos: Vec3 = getPathPosition(i)
                val x: Double = pos.x
                val y: Double = pos.y
                val z: Double = pos.z

                val tmp: Double = connectedEntity!!.distanceToSqr(x, y, z)
                if (tmp < dist) {
                    dist = tmp
                    closest = i
                }
            }

            return closest
        }
        return 0
    }

    abstract fun targetReached(): Boolean

    abstract fun getPositionOffset(): Int

    abstract fun process(be: DonkeyBlockEntity, level: Level, pos: BlockPos, state: BlockState, connectedEntity: PathfinderMob, recipe: DonkeyProcessingRecipe)

    abstract fun checkForRecipe(level: Level, donkeyBlockEntity: DonkeyBlockEntity): DonkeyProcessingRecipe?

    fun onUse(player: Player?, hand: BlockHitResult): InteractionResult {
        return InteractionResult.PASS
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        tag.putInt("ProcessingTime", processingTime)
        tag.putDouble("OldAngle", oldAngle)
        tag.putDouble("Angle", angle)

        //Horse
        if (connectedEntityId != null) {
            tag.putInt("EntityId", connectedEntityId!!)
        }

        super.saveAdditional(tag, registries)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        processingTime = tag.getInt("ProcessingTime")
        oldAngle = tag.getDouble("OldAngle")
        angle = tag.getDouble("Angle")

        if (tag.contains("EntityId")) {
            connectedEntityId = tag.getInt("EntityId")
        }

        super.loadAdditional(tag, registries)
    }


    //Sync
    override fun getUpdatePacket(): Packet<ClientGamePacketListener>? {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    private fun sendData() {
        if (level is ServerLevel) level!!.blockEntityChanged(blockPos)
    }

    override fun setChanged() {
        sendData()
        super.setChanged()
    }
}