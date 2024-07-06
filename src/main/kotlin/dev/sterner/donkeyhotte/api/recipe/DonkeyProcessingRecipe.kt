package dev.sterner.donkeyhotte.api.recipe

import net.minecraft.core.HolderLookup
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

abstract class DonkeyProcessingRecipe(val ingredient: Ingredient, val output: ItemStack, val extraOutput: ItemStack, val processingTime: Int) :
    Recipe<SingleRecipeInput> {

    override fun matches(recipeInput: SingleRecipeInput, level: Level): Boolean {
        return ingredient.test(recipeInput.item())
    }

    override fun assemble(recipeInput: SingleRecipeInput, provider: HolderLookup.Provider): ItemStack {
        return this.output.copy()
    }

    override fun canCraftInDimensions(i: Int, j: Int): Boolean {
        return true
    }

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack {
        return this.output
    }
}