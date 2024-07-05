package dev.sterner.donkeyhotte.block

import dev.sterner.donkeyhotte.blockentity.DonkeyBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult


abstract class DonkeyEntityBlock<T : DonkeyBlockEntity>(properties: Properties) : Block(properties), EntityBlock {

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { tickerWorld, pos, tickerState, blockEntity ->
            if (blockEntity is DonkeyBlockEntity) {
                (blockEntity as DonkeyBlockEntity).tick(tickerWorld, pos, tickerState)
            }
        }
    }

    override fun useWithoutItem(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        if (level.getBlockEntity(blockPos) is DonkeyBlockEntity) {
            val be = level.getBlockEntity(blockPos) as DonkeyBlockEntity
            return be.onUse(player, blockHitResult)
        }
        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult)
    }
}

