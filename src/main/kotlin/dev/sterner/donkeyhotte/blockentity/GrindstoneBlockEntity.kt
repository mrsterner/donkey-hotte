package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class GrindstoneBlockEntity(blockPos: BlockPos, blockState: BlockState
) : DonkeyBlockEntity(DonkeyBlockEntityTypes.GRINDSTONE_BLOCK_ENTITY.get(), blockPos, blockState) {


}