package dev.sterner.donkeyhotte.recipe

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingSerializer
import dev.sterner.donkeyhotte.api.recipe.ItemStackWithChance
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike

class DonkeyRecipeBuilder<T : DonkeyProcessingRecipe>(val ingredient: Ingredient, val result: Item, val resultCount: Int, val resultChance: Float, val extraResult: Item, val extraResultCount: Int, val extraResultChance: Float, val processingTime: Int, val factory: DonkeyProcessingSerializer.Factory<T>) : RecipeBuilder {

    private val criteria: MutableMap<String?, Criterion<*>?> = mutableMapOf()

    constructor(ingredient: Ingredient, stackWithChance: ItemStackWithChance, extraStackWithChance: ItemStackWithChance, proccessingTime: Int, factory: DonkeyProcessingSerializer.Factory<T>)
            : this(
        ingredient,
        stackWithChance.stack.item,
        stackWithChance.stack.count,
        stackWithChance.chance,
        extraStackWithChance.stack.item,
        extraStackWithChance.stack.count,
        extraStackWithChance.chance,
        proccessingTime,
                factory
            ) {
    }

    override fun unlockedBy(name: String, criterion: Criterion<*>): RecipeBuilder {
        criteria[name] = criterion
        return this
    }

    override fun group(groupName: String?): RecipeBuilder {
        return this
    }

    override fun getResult(): Item {
        return result
    }

    override fun save(recipeOutput: RecipeOutput, id: ResourceLocation) {

        val builder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(builder::addCriterion);

        val abstractCookingRecipe: DonkeyProcessingRecipe = factory.create(
            ingredient,
            ItemStackWithChance(ItemStack(result, resultCount), resultChance),
            ItemStackWithChance(ItemStack(extraResult, extraResultCount),
                extraResultChance
            ),
            processingTime
        )

        recipeOutput.accept(
            id,
            abstractCookingRecipe,
            builder.build(id.withPrefix("recipes/"))
        )
    }
}

/*
public class SimpleCookingRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final CookingBookCategory bookCategory;
	private final Item result;
	private final Ingredient ingredient;
	private final float experience;
	private final int cookingTime;
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
	@Nullable
	private String group;
	private final AbstractCookingRecipe.Factory<?> factory;

	private SimpleCookingRecipeBuilder(
		RecipeCategory category,
		CookingBookCategory bookCategory,
		ItemLike result,
		Ingredient ingredient,
		float experience,
		int cookingTime,
		AbstractCookingRecipe.Factory<?> factory
	) {
		this.category = category;
		this.bookCategory = bookCategory;
		this.result = result.asItem();
		this.ingredient = ingredient;
		this.experience = experience;
		this.cookingTime = cookingTime;
		this.factory = factory;
	}

	public static <T extends AbstractCookingRecipe> SimpleCookingRecipeBuilder generic(
		Ingredient ingredient,
		RecipeCategory category,
		ItemLike result,
		float experience,
		int cookingTime,
		RecipeSerializer<T> cookingSerializer,
		AbstractCookingRecipe.Factory<T> factory
	) {
		return new SimpleCookingRecipeBuilder(category, determineRecipeCategory(cookingSerializer, result), result, ingredient, experience, cookingTime, factory);
	}

	public static SimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime) {
		return new SimpleCookingRecipeBuilder(category, CookingBookCategory.FOOD, result, ingredient, experience, cookingTime, CampfireCookingRecipe::new);
	}

	public static SimpleCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime) {
		return new SimpleCookingRecipeBuilder(category, determineBlastingRecipeCategory(result), result, ingredient, experience, cookingTime, BlastingRecipe::new);
	}

	public static SimpleCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime) {
		return new SimpleCookingRecipeBuilder(category, determineSmeltingRecipeCategory(result), result, ingredient, experience, cookingTime, SmeltingRecipe::new);
	}

	public static SimpleCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime) {
		return new SimpleCookingRecipeBuilder(category, CookingBookCategory.FOOD, result, ingredient, experience, cookingTime, SmokingRecipe::new);
	}

	public SimpleCookingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.criteria.put(name, criterion);
		return this;
	}

	public SimpleCookingRecipeBuilder group(@Nullable String groupName) {
		this.group = groupName;
		return this;
	}

	@Override
	public Item getResult() {
		return this.result;
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		this.ensureValid(id);
		Advancement.Builder builder = recipeOutput.advancement()
			.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
			.rewards(AdvancementRewards.Builder.recipe(id))
			.requirements(AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(builder::addCriterion);
		AbstractCookingRecipe abstractCookingRecipe = this.factory
			.create(
				(String)Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, new ItemStack(this.result), this.experience, this.cookingTime
			);
		recipeOutput.accept(id, abstractCookingRecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
	}

	private static CookingBookCategory determineSmeltingRecipeCategory(ItemLike result) {
		if (result.asItem().components().has(DataComponents.FOOD)) {
			return CookingBookCategory.FOOD;
		} else {
			return result.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
		}
	}

	private static CookingBookCategory determineBlastingRecipeCategory(ItemLike result) {
		return result.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
	}

	private static CookingBookCategory determineRecipeCategory(RecipeSerializer<? extends AbstractCookingRecipe> serializer, ItemLike result) {
		if (serializer == RecipeSerializer.SMELTING_RECIPE) {
			return determineSmeltingRecipeCategory(result);
		} else if (serializer == RecipeSerializer.BLASTING_RECIPE) {
			return determineBlastingRecipeCategory(result);
		} else if (serializer != RecipeSerializer.SMOKING_RECIPE && serializer != RecipeSerializer.CAMPFIRE_COOKING_RECIPE) {
			throw new IllegalStateException("Unknown cooking recipe type");
		} else {
			return CookingBookCategory.FOOD;
		}
	}

	/**
	 * Makes sure that this obtainable.
	 */
	private void ensureValid(ResourceLocation id) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
}
 */