package dev.sterner.donkeyhotte.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

abstract class DonkeyBlockEntity(blockEntityType: BlockEntityType<*>, blockPos: BlockPos, blockState: BlockState) : BlockEntity(blockEntityType,
    blockPos, blockState
) {
    fun tick(world: Level, pos: BlockPos, state: BlockState) {

    }

    fun onUse(player: Player?, hand: BlockHitResult): InteractionResult {
        return InteractionResult.PASS
    }
}