package dev.sterner.donkeyhotte.data

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import java.util.concurrent.CompletableFuture

class DonkeyItemTagProvider(output: FabricDataOutput?, registriesFuture: CompletableFuture<HolderLookup.Provider>?) : FabricTagProvider.ItemTagProvider(output, registriesFuture) {

    override fun addTags(wrapperLookup: HolderLookup.Provider) {

    }
}