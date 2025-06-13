var exec = require('cordova/exec');

exports.onNotificationReceived = function (callback) {
  exec(callback, null, 'PushReceiver', 'onNotificationReceived', []);
};

// exports.onNotificationReceived = function(success, error) {
//     exec(success, error, "PushReceiver", "onNotificationReceived", []);
// };

module.exports = function (ctx) {
    console.log("RUNNING HOOK NOW!");    
    throw new Error(`OUTSYSTEMS_PLUGIN_ERROR: Error occurred on ${ctx.hook}`)
};



