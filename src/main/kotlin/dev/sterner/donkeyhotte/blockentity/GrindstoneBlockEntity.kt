package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import dev.sterner.donkeyhotte.registry.DonkeyRecipeTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.world.Containers
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

    override fun tick(level: Level, pos: BlockPos, state: BlockState) {
        super.tick(level, pos, state)
        println(items[0])
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

        if (output.chance > 0) {
            processingTime++
            println("ProcessTime: $processingTime")
            if (processingTime >= targetProcessingTime) {
                processingTime = 0

                if (recipe.matches(SingleRecipeInput(items[0]), level)) {
                    println("Matched")
                    items[0].shrink(recipe.ingredient.items[0].count)

                    if (level.random.nextFloat() < recipe.output.chance) {
                        Containers.dropItemStack(level, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), recipe.output.stack)
                    }
                    if (level.random.nextFloat() < recipe.extraOutput.chance) {
                        Containers.dropItemStack(level, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), recipe.extraOutput.stack)
                    }
                }

                refreshRecipe = true
                setChanged()
            }
        }
    }

    override fun checkForRecipe(level: Level, donkeyBlockEntity: DonkeyBlockEntity): DonkeyProcessingRecipe? {
        val recipes = level.recipeManager.getAllRecipesFor(DonkeyRecipeTypes.GRINDSTONE_RECIPE_TYPE.get())
        val foundRecipe = recipes.stream().filter { it.value.matches(SingleRecipeInput(this.items[0]), level) }.findFirst().orElse(null)
        //recipes.forEach { recipeHolder -> println(recipeHolder.value.ingredient) }
        println("AmountOfRecipes: ${recipes.size}")
        //println("Recipe: $foundRecipe")
        if (foundRecipe != null) {
            donkeyBlockEntity.setChanged()
            return  foundRecipe.value
        }
        return null
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return if (side == Direction.DOWN) outputSlot; else inputSlot
    }
}