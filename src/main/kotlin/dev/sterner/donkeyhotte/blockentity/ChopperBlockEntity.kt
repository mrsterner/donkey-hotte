package dev.sterner.donkeyhotte.blockentity

import dev.sterner.donkeyhotte.api.recipe.DonkeyProcessingRecipe
import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class ChopperBlockEntity(blockPos: BlockPos, blockState: BlockState) : DonkeyBlockEntity(DonkeyBlockEntityTypes.CHOPPER_BLOCK_ENTITY.get(),
    blockPos, blockState
) {
    override fun process(
        level: Level,
        pos: BlockPos,
        state: BlockState,
        connectedEntity: PathfinderMob,
        recipe: DonkeyProcessingRecipe
    ) {

    }

    override fun checkForRecipe(level: Level, donkeyBlockEntity: DonkeyBlockEntity): DonkeyProcessingRecipe? {
       return null
    }
}