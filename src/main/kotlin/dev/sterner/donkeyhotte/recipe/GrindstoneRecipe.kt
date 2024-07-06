package dev.sterner.donkeyhotte.recipe

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import dev.sterner.donkeyhotte.registry.DonkeyRecipeSerializers
import dev.sterner.donkeyhotte.registry.DonkeyRecipeTypes
import net.minecraft.world.item.crafting.*

class GrindstoneRecipe(ingredient: Ingredient, output: ItemStackWithChance, extraOutput: ItemStackWithChance, processingTime: Int) : DonkeyProcessingRecipe(ingredient, output, extraOutput, processingTime) {

    companion object {
        const val NAME: String = "grindstone"
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return DonkeyRecipeSerializers.GRINDSTONE_SERIALIZER.get()
    }

    override fun getType(): RecipeType<*> {
        return DonkeyRecipeTypes.GRINDSTONE_RECIPE_TYPE.get()
    }

    fun create(ingredient: Ingredient?, itemStackWithChance: ItemStackWithChance?, itemStackWithChance1: ItemStackWithChance?, i: Int): DonkeyProcessingRecipe {
        return GrindstoneRecipe(ingredient!!, itemStackWithChance!!, itemStackWithChance1!!, i)
    }
}