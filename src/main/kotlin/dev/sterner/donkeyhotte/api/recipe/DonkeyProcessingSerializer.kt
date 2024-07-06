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

    private val strictSingleItemChanceCodec: Codec<ItemStackWithChance> = Codec.lazyInitialized {
        RecordCodecBuilder.create { instance ->
            instance.group(
                ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("item").forGetter { it.stack },
                Codec.FLOAT.fieldOf("chance").forGetter { it.chance }
            ).apply(instance, ::ItemStackWithChance)
        }
    }

    private val processingCodec: MapCodec<T> = RecordCodecBuilder.mapCodec { instance ->
        instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter { it.ingredient },
            strictSingleItemChanceCodec.fieldOf("result").forGetter { it.output },
            strictSingleItemChanceCodec.fieldOf("extra").forGetter { it.extraOutput },
            Codec.INT.fieldOf("processingTime").forGetter { it.processingTime }
        ).apply(instance, factory::create)
    }

    private val streamCodec: StreamCodec<RegistryFriendlyByteBuf, T> = StreamCodec.of(::toNetwork, ::fromNetwork)

    private fun fromNetwork(registryFriendlyByteBuf: RegistryFriendlyByteBuf): T {
        //Input
        val inputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(registryFriendlyByteBuf)

        //Output with chance
        val outputStack = ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf)
        val outputChance = registryFriendlyByteBuf.readFloat()

        //Extra output with chance
        val extraOutputStack = ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf)
        val extraOutputChance = registryFriendlyByteBuf.readFloat()

        //Processing time
        val processingTime = registryFriendlyByteBuf.readVarInt()

        return factory.create(inputIngredient, ItemStackWithChance(outputStack, outputChance), ItemStackWithChance(extraOutputStack, extraOutputChance), processingTime)
    }


    private fun toNetwork(registryFriendlyByteBuf: RegistryFriendlyByteBuf, grindstoneRecipe: DonkeyProcessingRecipe) {
        //Input
        Ingredient.CONTENTS_STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.ingredient)

        //Output with chance
        ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.output.stack)
        registryFriendlyByteBuf.writeFloat(grindstoneRecipe.output.chance)

        //Extra output with chance
        ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, grindstoneRecipe.extraOutput.stack)
        registryFriendlyByteBuf.writeFloat(grindstoneRecipe.extraOutput.chance)

        //Processing time
        registryFriendlyByteBuf.writeVarInt(grindstoneRecipe.processingTime)
    }


    override fun codec(): MapCodec<T> {
        return processingCodec
    }

    override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, T> {
        return streamCodec
    }

    fun interface Factory<T : DonkeyProcessingRecipe> {
        fun create(
            ingredient: Ingredient?,
            output: ItemStackWithChance?,
            extraOutput: ItemStackWithChance?,
            processingTime: Int
        ): T
    }
}