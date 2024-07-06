package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.block.ChopperBlock
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

    val BLOCKS = LazyRegistrar.create(BuiltInRegistries.BLOCK, Donkeyhotte.MOD_ID)

    val GRINDSTONE_BLOCK = BLOCKS.register("grindstone_block") {
        GrindstoneBlock(
            Properties.ofFullCopy(
                Blocks.STONE
            )
        )
    }

    val CHOPPER_BLOCK = BLOCKS.register("chopper_block") {
        ChopperBlock(
            Properties.ofFullCopy(
                Blocks.STONE
            )
        )
    }

    fun init(){

    }
}