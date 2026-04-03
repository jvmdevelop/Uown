package com.jvmd.uown.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Mod.EventBusSubscriber(modid = "uown", value = Dist.CLIENT)
public class RollHandler {

    private static final long DOUBLE_PRESS_WINDOW = 300L;

    private static boolean prevSneaking = false;
    private static long lastSneakPress = 0L;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;
        if (!mc.player.isOnGround()) return;

        boolean sneaking = mc.options.keyShift.isDown();

        if (sneaking && !prevSneaking) {
            long now = System.currentTimeMillis();
            if (now - lastSneakPress <= DOUBLE_PRESS_WINDOW) {
                playRoll(mc.player);
                lastSneakPress = 0L;
            } else {
                lastSneakPress = now;
            }
        }

        prevSneaking = sneaking;
    }

    private static void playRoll(ClientPlayerEntity player) {
        try {
            Class<?> holderClass = Class.forName("io.github.kosmx.emotes.main.EmoteHolder");
            Field listField = holderClass.getField("list");
            List<?> list = (List<?>) listField.get(null);

            for (Object holder : list) {
                String name = (String) holderClass.getField("name").get(holder);

                if ("roll".equalsIgnoreCase(name)) {
                    for (Method m : holderClass.getMethods()) {
                        if (m.getName().equals("play") && m.getParameterCount() == 1) {
                            m.invoke(holder, player);
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

}
