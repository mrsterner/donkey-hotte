package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.block.ChopperBlock
import dev.sterner.donkeyhotte.block.GrindstoneBlock
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

object DonkeyBlocks {

    val BLOCKS: LazyRegistrar<Block> = LazyRegistrar.create(BuiltInRegistries.BLOCK, Donkeyhotte.MOD_ID)

    val GRINDSTONE_BLOCK: RegistryObject<GrindstoneBlock> = BLOCKS.register("grindstone_block") {
        GrindstoneBlock(
            Properties.ofFullCopy(
                Blocks.STONE
            )
                .noOcclusion()
        )
    }

    val CHOPPER_BLOCK: RegistryObject<ChopperBlock> = BLOCKS.register("chopper_block") {
        ChopperBlock(
            Properties.ofFullCopy(
                Blocks.STONE
            )
        )
    }
}