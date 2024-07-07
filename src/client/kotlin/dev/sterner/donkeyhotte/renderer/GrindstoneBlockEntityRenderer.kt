package dev.sterner.donkeyhotte.renderer

import com.mojang.blaze3d.vertex.PoseStack
import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.blockentity.GrindstoneBlockEntity
import dev.sterner.donkeyhotte.model.GrindstoneBlockEntityModel
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.util.Mth
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import org.joml.Quaternionf

class GrindstoneBlockEntityRenderer(private var ctx: BlockEntityRendererProvider.Context?) : BlockEntityRenderer<GrindstoneBlockEntity>, BuiltinItemRendererRegistry.DynamicItemRenderer {

    constructor() : this(null)

    var model: GrindstoneBlockEntityModel? = GrindstoneBlockEntityModel(GrindstoneBlockEntityModel.createBodyLayer().bakeRoot())

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
        poseStack.translate(0.5, -(12/16.0), 0.5)
        rotateGrindstone(blockEntity, poseStack, partialTick)
        model?.renderToBuffer(poseStack, vertexConsumer, light, overlay)
        poseStack.popPose()

        poseStack.pushPose()
        poseStack.translate(0.5, -(18/16.0), 0.5)
        model?.renderToBuffer(poseStack, vertexConsumer, light, overlay)
        poseStack.popPose()
    }

    private fun rotateGrindstone(blockEntity: GrindstoneBlockEntity, poseStack: PoseStack, partialTick: Float) {
        val angle = blockEntity.angle
        val oldAngle = blockEntity.oldAngle
        val lerp = Mth.lerp(partialTick, oldAngle.toFloat(), angle.toFloat())
        poseStack.mulPose(Quaternionf().rotateY(lerp))
    }

    override fun render(
        stack: ItemStack?,
        mode: ItemDisplayContext?,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        light: Int,
        overlay: Int
    ) {
        poseStack.pushPose()
        val vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(Donkeyhotte.id("textures/entity/grindstone_andesite.png")))
        poseStack.translate(0.5, -(12/16.0), 0.5)
        model?.renderToBuffer(poseStack, vertexConsumer, light, overlay)
        poseStack.popPose()

        poseStack.pushPose()
        poseStack.translate(0.5, -(18/16.0), 0.5)
        model?.renderToBuffer(poseStack, vertexConsumer, light, overlay)
        poseStack.popPose()
    }
}