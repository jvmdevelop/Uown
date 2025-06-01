package com.jvmd.uown.event;

import com.jvmd.uown.UownMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "uown", value = Dist.CLIENT)
public class PlayerModelRenderer {

    @SubscribeEvent
    public static void onRenderPlayer(RenderLivingEvent.Pre<ClientPlayerEntity, ?> event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ClientPlayerEntity)) return;
        ClientPlayerEntity player = (ClientPlayerEntity) entity;
        if (player.isInvisible()) return;

        MatrixStack poseStack = event.getMatrixStack();
        IRenderTypeBuffer buffer = event.getBuffers();
        
        poseStack.pushPose();

        poseStack.translate(0.0D, player.getBbHeight() + 1D, 0.0D);
        poseStack.scale(1.5f, 1.5f, 1.5f);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack stack = new ItemStack(UownMod.ModItems.SERVKEEPER_GREEN.get());

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