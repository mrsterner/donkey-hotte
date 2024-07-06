package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class GrindstoneBlockEntity(blockPos: BlockPos, blockState: BlockState
) : DonkeyContainerBlockEntity(DonkeyBlockEntityTypes.GRINDSTONE_BLOCK_ENTITY.get(), blockPos, blockState) {

    private var items: NonNullList<ItemStack> = NonNullList.withSize(4, ItemStack.EMPTY)

    override fun getItems(): NonNullList<ItemStack> {
        return items
    }

    override fun setItems(nonNullList: NonNullList<ItemStack>?) {
        items = nonNullList!!
    }

    override fun process(
        level: Level,
        pos: BlockPos,
        state: BlockState,
        connectedEntity: PathfinderMob,
        recipe: DonkeyProcessingRecipe
    ) {
        val targetProcessingTime = recipe.processingTime
        val output: ItemStackWithChance = recipe.output
        val extra: ItemStackWithChance = recipe.extraOutput
        if (output.chance > 0) {
            processingTime++
            if (processingTime >= targetProcessingTime) {
                processingTime = 0

            }
        }
    }

    override fun checkForRecipe(level: Level, donkeyBlockEntity: DonkeyBlockEntity): DonkeyProcessingRecipe? {
        TODO("Not yet implemented")
    }
}