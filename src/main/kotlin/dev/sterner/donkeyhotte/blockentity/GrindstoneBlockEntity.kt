package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import dev.sterner.donkeyhotte.registry.DonkeyRecipeTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.SingleRecipeInput
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
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
        val recipes = level.recipeManager.getAllRecipesFor(DonkeyRecipeTypes.GRINDSTONE_RECIPE_TYPE.get())
        val foundRecipe = recipes.stream().filter { it.value.matches(SingleRecipeInput(this.items[0]), level) }.findFirst().orElse(null)
        if (foundRecipe != null) {
            donkeyBlockEntity.recipe = foundRecipe.value
            donkeyBlockEntity.setChanged()
        }

        TODO("Not yet implemented")
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return if (side == Direction.DOWN) outputSlot; else inputSlot
    }
}