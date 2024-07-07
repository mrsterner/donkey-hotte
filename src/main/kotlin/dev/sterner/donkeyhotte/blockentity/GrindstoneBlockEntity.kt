package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import dev.sterner.donkeyhotte.registry.DonkeyRecipeTypes
import dev.sterner.donkeyhotte.util.VecUtils
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.core.particles.ItemParticleOption
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.Containers
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.SingleRecipeInput
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import java.util.stream.IntStream


class GrindstoneBlockEntity(blockPos: BlockPos, blockState: BlockState
) : DonkeyContainerBlockEntity(DonkeyBlockEntityTypes.GRINDSTONE_BLOCK_ENTITY.get(), blockPos, blockState) {

    private var items: NonNullList<ItemStack> = NonNullList.withSize(4, ItemStack.EMPTY)
    private var inputSlot: IntArray = IntStream.range(0, 1).toArray()
    private var outputSlot: IntArray = IntStream.range(2, 3).toArray()

    override fun getItems(): NonNullList<ItemStack> {
        return items
    }

    override fun setItems(nonNullList: NonNullList<ItemStack>?) {
        items = nonNullList!!
    }

    override fun tick(level: Level, pos: BlockPos, state: BlockState) {
        super.tick(level, pos, state)
        oldAngle = angle
        angle = (angle + 0.1) % 360
        setChanged()
    }

    override fun process(
        be: DonkeyBlockEntity,
        level: Level,
        pos: BlockPos,
        state: BlockState,
        connectedEntity: PathfinderMob,
        recipe: DonkeyProcessingRecipe
    ) {
        val targetProcessingTime = recipe.processingTime
        val output: ItemStackWithChance = recipe.output
        if (level is ServerLevel) {
            val server = level as ServerLevel
            spawnParticles(server, recipe.ingredient.items.get(0))
        }

        if (output.chance > 0) {
            processingTime++

            if (processingTime >= targetProcessingTime) {
                processingTime = 0

                if (recipe.matches(SingleRecipeInput(items[0]), level)) {
                    items[0].shrink(recipe.ingredient.items[0].count)

                    if (level.random.nextFloat() < recipe.output.chance) {
                        Containers.dropItemStack(level, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), recipe.output.stack)
                    }
                    if (level.random.nextFloat() < recipe.extraOutput.chance) {
                        Containers.dropItemStack(level, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), recipe.extraOutput.stack)
                    }
                }
                be.recipe = null
                refreshRecipe = true
                setChanged()
            }
        }
    }


    fun spawnParticles(level: ServerLevel, stackInSlot: ItemStack) {
        if (stackInSlot.isEmpty) return

        val data = ItemParticleOption(ParticleTypes.ITEM, stackInSlot)
        val angle = level.random.nextFloat() * 360
        var offset = Vec3(0.0, 0.0, 0.5)
        offset = VecUtils.rotate(offset, angle.toDouble(), Direction.Axis.Y)
        var target: Vec3 = VecUtils.rotate(offset, 25.0, Direction.Axis.Y)

        val center = offset.add(VecUtils.getCenterOf(worldPosition))
        target = VecUtils.offsetRandomly(target.subtract(offset), level.random, 1 / 128f)

        PlayerLookup.tracking(this).forEach {
            level.sendParticles(it, data, false, center.x, center.y, center.z, 1, target.x, target.y, target.z, 0.0)
        }
    }

    override fun checkForRecipe(level: Level, donkeyBlockEntity: DonkeyBlockEntity): DonkeyProcessingRecipe? {
        val recipes = level.recipeManager.getAllRecipesFor(DonkeyRecipeTypes.GRINDSTONE_RECIPE_TYPE.get())
        val foundRecipe = recipes.stream().filter { it.value.matches(SingleRecipeInput(this.items[0]), level) }.findFirst().orElse(null)
        if (foundRecipe != null) {
            donkeyBlockEntity.setChanged()
            return foundRecipe.value
        }
        return null
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return if (side == Direction.DOWN) outputSlot; else inputSlot
    }


}