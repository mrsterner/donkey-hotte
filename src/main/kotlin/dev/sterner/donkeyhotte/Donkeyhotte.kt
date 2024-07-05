package dev.sterner.donkeyhotte

import dev.sterner.donkeyhotte.blockentity.DonkeyBlockEntity
import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import dev.sterner.donkeyhotte.registry.DonkeyBlocks
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation
import org.intellij.lang.annotations.Identifier
import org.slf4j.LoggerFactory

object Donkeyhotte : ModInitializer {
	private const val MOD_ID = "donkeyhotte"
    private val logger = LoggerFactory.getLogger(MOD_ID)


	override fun onInitialize() {
		DonkeyBlocks.init()
		DonkeyBlockEntityTypes.init()
	}

	fun id(name: String): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
	}
}