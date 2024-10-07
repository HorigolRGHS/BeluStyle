package com.emc.belustyle.util;


import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class GoogleUtil {

    public Map<String, String> getGoogleInfoFromToken(String accessToken) {
        Map<String, String> googleInfo = new HashMap<>();
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken);

            httpGet.setHeader("Authorization", "Bearer " + accessToken);
            httpGet.setHeader("Content-Type", "application/json");

            HttpResponse httpResponse = httpClient.execute(httpGet);

            String responseBody = EntityUtils.toString(httpResponse.getEntity());

            // Parse the response to extract google_id (sub), email, name, and picture
            googleInfo = extractGoogleInfoFromResponse(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return googleInfo;
    }


    public Map<String, String> extractGoogleInfoFromResponse(String responseBody) {
        Map<String, String> googleInfo = new HashMap<>();
        try {
            // Parse the response body string as a JSON object
            org.json.JSONObject jsonObject = new JSONObject(responseBody);

            // Extract fields from the JSON object
            googleInfo.put("google_id", jsonObject.getString("id"));
            googleInfo.put("email", jsonObject.getString("email"));
            googleInfo.put("name", jsonObject.getString("name"));
            googleInfo.put("picture", jsonObject.getString("picture"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googleInfo;
    }



}
