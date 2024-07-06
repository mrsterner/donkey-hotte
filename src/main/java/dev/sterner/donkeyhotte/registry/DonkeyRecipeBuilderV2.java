package dev.sterner.donkeyhotte.registry;

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe;
import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingSerializer;
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance;
import dev.sterner.donkeyhotte.recipe.GrindstoneRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class DonkeyRecipeBuilderV2<T extends DonkeyProcessingRecipe> implements RecipeBuilder {

    private final ItemStackWithChance result;
    private final ItemStackWithChance extra;
    private final Ingredient ingredient;
    private final int processingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
    private final DonkeyProcessingSerializer.Factory<?> factory;

    private DonkeyRecipeBuilderV2(
            Ingredient ingredient,
            ItemStackWithChance result,
            ItemStackWithChance extra,
            int processingTime,
            DonkeyProcessingSerializer.Factory<?> factory
    ) {
        this.result = result;
        this.extra = extra;
        this.ingredient = ingredient;
        this.processingTime = processingTime;
        this.factory = factory;
    }

    public static DonkeyRecipeBuilderV2 grindstone(Ingredient ingredient, ItemStackWithChance result, ItemStackWithChance extra, int processingTime) {
        return new DonkeyRecipeBuilderV2(ingredient, result, extra, processingTime, GrindstoneRecipe::new);
    }

    public DonkeyRecipeBuilderV2 unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public DonkeyRecipeBuilderV2 group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.component1().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        DonkeyProcessingRecipe abstractCookingRecipe = this.factory
                .create(this.ingredient, this.result, this.extra, this.processingTime);
        recipeOutput.accept(id, abstractCookingRecipe, builder.build(id.withPrefix("recipes/")));
    }

    /*
     * Makes sure that this obtainable.
     */
    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}