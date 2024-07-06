package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult


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

    fun tick(level: Level, pos: BlockPos, state: BlockState) {
        if (!initialized) {
            initialized = true
            if (recipe == null) {
                recipe = checkForRecipe(level, this)
            }
        }

        if (refreshRecipe && recipe == null) {
            recipe = checkForRecipe(level, this)
        }
        /*
        testcode
         */
        connectedEntity = EntityType.DONKEY.create(level)

        if (connectedEntity != null && recipe != null) {
            process(level, pos, state, connectedEntity!!, recipe!!)
            println("Process")
        }
        if (connectedEntity == null || recipe == null) {
            processingTime = 0
        }
    }

    abstract fun process(level: Level, pos: BlockPos, state: BlockState, connectedEntity: PathfinderMob, recipe: DonkeyProcessingRecipe)

    abstract fun checkForRecipe(level: Level, donkeyBlockEntity: DonkeyBlockEntity): DonkeyProcessingRecipe?

    fun onUse(player: Player?, hand: BlockHitResult): InteractionResult {
        return InteractionResult.PASS
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        tag.putInt("ProcessingTime", processingTime)
        tag.putDouble("OldAngle", oldAngle)
        tag.putDouble("Angle", angle)
        super.saveAdditional(tag, registries)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        processingTime = tag.getInt("ProcessingTime")
        oldAngle = tag.getDouble("OldAngle")
        angle = tag.getDouble("Angle")
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