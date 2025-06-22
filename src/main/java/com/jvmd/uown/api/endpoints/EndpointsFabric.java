package com.jvmd.uown.api.endpoints;

import com.jvmd.uown.config.ConnectionConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class EndpointsFabric {

    private static final String url = ConnectionConfig.getHost() + ":" + ConnectionConfig.getPort();

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

        return endpoint.getResponse(params);
    }

}
