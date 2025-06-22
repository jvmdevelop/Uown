package com.jvmd.uown.event;

import com.jvmd.uown.UownMod;
import com.jvmd.uown.api.render.RenderModelManger;
import com.jvmd.uown.api.user.UsersRenderOwnManager;
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

        EOwn own = EOwn.GREEN;
        try {
            own = UsersRenderOwnManager.getCurrentUser().getOwn();
            if (own == null) own = EOwn.GREEN;
        } catch (Exception e) {
            // log error if needed
        }
        RenderModelManger.renderModel(poseStack, entity, buffer , own.item , 0D, entity.getBbHeight()+1D, 0D, 1.5f);
    }
}