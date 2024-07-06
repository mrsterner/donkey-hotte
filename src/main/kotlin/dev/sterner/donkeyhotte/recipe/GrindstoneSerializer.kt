package dev.sterner.donkeyhotte.recipe

import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.util.Function6
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.codec.StreamDecoder
import net.minecraft.network.codec.StreamEncoder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CookingBookCategory
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import java.util.function.Function

class GrindstoneSerializer(private val factory: Factory<GrindstoneRecipe>) : RecipeSerializer<GrindstoneRecipe> {

    private val codec: MapCodec<GrindstoneRecipe> = RecordCodecBuilder.mapCodec { instance ->
        instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter { it.ingredient },
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter { it.output },
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter { it.extraOutput }
        ).apply(instance, factory::create)
    }

    override fun codec(): MapCodec<GrindstoneRecipe> {
        TODO("Not yet implemented")
    }

    override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, GrindstoneRecipe> {
        TODO("Not yet implemented")
    }

    interface Factory<T : GrindstoneRecipe> {
        fun create(
            ingredient: Ingredient?,
            output: ItemStack?,
            extraOutput: ItemStack?
        ): T
    }
}