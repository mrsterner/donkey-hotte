package dev.sterner.donkeyhotte.api.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer

open class DonkeyProcessingSerializer<T : DonkeyProcessingRecipe>(private val factory: Factory<T>) : RecipeSerializer<T> {

    private val codec: MapCodec<T> = RecordCodecBuilder.mapCodec { instance ->
        instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter { it.ingredient },
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter { it.output },
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("extra").forGetter { it.extraOutput },
            Codec.INT.fieldOf("processingTime").forGetter { it.processingTime }
        ).apply(instance, factory::create)
    }

    private val streamCodec: StreamCodec<RegistryFriendlyByteBuf, T> = StreamCodec.of(this::toNetwork, this::fromNetwork)

    private fun fromNetwork(registryFriendlyByteBuf: RegistryFriendlyByteBuf): T {
        val inputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(registryFriendlyByteBuf)
        val outputStack = ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf)
        val extraOutputStack = ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf)
        val processingTime = registryFriendlyByteBuf.readVarInt()
        return factory.create(inputIngredient, outputStack, extraOutputStack, processingTime)
    }


    private fun toNetwork(registryFriendlyByteBuf: RegistryFriendlyByteBuf, grindstoneRecipe: DonkeyProcessingRecipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.ingredient)
        ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.output)
        ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.extraOutput)
        registryFriendlyByteBuf.writeVarInt(grindstoneRecipe.processingTime)
    }


    override fun codec(): MapCodec<T> {
        return codec
    }

    override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, T> {
        return streamCodec
    }

    fun interface Factory<T : DonkeyProcessingRecipe> {
        fun create(
            ingredient: Ingredient?,
            output: ItemStack?,
            extraOutput: ItemStack?,
            processingTime: Int
        ): T
    }
}