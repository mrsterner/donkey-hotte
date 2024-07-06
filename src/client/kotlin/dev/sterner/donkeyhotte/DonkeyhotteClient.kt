package dev.sterner.donkeyhotte

import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import dev.sterner.donkeyhotte.renderer.ChopperBlockEntityRenderer
import dev.sterner.donkeyhotte.renderer.GrindstoneBlockEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers

object DonkeyhotteClient : ClientModInitializer {
	override fun onInitializeClient() {

		BlockEntityRenderers.register(DonkeyBlockEntityTypes.GRINDSTONE_BLOCK_ENTITY.get(), ::GrindstoneBlockEntityRenderer)
		BlockEntityRenderers.register(DonkeyBlockEntityTypes.CHOPPER_BLOCK_ENTITY.get(), ::ChopperBlockEntityRenderer)
	}
}