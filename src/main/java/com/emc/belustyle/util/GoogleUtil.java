package com.emc.belustyle.util;


import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author ADMIN
 */
public class GoogleUtil {

    private final String GOOGLE_CLIENT_ID = "300845919892-bbvpmkgcep2j7jl8dfk09spmf4lf95sv.apps.googleusercontent.com";
    private final String GOOGLE_CLIENT_SECRET = "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";
    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/api/auth/googleCallback";
    private final String GOOGLE_GRANT_TYPE = "authorization_code";

    // Generate Google OAuth login URL
    public String getGoogleOAuthLoginURL() {
        return "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + GOOGLE_CLIENT_ID
                + "&redirect_uri=" + GOOGLE_REDIRECT_URI
                + "&response_type=code"
                + "&scope=email%20profile";
    }

    // Get email and google_id from authorization code
    public Map<String, String> getGoogleIdAndEmailFromCode(String code) {
        Map<String, String> googleInfo = new HashMap<>();
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://oauth2.googleapis.com/token");

            Map<String, String> parameters = new HashMap<>();
            parameters.put("code", code);
            parameters.put("client_id", GOOGLE_CLIENT_ID);
            parameters.put("client_secret", GOOGLE_CLIENT_SECRET);
            parameters.put("redirect_uri", GOOGLE_REDIRECT_URI);
            parameters.put("grant_type", GOOGLE_GRANT_TYPE);
            String jsonParameters = mapToJson(parameters);

            StringEntity entity = new StringEntity(jsonParameters);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            HttpResponse httpResponse = httpClient.execute(httpPost);

            String responseBody = EntityUtils.toString(httpResponse.getEntity());

            // Extract google_id (sub) and email from the token
            googleInfo = extractGoogleInfoFromToken(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return googleInfo;
    }

    // Convert a Map to a JSON string
    private String mapToJson(Map<String, String> map) {
        StringBuilder jsonBuilder = new StringBuilder("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    // Extract google_id (sub) and email from the token payload
    private Map<String, String> extractGoogleInfoFromToken(String responseBody) {
        Map<String, String> googleInfo = new HashMap<>();

        // Extract the id_token from the response
        String idToken = new Gson().fromJson(responseBody, JsonObject.class).get("id_token").getAsString();
        String[] idTokenParts = idToken.split("\\.");
        String encodedPayload = idTokenParts[1];

        // Decode the payload part of the ID token
        String payload = new String(Base64.getDecoder().decode(encodedPayload));
        JsonObject payloadObject = JsonParser.parseString(payload).getAsJsonObject();

        // Extract google_id (sub), email, name, and picture URL
        String googleId = payloadObject.get("sub").getAsString();
        String email = payloadObject.get("email").getAsString();
        String name = payloadObject.has("name") ? payloadObject.get("name").getAsString() : "No Name Provided";
        String pictureUrl = payloadObject.has("picture") ? payloadObject.get("picture").getAsString() : "No Picture URL Provided";

        // Add the extracted data to the map
        googleInfo.put("google_id", googleId);
        googleInfo.put("email", email);
        googleInfo.put("name", name);
        googleInfo.put("picture", pictureUrl);

        return googleInfo;
    }

}
