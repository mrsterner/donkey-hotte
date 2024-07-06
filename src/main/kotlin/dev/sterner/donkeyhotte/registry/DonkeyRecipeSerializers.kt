package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.recipe.ChopperRecipe
import dev.sterner.donkeyhotte.recipe.ChopperSerializer
import dev.sterner.donkeyhotte.recipe.GrindstoneRecipe
import dev.sterner.donkeyhotte.recipe.GrindstoneSerializer
import net.minecraft.core.registries.BuiltInRegistries

object DonkeyRecipeSerializers {
    val RECIPE_SERIALIZERS = LazyRegistrar.create(BuiltInRegistries.RECIPE_SERIALIZER, Donkeyhotte.MOD_ID)

    val GRINDSTONE_SERIALIZER = RECIPE_SERIALIZERS.register(GrindstoneRecipe.NAME) {
        GrindstoneSerializer { ingredient, output, extraOutput, processingTime ->
            GrindstoneRecipe(ingredient!!, output!!, extraOutput!!, processingTime)
        }
    }

    val CHOPPER_SERIALIZER = RECIPE_SERIALIZERS.register(ChopperRecipe.NAME) {
        ChopperSerializer { ingredient, output, extraOutput, processingTime ->
            ChopperRecipe(ingredient!!, output!!, extraOutput!!, processingTime)
        }
    }
}