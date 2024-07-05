package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.block.GrindstoneBlock
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import java.util.function.Consumer
import java.util.function.Supplier


object DonkeyBlocks {

    val BLOCKS: MutableMap<Supplier<Block>, ResourceLocation> = mutableMapOf()
    val ITEMS: MutableMap<Supplier<Item>, ResourceLocation> = mutableMapOf()

    val GRINDSTONE_BLOCK = register("grindstone_block", { GrindstoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)) }, true)

    private fun <T : Block> register(name: String, blockSupplier: Supplier<T>, createItem: Boolean): Supplier<T> {
        BLOCKS[blockSupplier as Supplier<Block>] = Donkeyhotte.id(name)
        if (createItem) {
            val itemSupplier = Supplier { BlockItem(blockSupplier.get(), Item.Properties()) }
            ITEMS[itemSupplier as Supplier<Item>] = BLOCKS[blockSupplier]!!
        }
        return blockSupplier
    }

    private fun <T : Item> register(name: String, itemSupplier: Supplier<T>): Supplier<T> {
        ITEMS[itemSupplier as Supplier<Item>] = Donkeyhotte.id(name)
        return itemSupplier
    }

    fun init(){
        BLOCKS.keys.forEach(Consumer { blockSupplier: Supplier<Block> ->
            Registry.register(
                BuiltInRegistries.BLOCK,
                BLOCKS[blockSupplier]!!,
                blockSupplier.get()
            )
        })
        ITEMS.keys.forEach(Consumer { itemSupplier: Supplier<Item> ->
            Registry.register(
                BuiltInRegistries.ITEM,
                ITEMS[itemSupplier]!!,
                itemSupplier.get()
            )
        })
    }
}