package com.medical.client.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectToServer {

    private static HttpURLConnection conn;

    public static HttpURLConnection getConn() {
        return conn;
    }

    public static boolean establishConnection(String URL) throws IOException {
        URL url = new URL(URL);
        conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.connect();
        if(conn.getResponseCode()==HttpURLConnection.HTTP_OK)
            return true;
        return false;
    }

}
