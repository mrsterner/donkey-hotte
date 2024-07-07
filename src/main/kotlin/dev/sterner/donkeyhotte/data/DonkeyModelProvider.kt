package dev.sterner.donkeyhotte.data

import dev.sterner.donkeyhotte.registry.DonkeyBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.world.level.block.Blocks

class DonkeyModelProvider(output: FabricDataOutput?) : FabricModelProvider(output) {

    override fun generateBlockStateModels(gen: BlockModelGenerators) {
        gen.blockEntityModels(ModelLocationUtils.getModelLocation(DonkeyBlocks.GRINDSTONE_BLOCK.get()), Blocks.ANDESITE)
            .createWithoutBlockItem(
                DonkeyBlocks.GRINDSTONE_BLOCK.get()
            )
        gen.blockEntityModels(ModelLocationUtils.getModelLocation(DonkeyBlocks.CHOPPER_BLOCK.get()), Blocks.ANDESITE)
            .createWithoutBlockItem(
                DonkeyBlocks.CHOPPER_BLOCK.get()
            )
    }

    override fun generateItemModels(gen: ItemModelGenerators) {

    }


}