package com.jvmd.uown.event;

import com.jvmd.uown.UownMod;
import com.jvmd.uown.api.render.RenderModelManger;
import com.jvmd.uown.api.user.EOwn;
import com.jvmd.uown.api.user.UsersRenderOwnManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "uown", value = Dist.CLIENT)
public class OtherPlayersRenderer {

    @SubscribeEvent
    public static void renderPlayers(RenderLivingEvent.Pre<PlayerEntity, ?> event) {
        // Фикс: NPC с серверных плагинов — это PlayerEntity, но не AbstractClientPlayerEntity
        if (!(event.getEntity() instanceof AbstractClientPlayerEntity)) return;
        AbstractClientPlayerEntity entity = (AbstractClientPlayerEntity) event.getEntity();
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

    @SubscribeEvent
    public static void renderMonsters(RenderLivingEvent.Pre<MonsterEntity, ?> event) {
        if (!(event.getEntity() instanceof MonsterEntity)) return;
        MonsterEntity entity = (MonsterEntity) event.getEntity();
        MatrixStack poseStack = event.getMatrixStack();
        IRenderTypeBuffer buffer = event.getBuffers();
        RenderModelManger.renderModel(poseStack, entity, buffer, EOwn.RED.item, 0D, entity.getBbHeight() + 1D, 0D, 1.5f);
    }

}
