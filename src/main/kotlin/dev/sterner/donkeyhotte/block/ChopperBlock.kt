package dev.sterner.donkeyhotte.block

import dev.sterner.donkeyhotte.blockentity.ChopperBlockEntity
import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class ChopperBlock(properties: Properties) : DonkeyEntityBlock<ChopperBlockEntity>(properties) {
    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity? {
        return DonkeyBlockEntityTypes.CHOPPER_BLOCK_ENTITY.get().create(blockPos, blockState)
    }
}