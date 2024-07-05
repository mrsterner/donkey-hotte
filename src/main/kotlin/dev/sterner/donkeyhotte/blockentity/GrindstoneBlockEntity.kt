package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class GrindstoneBlockEntity(blockPos: BlockPos, blockState: BlockState
) : DonkeyBlockEntity(DonkeyBlockEntityTypes.GRINDSTONE_BLOCK_ENTITY, blockPos, blockState) {

    override fun tick(world: Level, pos: BlockPos, state: BlockState) {

    }

    override fun onUse(player: Player?, hand: BlockHitResult): InteractionResult {
        return InteractionResult.PASS
    }
}