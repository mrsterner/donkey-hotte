package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.block.GrindstoneBlock
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import java.util.function.Supplier


object DonkeyBlocks {

    val GRINDSTONE_BLOCK = register("grindstone_block", {
        GrindstoneBlock(Properties.ofFullCopy(Blocks.STONE))
    }, true)

    private fun <T : Block> register(name: String, block: Supplier<T>, createItem: Boolean): T {
        Registry.register(BuiltInRegistries.BLOCK, Donkeyhotte.id(name), block.get())
        if (createItem) {
            Registry.register(BuiltInRegistries.ITEM, Donkeyhotte.id(name), BlockItem(block.get(), Item.Properties()))
        }
        return block.get()
    }

    fun init(){

    }
}