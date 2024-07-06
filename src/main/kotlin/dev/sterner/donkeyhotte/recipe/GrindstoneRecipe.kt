package dev.sterner.donkeyhotte.recipe

import com.mojang.serialization.MapCodec
import dev.sterner.donkeyhotte.registry.DonkeyRecipeSerializers
import dev.sterner.donkeyhotte.registry.DonkeyRecipeTypes
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class GrindstoneRecipe(val id: ResourceLocation, val ingredient: Ingredient, val output: ItemStack, val extraOutput: ItemStack) : Recipe<SingleRecipeInput> {

    companion object {
        const val NAME: String = "grindstone"
    }

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

    override fun getSerializer(): RecipeSerializer<*> {
        return DonkeyRecipeSerializers.GRINDSTONE_SERIALIZER.get()
    }

    override fun getType(): RecipeType<*> {
        return DonkeyRecipeTypes.GRINDSTONE_RECIPE_TYPE.get()
    }
}