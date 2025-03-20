/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginGoogle;


import model.GoogleUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 *
 * @author ASUS
 */
public class GoogleUtils {
 public static String getToken(String code) throws IOException {
        URL url = new URL(ConstansGoogle.GOOGLE_LINK_GET_TOKEN);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);

    String params = "code=" + code
            + "&client_id=" + ConstansGoogle.GOOGLE_CLIENT_ID
            + "&client_secret=" + ConstansGoogle.GOOGLE_CLIENT_SECRET
            + "&redirect_uri=" + ConstansGoogle.GOOGLE_REDIRECT_URI
            + "&grant_type=" + ConstansGoogle.GOOGLE_GRANT_TYPE;

    try (OutputStream os = conn.getOutputStream()) {
        os.write(params.getBytes());
    }

    String response = new Scanner(conn.getInputStream()).useDelimiter("\\A").next();

    // Chuyển đổi JSON sử dụng Gson
    JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
    return jsonObject.get("access_token").getAsString();
    }

    public static GoogleUser getUserInfo(String accessToken) throws IOException {
        URL url = new URL(ConstansGoogle.GOOGLE_LINK_GET_USER_INFO + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String response = new Scanner(conn.getInputStream()).useDelimiter("\\A").next();
        return new Gson().fromJson(response, GoogleUser.class);
    }
}