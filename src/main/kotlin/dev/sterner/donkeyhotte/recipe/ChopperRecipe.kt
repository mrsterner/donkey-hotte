package dev.sterner.donkeyhotte.recipe

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import dev.sterner.donkeyhotte.registry.DonkeyRecipeSerializers
import dev.sterner.donkeyhotte.registry.DonkeyRecipeTypes
import net.minecraft.world.item.crafting.*

class ChopperRecipe(ingredient: Ingredient, output: ItemStackWithChance, extraOutput: ItemStackWithChance, processingTime: Int) : DonkeyProcessingRecipe(ingredient, output, extraOutput, processingTime) {

    companion object {
        const val NAME: String = "chopper"
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return DonkeyRecipeSerializers.CHOPPER_SERIALIZER.get()
    }

    override fun getType(): RecipeType<*> {
        return DonkeyRecipeTypes.CHOPPER_RECIPE_TYPE.get()
    }
}