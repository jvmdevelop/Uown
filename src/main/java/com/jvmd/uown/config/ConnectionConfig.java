package com.jvmd.uown.config;


import com.jvmd.uown.util.HttpUtil;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;


public class ConnectionConfig {

    @Getter
    private static final String host;
    @Getter
    private static final int port;
    private static final String password;
    @Getter
    private static final String token;


    private static String register() throws IOException {
        URLConnection connection = HttpUtil.getReadingConnection(host + ":" + port + Endpoints.REGISTER.value.replaceAll("$", password));
        return HttpUtil.readContent(connection);
    }

    static {
        host = System.getenv("HOST") != null ? System.getenv("HOST") : "localhost";
        port = Integer.parseInt(System.getenv("PORT") != null ? System.getenv("PORT") : "8080") ;
        password = System.getenv("PASSWORD") != null ? System.getenv("PASSWORD") : "deltaq123";

        try {
            token = register();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public enum Endpoints{
        REGISTER("/api/v1/public/register?password=$"),
        GET_PLAYER_STATUS("/api/v1/getPlayerStatus?token=$&username=$"),
        // TODO some add
        ;

        public final String value;


        Endpoints(String s) {
            value = s;
        }
    }


}
