package dev.sterner.donkeyhotte.data

import dev.sterner.donkeyhotte.recipe.DonkeyRecipeBuilder
import dev.sterner.donkeyhotte.recipe.GrindstoneRecipe
import dev.sterner.donkeyhotte.recipe.GrindstoneSerializer
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import java.util.concurrent.CompletableFuture

class DonkeyRecipeProvider(output: FabricDataOutput?,
                           registriesFuture: CompletableFuture<HolderLookup.Provider>?
) : FabricRecipeProvider(output, registriesFuture) {

    override fun buildRecipes(exporter: RecipeOutput) {
        //exporter.accept()

        DonkeyRecipeBuilder(
            Ingredient.of(Items.IRON_ORE),
            Items.FISHING_ROD,
            1,
            1f,
            Items.IRON_HOE,
            1,
            1f,
            100
        ) { ingredient, output, extraOutput, processingTime ->
            GrindstoneRecipe(
                ingredient!!,
                output!!,
                extraOutput!!,
                processingTime
            )
        }.save(exporter)


        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, Items.WARPED_FUNGUS_ON_A_STICK)
            .define('#', Items.FISHING_ROD)
            .define('X', Items.WARPED_FUNGUS)
            .pattern("#X")
            .pattern(" X")
            .unlockedBy("has_warped_fungus", has(Items.WARPED_FUNGUS))
            .save(exporter)
        //cookRecipes(recipeOutput, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600);
    }

    /*
    public static <T extends AbstractCookingRecipe> void simpleCookingRecipe(
		RecipeOutput recipeOutput,
		String cookingMethod,
		RecipeSerializer<T> cookingSerializer,
		AbstractCookingRecipe.Factory<T> recipeFactory,
		int cookingTime,
		ItemLike material,
		ItemLike result,
		float experience
	) {
		SimpleCookingRecipeBuilder.generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, cookingSerializer, recipeFactory)
			.unlockedBy(getHasName(material), has(material))
			.save(recipeOutput, getItemName(result) + "_from_" + cookingMethod);
	}
     */
}