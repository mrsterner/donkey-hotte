package dev.sterner.donkeyhotte.data

import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import dev.sterner.donkeyhotte.recipe.DonkeyRecipeBuilder
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import java.util.concurrent.CompletableFuture

class DonkeyRecipeProvider(output: FabricDataOutput?,
                           registriesFuture: CompletableFuture<HolderLookup.Provider>?
) : FabricRecipeProvider(output, registriesFuture) {

    override fun buildRecipes(exporter: RecipeOutput) {

        DonkeyRecipeBuilder.grindstone(
            Ingredient.of(Items.IRON_ORE),
            ItemStackWithChance(ItemStack(Items.IRON_INGOT), 1f),
            ItemStackWithChance(ItemStack(Items.IRON_INGOT), 1f),
            100
        ).save(exporter)
    }
}