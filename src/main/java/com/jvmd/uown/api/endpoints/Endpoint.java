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
        if (params.length == 0) {
            return "";
        }

        String[] split = getName().split("\\?");
        if (split.length < 2) {
            throw new IllegalArgumentException("Invalid URL format: missing parameters");
        }

        String baseUrl = split[0];
        String[] queryParts = split[1].split("&");

        List<String> paramNames = Arrays.stream(queryParts)
                .map(part -> part.split("=")[0])
                .collect(Collectors.toList());

        LOG.info("Request URL: " + getName());
        LOG.info("Request Params: " + paramNames);

        StringBuilder fullUrl = new StringBuilder(baseUrl);
        for (int i = 0; i < paramNames.size(); i++) {
            String paramValue = (i < params.length) ? params[i] : "";
            LOG.info("Request Param: {}", paramValue);
            fullUrl.append(i == 0 ? "?" : "&")
                    .append(paramNames.get(i))
                    .append("=")
                    .append(paramValue);
        }


        URLConnection connection = HttpUtil.getReadingConnection(fullUrl.toString());
        return HttpUtil.readContent(connection);
    }

}
