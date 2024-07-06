package dev.sterner.donkeyhotte.api.recipe

import net.minecraft.world.item.ItemStack

data class ItemStackWithChance(
    val stack: ItemStack,
    val chance: Float
) {

    override fun toString(): String = "($stack, $chance)"
}