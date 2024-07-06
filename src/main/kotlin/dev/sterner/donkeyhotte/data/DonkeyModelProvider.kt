package dev.sterner.donkeyhotte.data

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators

class DonkeyModelProvider(output: FabricDataOutput?) : FabricModelProvider(output) {

    override fun generateBlockStateModels(gen: BlockModelGenerators) {
    }

    override fun generateItemModels(gen: ItemModelGenerators) {
    }


}