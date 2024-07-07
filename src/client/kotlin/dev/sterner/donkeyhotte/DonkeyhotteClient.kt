package dev.sterner.donkeyhotte

import dev.sterner.donkeyhotte.model.GrindstoneBlockEntityModel
import dev.sterner.donkeyhotte.registry.DonkeyBlockEntityTypes
import dev.sterner.donkeyhotte.registry.DonkeyItems
import dev.sterner.donkeyhotte.renderer.ChopperBlockEntityRenderer
import dev.sterner.donkeyhotte.renderer.GrindstoneBlockEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers

object DonkeyhotteClient : ClientModInitializer {
	override fun onInitializeClient() {

		EntityModelLayerRegistry.registerModelLayer(GrindstoneBlockEntityModel.LAYER_LOCATION, GrindstoneBlockEntityModel.Companion::createBodyLayer)

		BlockEntityRenderers.register(DonkeyBlockEntityTypes.GRINDSTONE_BLOCK_ENTITY.get(), ::GrindstoneBlockEntityRenderer)
		BlockEntityRenderers.register(DonkeyBlockEntityTypes.CHOPPER_BLOCK_ENTITY.get(), ::ChopperBlockEntityRenderer)

		BuiltinItemRendererRegistry.INSTANCE.register(DonkeyItems.GRINDSONE.get(), GrindstoneBlockEntityRenderer())


	}
}