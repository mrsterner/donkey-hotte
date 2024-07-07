package dev.sterner.donkeyhotte.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.sterner.donkeyhotte.Donkeyhotte
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.Model
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType


class GrindstoneBlockEntityModel(root: ModelPart) : Model(RenderType::entitySolid) {

    private var wheel: ModelPart? = null
    /*
    private var wheel2: ModelPart? = null
    private var wheel3: ModelPart? = null
    private var wheel4: ModelPart? = null
    private var wheel5: ModelPart? = null
    private var wheel6: ModelPart? = null
    private var wheel7: ModelPart? = null
    private var wheel8: ModelPart? = null
    private var wheelface: ModelPart? = null

     */

    init {
        wheel = root.getChild("wheel")
        /*
        wheel2 = root.getChild("wheel2")
        wheel3 = root.getChild("wheel3")
        wheel4 = root.getChild("wheel4")
        wheel5 = root.getChild("wheel5")
        wheel6 = root.getChild("wheel6")
        wheel7 = root.getChild("wheel7")
        wheel8 = root.getChild("wheel8")
        wheelface = root.getChild("wheelface")

         */
    }

    override fun renderToBuffer(
        poseStack: PoseStack,
        buffer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        color: Int
    ) {
        wheel?.render(poseStack, buffer, packedLight, packedOverlay)
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(Donkeyhotte.id("grindstone"), "main")

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val wheel = partdefinition.addOrReplaceChild(
                "wheel",
                CubeListBuilder.create().texOffs(20, 34)
                    .addBox(-8.0f, -6.0f, -4.0f, 1.0f, 6.0f, 8.0f, CubeDeformation(0.01f)),
                PartPose.offset(-1.6f, 24.0f, 0.0f)
            )

            val wheel2 = wheel.addOrReplaceChild(
                "wheel2",
                CubeListBuilder.create().texOffs(0, 32)
                    .addBox(0.0f, -3.0f, 0.0f, 1.0f, 6.0f, 8.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-8.0f, -3.0f, 4.0f, 0.0f, 0.7854f, 0.0f)
            )

            val wheel3 = wheel2.addOrReplaceChild(
                "wheel3",
                CubeListBuilder.create().texOffs(30, 26)
                    .addBox(0.0f, -3.0f, 0.0f, 1.0f, 6.0f, 8.0f, CubeDeformation(0.01f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 8.0f, 0.0f, 0.7854f, 0.0f)
            )

            val wheel4 = wheel3.addOrReplaceChild(
                "wheel4",
                CubeListBuilder.create().texOffs(10, 26)
                    .addBox(0.0f, -3.0f, 0.0f, 1.0f, 6.0f, 8.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 8.0f, 0.0f, 0.7854f, 0.0f)
            )

            val wheel5 = wheel4.addOrReplaceChild(
                "wheel5",
                CubeListBuilder.create().texOffs(20, 20)
                    .addBox(0.0f, -3.0f, 0.0f, 1.0f, 6.0f, 8.0f, CubeDeformation(0.01f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 8.0f, 0.0f, 0.7854f, 0.0f)
            )

            val wheel6 = wheel5.addOrReplaceChild(
                "wheel6",
                CubeListBuilder.create().texOffs(0, 18)
                    .addBox(0.0f, -3.0f, 0.0f, 1.0f, 6.0f, 8.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 8.0f, 0.0f, 0.7854f, 0.0f)
            )

            val wheel7 = wheel6.addOrReplaceChild(
                "wheel7",
                CubeListBuilder.create().texOffs(10, 12)
                    .addBox(0.0f, -3.0f, 0.0f, 1.0f, 6.0f, 8.0f, CubeDeformation(0.01f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 8.0f, 0.0f, 0.7854f, 0.0f)
            )

            val wheel8 = wheel7.addOrReplaceChild(
                "wheel8",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(0.0f, -3.0f, 0.0f, 1.0f, 6.0f, 8.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 8.0f, 0.0f, 0.7854f, 0.0f)
            )

            val wheelface = wheel.addOrReplaceChild(
                "wheelface",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-9.5f, 0.08f, -10.0f, 20.0f, 0.0f, 20.0f, CubeDeformation(0.01f))
                    .texOffs(0, 0).addBox(-9.5f, 5.94f, -10.0f, 20.0f, 0.0f, 20.0f, CubeDeformation(0.01f)),
                PartPose.offset(1.6f, -6.0f, 0.0f)
            )

            return LayerDefinition.create(meshdefinition, 64, 64)
        }
    }




}