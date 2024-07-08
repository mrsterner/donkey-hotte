package dev.sterner.donkeyhotte.data

import dev.sterner.donkeyhotte.registry.DonkeyBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import java.util.concurrent.CompletableFuture

class DonkeyBlockTagProvider(output: FabricDataOutput?,
                             registriesFuture: CompletableFuture<HolderLookup.Provider>?
) : FabricTagProvider.BlockTagProvider(output, registriesFuture) {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(DonkeyBlocks.GRINDSTONE_BLOCK.get()).add(DonkeyBlocks.CHOPPER_BLOCK.get())
    }
}