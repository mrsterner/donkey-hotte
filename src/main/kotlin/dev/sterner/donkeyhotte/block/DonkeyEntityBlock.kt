package dev.sterner.donkeyhotte.block

import dev.sterner.donkeyhotte.blockentity.DonkeyBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.StringRepresentable
import net.minecraft.world.Containers
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.ChestType
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.BlockHitResult


abstract class DonkeyEntityBlock<T : DonkeyBlockEntity>(properties: Properties) : Block(properties), EntityBlock,
    SimpleWaterloggedBlock {

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(DOUBLE_VERTICAL, DoubleBlock.LOWER)
            .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
            .setValue(BlockStateProperties.WATERLOGGED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(DOUBLE_VERTICAL).add(BlockStateProperties.HORIZONTAL_FACING).add(BlockStateProperties.WATERLOGGED)
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

    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        movedByPiston: Boolean
    ) {
        Containers.dropContentsOnDestroy(state, newState, level, pos)
        super.onRemove(state, level, pos, newState, movedByPiston)
    }

    override fun getFluidState(state: BlockState): FluidState {
        return if (state.getValue(ChestBlock.WATERLOGGED)) Fluids.WATER.getSource(false) else super.getFluidState(state)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val fluidState = context.level.getFluidState(context.clickedPos)
        return defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.type === Fluids.WATER)
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

