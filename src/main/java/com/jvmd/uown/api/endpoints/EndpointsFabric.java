package com.jvmd.uown.api.endpoints;

import com.jvmd.uown.config.ConnectionConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class EndpointsFabric {

    private static final String url = ConnectionConfig.getHost() + ":" + ConnectionConfig.getPort();
    private static final String token = ConnectionConfig.getToken();

    private static HashMap<String, Endpoint> endpoints = new HashMap<>();

    static {
        Arrays.stream(ConnectionConfig.Endpoints.values()).forEach(endpoint -> {
            endpoints.put(endpoint.value, () -> url + endpoint.value);
        });
    }


    private static Endpoint getEndpoint(String endpointName) {
        return endpoints.get(endpointName);
    }

    public static String getResponse(String endpointName, String... params) throws IOException {
        Endpoint endpoint = getEndpoint(endpointName);
        String[] paramArray = new String[params.length + 1];
        paramArray[0] = token;
        for (int i = 1; i < params.length; i++) {
            paramArray[i] = params[i];
        }

        return endpoint.getResponse(params);
    }

}
