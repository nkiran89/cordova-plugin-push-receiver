package com.example.pushreceiver;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.apache.cordova.CallbackContext;

public class PushReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle the push notification here
        String message = remoteMessage.getNotification().getBody();
        // TODO: Send message to JS via Cordova Plugin bridge

        
    }
}