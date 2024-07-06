package dev.sterner.donkeyhotte.renderer

import com.mojang.blaze3d.vertex.PoseStack
import dev.sterner.donkeyhotte.blockentity.ChopperBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class ChopperBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<ChopperBlockEntity> {
    override fun render(
        blockEntity: ChopperBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        light: Int,
        overlay: Int
    ) {

    }
}