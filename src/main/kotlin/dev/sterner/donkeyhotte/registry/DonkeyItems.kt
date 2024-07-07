package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item

object DonkeyItems {

    val ITEMS: LazyRegistrar<Item> = LazyRegistrar.create(BuiltInRegistries.ITEM, Donkeyhotte.MOD_ID)

    val GRINDSONE: RegistryObject<BlockItem> = ITEMS.register("grindstone") {
        BlockItem(DonkeyBlocks.GRINDSTONE_BLOCK.get(), Item.Properties())
    }

    val CHOPPER: RegistryObject<BlockItem> = ITEMS.register("chopper") {
        BlockItem(DonkeyBlocks.CHOPPER_BLOCK.get(), Item.Properties())
    }
}