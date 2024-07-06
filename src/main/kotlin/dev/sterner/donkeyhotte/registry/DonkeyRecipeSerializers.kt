package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.blockentity.GrindstoneBlockEntity
import dev.sterner.donkeyhotte.recipe.GrindstoneRecipe
import dev.sterner.donkeyhotte.recipe.GrindstoneSerializer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

object DonkeyRecipeSerializers {
    val RECIPE_SERIALIZERS = LazyRegistrar.create(BuiltInRegistries.RECIPE_SERIALIZER, Donkeyhotte.MOD_ID)

    val GRINDSTONE_SERIALIZER = RECIPE_SERIALIZERS.register(GrindstoneRecipe.NAME) {
        GrindstoneSerializer { ingredient, output, extraOutput, processingTime ->
            GrindstoneRecipe(ingredient!!, output!!, extraOutput!!, processingTime)
        }
    }
}