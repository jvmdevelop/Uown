package com.jvmd.uown.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public final class HttpUtil {

    public static URLConnection getReadingConnection(String spec) throws IOException {

        URLConnection connection =  new URL(spec).openConnection();

        return connection;
    }

    public static String readContent(URLConnection connection) throws IOException {
        return new Scanner(connection.getInputStream()).useDelimiter("\\A").next();
    }

}
