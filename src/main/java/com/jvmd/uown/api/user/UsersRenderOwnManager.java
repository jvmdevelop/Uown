package com.jvmd.uown.api.user;

import com.jvmd.uown.UownMod;
import com.jvmd.uown.api.endpoints.Endpoint;
import com.jvmd.uown.api.endpoints.EndpointsFabric;
import com.jvmd.uown.config.ConnectionConfig;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UsersRenderOwnManager {
    private static final Logger LOG = LoggerFactory.getLogger(UsersRenderOwnManager.class);
    private static final User currentUser;

    static {
        Minecraft mc = UownMod.mc;
        String username = "default";
        if (mc != null && mc.player != null && mc.player.getGameProfile() != null) {
            username = mc.player.getGameProfile().getName();
        } else {
            LOG.warn("Minecraft player is null, using default username");
        }
        currentUser = User.builder().username(username).own(EOwn.GREEN).build();
        try {
            currentUser.setOwn(getUserStatus(currentUser.getUsername()));
        } catch (Exception e) {
            LOG.error("Failed to fetch user status for {}: {}", username, e.getMessage());
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static EOwn getUserStatus(String username) throws IOException {
        try {
            return EOwn.valueOf(getStatus(username));
        } catch (Exception e) {
            LOG.error("Failed to get EOwn for username {}: {}", username, e.getMessage());
            return EOwn.GREEN;
        }
    }

    private static String getStatus(String username) throws IOException {
        return EndpointsFabric.getResponse(ConnectionConfig.Endpoints.GET_PLAYER_STATUS.value, username);
    }
}
