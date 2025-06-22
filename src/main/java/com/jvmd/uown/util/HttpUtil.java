package com.jvmd.uown.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;

public final class HttpUtil {
    private static final OkHttpClient client = new OkHttpClient();

    public static URLConnection getReadingConnection(String spec) throws IOException {

        URLConnection connection =  new URL(spec).openConnection();

        return connection;
    }

    public static String readContent(URLConnection connection) throws IOException {
        return new Scanner(connection.getInputStream()).useDelimiter("\\A").next();
    }

    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body() != null ? response.body().string() : "";
        }
    }

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body() != null ? response.body().string() : "";
        }
    }
}
