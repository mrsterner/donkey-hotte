package dev.sterner.donkeyhotte.renderer

import com.mojang.blaze3d.vertex.PoseStack
import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.blockentity.GrindstoneBlockEntity
import dev.sterner.donkeyhotte.model.GrindstoneBlockEntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class GrindstoneBlockEntityRenderer(private var ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<GrindstoneBlockEntity> {

    var model: GrindstoneBlockEntityModel? = null

    init {
        model = GrindstoneBlockEntityModel(ctx.modelSet.bakeLayer(GrindstoneBlockEntityModel.LAYER_LOCATION))
    }

    override fun render(
        blockEntity: GrindstoneBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        light: Int,
        overlay: Int
    ) {
        poseStack.pushPose()
        val vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(Donkeyhotte.id("textures/entity/grindstone_andesite.png")))
        model?.renderToBuffer(poseStack, vertexConsumer, light, overlay)
        poseStack.popPose()
    }
}