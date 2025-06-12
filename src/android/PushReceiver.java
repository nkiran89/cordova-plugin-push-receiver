package com.example.pushreceiver;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.apache.cordova.CallbackContext;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle the push notification here
        String message = remoteMessage.getNotification().getBody();
        // TODO: Send message to JS via Cordova Plugin bridge

         //Create JSON string
        JSONObject jsonObject = new JSONObject();
            jsonObject.put("Title", message);
            jsonObject.put("Message",message);

            String jsonInputString = jsonObject.toString();

        //Call REST API to notify about push received on device
        String apiUrl = "https://kiran-nikam.outsystemscloud.com/SampleAPITest/rest/Notify/PostMessage";
        
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        //connection.setRequestProperty("Authorization", "Bearer your_api_key");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("Response: " + response.toString());
            }
        } else {
            System.out.println("Error: " + responseCode);
        }

        connection.disconnect();
    }

    }
}