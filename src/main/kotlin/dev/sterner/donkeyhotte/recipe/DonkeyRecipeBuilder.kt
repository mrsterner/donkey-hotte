package dev.sterner.donkeyhotte.recipe

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingSerializer
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import org.jetbrains.annotations.Nullable

class DonkeyRecipeBuilderV2<T : DonkeyProcessingRecipe>(
    private val ingredient: Ingredient,
    private val result: ItemStackWithChance,
    private val extra: ItemStackWithChance,
    private val processingTime: Int,
    private val factory: DonkeyProcessingSerializer.Factory<*>
) : RecipeBuilder {

    private val criteria: MutableMap<String, Criterion<*>> = LinkedHashMap()

    companion object {
        fun grindstone(
            ingredient: Ingredient,
            result: ItemStackWithChance,
            extra: ItemStackWithChance,
            processingTime: Int
        ): DonkeyRecipeBuilderV2<GrindstoneRecipe> {
            return DonkeyRecipeBuilderV2(ingredient, result, extra, processingTime, GrindstoneRecipe::create)
        }
    }

    override fun unlockedBy(name: String, criterion: Criterion<*>): DonkeyRecipeBuilderV2<T> {
        this.criteria[name] = criterion
        return this
    }

    override fun group(@Nullable groupName: String?): DonkeyRecipeBuilderV2<T> {
        return this
    }

    override fun getResult(): Item {
        return this.result.stack.item
    }

    override fun save(recipeOutput: RecipeOutput, id: ResourceLocation) {
        ensureValid(id)
        val builder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR)
        criteria.forEach { (name, criterion) -> builder.addCriterion(name, criterion) }

        val abstractCookingRecipe = factory.create(ingredient, result, extra, processingTime)
        recipeOutput.accept(id, abstractCookingRecipe, builder.build(id.withPrefix("recipes/")))
    }

    /*
     * Makes sure that this obtainable.
     */
    private fun ensureValid(id: ResourceLocation) {
        if (criteria.isEmpty()) {
            throw IllegalStateException("No way of obtaining recipe $id")
        }
    }
}