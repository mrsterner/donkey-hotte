package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.recipe.GrindstoneRecipe
import dev.sterner.donkeyhotte.recipe.GrindstoneSerializer
import net.minecraft.core.registries.BuiltInRegistries

object DonkeyRecipeSerializers {
    val RECIPE_SERIALIZERS = LazyRegistrar.create(BuiltInRegistries.RECIPE_SERIALIZER, Donkeyhotte.MOD_ID)

    val GRINDSTONE_SERIALIZER = RECIPE_SERIALIZERS.register(GrindstoneRecipe.NAME) {
        GrindstoneSerializer()
    }
}