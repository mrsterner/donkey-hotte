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

class DonkeyRecipeBuilder<T : DonkeyProcessingRecipe>(
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
        ): DonkeyRecipeBuilder<GrindstoneRecipe> {
            return DonkeyRecipeBuilder(ingredient, result, extra, processingTime) { ingredient2, result2, extra2, processingTime2 ->
                GrindstoneRecipe(ingredient2!!, result2!!, extra2!!, processingTime2)
            }
        }

        fun chopper(
            ingredient: Ingredient,
            result: ItemStackWithChance,
            extra: ItemStackWithChance,
            processingTime: Int
        ): DonkeyRecipeBuilder<ChopperRecipe> {
            return DonkeyRecipeBuilder(ingredient, result, extra, processingTime) { ingredient2, result2, extra2, processingTime2 ->
                ChopperRecipe(ingredient2!!, result2!!, extra2!!, processingTime2)
            }
        }
    }

    override fun unlockedBy(name: String, criterion: Criterion<*>): DonkeyRecipeBuilder<T> {
        this.criteria[name] = criterion
        return this
    }

    override fun group(@Nullable groupName: String?): DonkeyRecipeBuilder<T> {
        return this
    }

    override fun getResult(): Item {
        return this.result.stack.item
    }

    override fun save(recipeOutput: RecipeOutput, id: ResourceLocation) {
        //ensureValid(id)
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