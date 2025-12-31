package com.mycompany.minecraftweb3.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BackendClient {

    private static final String BASE_URL = "http://localhost:3000";

    public static BackendStatus checkStatus(String token) {

        try {
            URL url = new URL(BASE_URL + "/link/status?token=" + token);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);

            if (con.getResponseCode() != 200) {
                return BackendStatus.error("HTTP error");
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            con.disconnect();

            String json = response.toString();

            // Parseo manual simple
            if (json.contains("\"linked\":false")) {
                return BackendStatus.notLinked();
            }

            if (json.contains("\"linked\":true")) {
                int start = json.indexOf("\"wallet\":\"") + 10;
                int end = json.indexOf("\"", start);
                String wallet = json.substring(start, end);
                return BackendStatus.linked(wallet);
            }

            return BackendStatus.error("Invalid response");

        } catch (Exception e) {
            return BackendStatus.error(e.getMessage());
        }
    }
}
