package dev.sterner.donkeyhotte

import dev.sterner.donkeyhotte.data.*
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object DonkeyhotteDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
		val pack = gen.createPack()

		pack.addProvider { output, _ -> DonkeyModelProvider(output)}
		pack.addProvider { output, r -> DonkeyRecipeProvider(output, r)}
		pack.addProvider { output, r -> DonkeyLangProvider(output, r)}
		pack.addProvider { output, r -> DonkeyLootTableProvider(output, r)}
		pack.addProvider { output, r -> DonkeyBlockTagProvider(output, r)}
		pack.addProvider { output, r -> DonkeyItemTagProvider(output, r)}
	}
}