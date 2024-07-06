package dev.sterner.donkeyhotte.block

import dev.sterner.donkeyhotte.blockentity.DonkeyBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.StringRepresentable
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.AbstractFurnaceBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.FurnaceBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.phys.BlockHitResult


abstract class DonkeyEntityBlock<T : DonkeyBlockEntity>(properties: Properties) : Block(properties), EntityBlock {

    init {
        registerDefaultState(stateDefinition.any().setValue(DOUBLE_VERTICAL, DoubleBlock.LOWER).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(DOUBLE_VERTICAL).add(BlockStateProperties.HORIZONTAL_FACING)
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { tickerLevel, pos, tickerState, blockEntity ->
            if (blockEntity is DonkeyBlockEntity) {
                (blockEntity as DonkeyBlockEntity).tick(tickerLevel, pos, tickerState)
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

    companion object {
        val DOUBLE_VERTICAL: EnumProperty<DoubleBlock> = EnumProperty.create("double", DoubleBlock::class.java)
    }

    enum class DoubleBlock : StringRepresentable {
        UPPER,
        LOWER;

        override fun getSerializedName(): String {
            return if(this == UPPER) "upper" else "lower"
        }
    }
}

