package dev.sterner.donkeyhotte

import dev.sterner.donkeyhotte.data.*
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object DonkeyhotteDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
		val pack = gen.createPack()

		pack.addProvider { output, registriesFuture ->  DonkeyModelProvider(output)}
		pack.addProvider { output, registriesFuture ->  DonkeyRecipeProvider(output, registriesFuture)}
		pack.addProvider { output, registriesFuture ->  DonkeyLangProvider(output, registriesFuture)}
		pack.addProvider { output, registriesFuture ->  DonkeyLootTableProvider(output, registriesFuture)}
		pack.addProvider { output, registriesFuture ->  DonkeyBlockTagProvider(output, registriesFuture)}
		pack.addProvider { output, registriesFuture ->  DonkeyItemTagProvider(output, registriesFuture)}
	}
}