package com.jvmd.uown.api.user;

import com.jvmd.uown.api.endpoints.Endpoint;
import com.jvmd.uown.api.endpoints.EndpointsFabric;
import com.jvmd.uown.config.ConnectionConfig;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class UserObserver {

    private static ConcurrentMap<Object, Object> users = new ConcurrentHashMap<>();
    private static ExecutorService executor = Executors.newFixedThreadPool(2);
    private static User currentUser;


    static {
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;

        currentUser = User.builder()
                .username(mc.player.getName().getString())
                .build();

        try {
            currentUser.setOwn(getUserStatus(currentUser.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // call this method if any user render near
    // add user if player see him
    public static boolean addUser(User user) {
        //get user status
        //call render method
        return false;
    }

    // remove if he leave or you dont see it many time
    public static boolean removeUser(User user) {
        return false;
    }


    public static EOwn getUserStatus(String username) throws IOException {
        return EOwn.valueOf(getStatus(username));
    }

    private static String getStatus(String username) throws IOException {
        return EndpointsFabric.getResponse(ConnectionConfig.Endpoints.GET_PLAYER_STATUS.value, username);
    }

}
