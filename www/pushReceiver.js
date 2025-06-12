var exec = require('cordova/exec');

exports.onNotificationReceived = function(success, error) {
    exec(success, error, "PushReceiver", "onNotificationReceived", []);
};