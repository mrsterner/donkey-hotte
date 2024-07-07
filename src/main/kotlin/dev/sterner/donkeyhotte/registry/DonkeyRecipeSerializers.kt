package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.recipe.ChopperRecipe
import dev.sterner.donkeyhotte.recipe.ChopperSerializer
import dev.sterner.donkeyhotte.recipe.GrindstoneRecipe
import dev.sterner.donkeyhotte.recipe.GrindstoneSerializer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.RecipeSerializer

object DonkeyRecipeSerializers {
    val RECIPE_SERIALIZERS: LazyRegistrar<RecipeSerializer<*>> = LazyRegistrar.create(BuiltInRegistries.RECIPE_SERIALIZER, Donkeyhotte.MOD_ID)

    val GRINDSTONE_SERIALIZER: RegistryObject<GrindstoneSerializer> = RECIPE_SERIALIZERS.register(GrindstoneRecipe.NAME) {
        GrindstoneSerializer { ingredient, output, extraOutput, processingTime ->
            GrindstoneRecipe(ingredient!!, output!!, extraOutput!!, processingTime)
        }
    }

    val CHOPPER_SERIALIZER: RegistryObject<ChopperSerializer> = RECIPE_SERIALIZERS.register(ChopperRecipe.NAME) {
        ChopperSerializer { ingredient, output, extraOutput, processingTime ->
            ChopperRecipe(ingredient!!, output!!, extraOutput!!, processingTime)
        }
    }
}