package dev.sterner.donkeyhotte

import dev.sterner.donkeyhotte.data.DonkeyModelProvider
import dev.sterner.donkeyhotte.data.DonkeyRecipeProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object DonkeyhotteDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
		val pack = gen.createPack()

		pack.addProvider { output, re ->
			DonkeyModelProvider(output)
			DonkeyRecipeProvider(output, re)
		}
	}
}