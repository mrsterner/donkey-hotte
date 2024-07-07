package dev.sterner.donkeyhotte.data

import dev.sterner.donkeyhotte.registry.DonkeyBlocks
import dev.sterner.donkeyhotte.registry.DonkeyItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class DonkeyLootTableProvider(dataOutput: FabricDataOutput?,
                              registryLookup: CompletableFuture<HolderLookup.Provider>?
) : FabricBlockLootTableProvider(dataOutput, registryLookup) {

    override fun generate() {
        add(DonkeyBlocks.GRINDSTONE_BLOCK.get(), createSingleItemTable(DonkeyItems.GRINDSTONE.get()))
        add(DonkeyBlocks.CHOPPER_BLOCK.get(), createSingleItemTable(DonkeyItems.CHOPPER.get()))
    }

}