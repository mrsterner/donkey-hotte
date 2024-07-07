package dev.sterner.donkeyhotte.data

import dev.sterner.donkeyhotte.registry.DonkeyBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class DonkeyLangProvider(output: FabricDataOutput, comp: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(output, comp) {

    override fun generateTranslations(registryLookup: HolderLookup.Provider?, builder: TranslationBuilder) {
        builder.add(DonkeyBlocks.GRINDSTONE_BLOCK.get(), "Grindstone")
        builder.add(DonkeyBlocks.CHOPPER_BLOCK.get(), "Chopper")
    }
}