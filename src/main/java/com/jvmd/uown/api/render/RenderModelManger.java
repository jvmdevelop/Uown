package com.jvmd.uown.api.render;

import com.jvmd.uown.UownMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

public class RenderModelManger {
    public static void renderModel(MatrixStack poseStack , Entity player , IRenderTypeBuffer buffer, RegistryObject<Item> model
                                   , double translate1, double translate2, double translate3 , float scale
    ) {
        poseStack.pushPose();
        poseStack.translate(translate1,translate2 ,translate3);
        poseStack.scale(scale, scale, scale);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack stack = new ItemStack(model.get());

        itemRenderer.renderStatic(
                stack,
                ItemCameraTransforms.TransformType.GROUND,
                15728880,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer
        );

        poseStack.popPose();
    }
}
