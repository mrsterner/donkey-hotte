package dev.sterner.donkeyhotte

import dev.sterner.donkeyhotte.data.DonkeyModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object DonkeyhotteDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
		val pack = gen.createPack()

		pack.addProvider { output, _ ->
			DonkeyModelProvider(output)
		}
	}
}