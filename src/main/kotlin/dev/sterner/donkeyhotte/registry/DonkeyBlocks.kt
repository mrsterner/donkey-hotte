package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.block.ChopperBlock
import dev.sterner.donkeyhotte.block.GrindstoneBlock
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

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
}