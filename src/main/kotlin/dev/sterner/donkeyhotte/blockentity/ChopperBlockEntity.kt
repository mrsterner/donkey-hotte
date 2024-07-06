package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class ChopperBlockEntity(blockPos: BlockPos, blockState: BlockState) : DonkeyBlockEntity(DonkeyBlockEntityTypes.CHOPPER_BLOCK_ENTITY.get(),
    blockPos, blockState
) {
}