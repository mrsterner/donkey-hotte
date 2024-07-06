package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.recipe.ChopperRecipe
import dev.sterner.donkeyhotte.recipe.GrindstoneRecipe
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType

object DonkeyRecipeTypes {
    val RECIPE_TYPES = LazyRegistrar.create(BuiltInRegistries.RECIPE_TYPE, Donkeyhotte.MOD_ID)

    val GRINDSTONE_RECIPE_TYPE = RECIPE_TYPES.register(GrindstoneRecipe.NAME) {
        registerRecipeType<GrindstoneRecipe>(GrindstoneRecipe.NAME)
    }

    val CHOPPER_RECIPE_TYPE = RECIPE_TYPES.register(ChopperRecipe.NAME) {
        registerRecipeType<ChopperRecipe>(ChopperRecipe.NAME)
    }

    private fun <T : Recipe<*>> registerRecipeType(identifier: String): RecipeType<T> {
        return object : RecipeType<T> {
            override fun toString(): String {
                return Donkeyhotte.MOD_ID + ":" + identifier
            }
        }
    }
}