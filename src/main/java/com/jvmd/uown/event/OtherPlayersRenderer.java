package com.jvmd.uown.event;

import com.jvmd.uown.UownMod;
import com.jvmd.uown.api.render.RenderModelManger;
import com.jvmd.uown.api.user.EOwn;
import com.jvmd.uown.api.user.UsersRenderOwnManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = "uown", value = Dist.CLIENT)
public class OtherPlayersRenderer {

    @SubscribeEvent
    public static void renderPlayers(RenderLivingEvent.Pre<PlayerEntity, ?> event) {
        if (!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity entity = (PlayerEntity) event.getEntity();
        if (entity == UownMod.mc.player) return;
        String name = entity.getGameProfile().getName();
        EOwn playerOwn = EOwn.GREEN;
        try {
            playerOwn = UsersRenderOwnManager.getUserStatus(name);
            if (playerOwn == null) playerOwn = EOwn.GREEN;
        } catch (Exception e) {
            // log error if needed
        }
        MatrixStack poseStack = event.getMatrixStack();
        IRenderTypeBuffer buffer = event.getBuffers();
        RenderModelManger.renderModel(poseStack, entity, buffer , playerOwn.item, 0D, entity.getBbHeight()+1D, 0D, 1.5f);
    }

}
