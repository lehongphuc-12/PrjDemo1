package LoginGoogle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import model.User;

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

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            System.err.println("[ERROR] Failed to get access token. HTTP Code: " + responseCode);
            String errorResponse = new Scanner(conn.getErrorStream()).useDelimiter("\\A").next();
            System.err.println("[ERROR] Google Error Response: " + errorResponse);
            return null;
        }

        String response = new Scanner(conn.getInputStream()).useDelimiter("\\A").next();
        System.out.println("[INFO] Access token response: " + response);

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }

    public static User getUserInfo(String accessToken) throws IOException {
        URL url = new URL(ConstansGoogle.GOOGLE_LINK_GET_USER_INFO + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            System.err.println("[ERROR] Failed to fetch user info. HTTP Code: " + responseCode);
            String error = new Scanner(conn.getErrorStream()).useDelimiter("\\A").next();
            System.err.println("[ERROR] User info error response: " + error);
            return null;
        }

        String response = new Scanner(conn.getInputStream()).useDelimiter("\\A").next();
        System.out.println("[INFO] User Info JSON: " + response);

        return new Gson().fromJson(response, User.class);
    }

}
