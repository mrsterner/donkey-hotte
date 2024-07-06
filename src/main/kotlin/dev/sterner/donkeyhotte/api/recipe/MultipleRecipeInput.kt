package dev.sterner.donkeyhotte.api.recipe

import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput

@JvmRecord
data class MultipleRecipeInput(val inventory: Container) : RecipeInput {
    override fun getItem(index: Int): ItemStack {
        return inventory.getItem(index)
    }

    override fun size(): Int {
        return inventory.containerSize
    }
}