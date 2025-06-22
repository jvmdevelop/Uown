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


    static {
        host = System.getenv("HOST") != null ? System.getenv("HOST") : "localhost";
        port = Integer.parseInt(System.getenv("PORT") != null ? System.getenv("PORT") : "8080") ;
        password = System.getenv("PASSWORD") != null ? System.getenv("PASSWORD") : "deltaq123";
    }



    public enum Endpoints{
        GET_PLAYER_STATUS("/api/v1/user/own?username=$"),
        GET_PLAYER("/api/v1/user?username=$");
        public final String value;
        Endpoints(String s) {
            value = s;
        }
    }


}
