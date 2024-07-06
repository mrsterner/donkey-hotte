package dev.sterner.donkeyhotte

import dev.sterner.donkeyhotte.registry.*
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object Donkeyhotte : ModInitializer {
	const val MOD_ID = "donkeyhotte"
    private val logger = LoggerFactory.getLogger(MOD_ID)


	override fun onInitialize() {
		DonkeyBlocks.BLOCKS.register()
		DonkeyItems.ITEMS.register()
		DonkeyBlockEntityTypes.BLOCK_ENTITY_TYPES.register()

		DonkeyRecipeTypes.RECIPE_TYPES.register()
		DonkeyRecipeSerializers.RECIPE_SERIALIZERS.register()
	}

	fun id(name: String): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
	}
}