package com.example.pushreceiver;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.apache.cordova.CallbackContext;

import org.json.JSONObject;
import org.json.JSONException;
import java.io.OutputStream;
import java.io.BufferedReader;
   import java.io.DataOutputStream;
   import java.io.IOException;
   import java.io.InputStreamReader;
   import java.net.HttpURLConnection;
   import java.net.URL;

public class PushReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle the push notification here
        String message = remoteMessage.getNotification().getBody();
        // TODO: Send message to JS via Cordova Plugin bridge

         //Create JSON string
         String jsonInputString="";
         try{
             JSONObject jsonObject = new JSONObject();
            jsonObject.put("Title", message);
            jsonObject.put("Message",message);

            jsonInputString = jsonObject.toString();
         }
         catch(JSONException e) {
               e.printStackTrace();
         }

        //Call REST API to notify about push received on device
        String apiUrl = "https://kiran-nikam.outsystemscloud.com/SampleAPITest/rest/Notify/PostMessage";
        try {
               URL url = new URL(apiUrl); // Replace with your API endpoint
               HttpURLConnection connection = (HttpURLConnection) url.openConnection();
               connection.setRequestMethod("POST"); // Or "GET", "PUT", etc.
               connection.setRequestProperty("Content-Type", "application/json");
               connection.setDoOutput(true);

               DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
               outputStream.writeBytes(jsonInputString);
               outputStream.flush();
               outputStream.close();

               int responseCode = connection.getResponseCode();
               if (responseCode == HttpURLConnection.HTTP_OK) {
                   BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                   StringBuilder response = new StringBuilder();
                   String line;
                   while ((line = reader.readLine()) != null) {
                       response.append(line);
                   }
                   reader.close();
                   //return response.toString();
               } else {
                   //return "Error: " + responseCode;
               }

                connection.disconnect();
           } catch (IOException e) {
               e.printStackTrace();
               //return "Error: " + e.getMessage();
           }








       
        
       
       
    }

    }

