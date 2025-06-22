package com.jvmd.uown.api.endpoints;

import com.jvmd.uown.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
public interface Endpoint {

    Logger LOG = LoggerFactory.getLogger(Endpoint.class);

    String getName(); // /api/v1/users ? username=jdjsdj&passwword=sdsd


    /**
     * @param params param1 - apiEndpoint; any - params: username=name
     * @return
     * @throws IOException
     */
    default String getResponse(String[] params) throws IOException {
        String url = getName();
        if (url.contains("$")) {
            url = String.format(url.replace("$", "%s"), (Object[]) params);
        } else if (params.length > 0) {
            StringBuilder sb = new StringBuilder(url);
            sb.append(url.contains("?") ? "&" : "?");
            for (int i = 0; i < params.length; i++) {
                if (i > 0) sb.append("&");
                sb.append("param").append(i).append("=").append(params[i]);
            }
            url = sb.toString();
        }
        LOG.info("Request URL: {}", url);
        return com.jvmd.uown.util.HttpUtil.get(url);
    }

}
