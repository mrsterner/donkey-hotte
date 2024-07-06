package dev.sterner.donkeyhotte.recipe

import com.mojang.serialization.MapCodec
import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.registry.DonkeyRecipeSerializers
import dev.sterner.donkeyhotte.registry.DonkeyRecipeTypes
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class GrindstoneRecipe(ingredient: Ingredient, output: ItemStack, extraOutput: ItemStack, processingTime: Int) : DonkeyProcessingRecipe(ingredient, output, extraOutput, processingTime) {

    companion object {
        const val NAME: String = "grindstone"
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return DonkeyRecipeSerializers.GRINDSTONE_SERIALIZER.get()
    }

    override fun getType(): RecipeType<*> {
        return DonkeyRecipeTypes.GRINDSTONE_RECIPE_TYPE.get()
    }
}