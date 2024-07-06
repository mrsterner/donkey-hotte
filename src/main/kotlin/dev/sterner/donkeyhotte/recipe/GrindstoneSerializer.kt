package dev.sterner.donkeyhotte.recipe

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer

class GrindstoneSerializer(private val factory: Factory<GrindstoneRecipe>) : RecipeSerializer<GrindstoneRecipe> {

    private val codec: MapCodec<GrindstoneRecipe> = RecordCodecBuilder.mapCodec { instance ->
        instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter { it.ingredient },
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter { it.output },
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter { it.extraOutput }
        ).apply(instance, factory::create)
    }

    private val streamCodec: StreamCodec<RegistryFriendlyByteBuf, GrindstoneRecipe> = StreamCodec.of(this::toNetwork, this::fromNetwork)

    private fun fromNetwork(registryFriendlyByteBuf: RegistryFriendlyByteBuf): GrindstoneRecipe {
        val inputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(registryFriendlyByteBuf)
        val outputStack = ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf)
        val extraOutputStack = ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf)
        return factory.create(inputIngredient, outputStack, extraOutputStack)
    }


    private fun toNetwork(registryFriendlyByteBuf: RegistryFriendlyByteBuf, grindstoneRecipe: GrindstoneRecipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.ingredient)
        ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.output)
        ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.extraOutput)
    }


    override fun codec(): MapCodec<GrindstoneRecipe> {
        return codec
    }

    override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, GrindstoneRecipe> {
        return streamCodec
    }

    fun interface Factory<T : GrindstoneRecipe> {
        fun create(
            ingredient: Ingredient?,
            output: ItemStack?,
            extraOutput: ItemStack?
        ): T
    }
}