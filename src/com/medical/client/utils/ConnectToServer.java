package com.medical.client.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectToServer {

    public static boolean establishConnection(String URL) throws IOException {
        java.net.URL url = new URL(URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        if(conn.getResponseCode()==HttpURLConnection.HTTP_OK)
            return true;
        return false;
    }

}
