package dev.sterner.donkeyhotte.renderer

import com.mojang.blaze3d.vertex.PoseStack
import dev.sterner.donkeyhotte.blockentity.GrindstoneBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class GrindstoneBlockEntityRenderer(var ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<GrindstoneBlockEntity> {

    override fun render(
        blockEntity: GrindstoneBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        light: Int,
        overlay: Int
    ) {

    }
}