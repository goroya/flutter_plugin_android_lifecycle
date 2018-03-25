import 'dart:async';

import 'package:flutter/services.dart';

const EventChannel _androidLifecycleEventChannel =
    const EventChannel('plugins.goroya.io/android_lifecycle');
Stream<Map<String, dynamic>> _androidLifecycleEvents;

class AndroidLifecycle {
  static const MethodChannel _channel =
      const MethodChannel('android_lifecycle');

  static Future<String> get platformVersion =>
      _channel.invokeMethod('getPlatformVersion');

}
Stream<Map<String, dynamic>> get onAndroidLifeCycleChanged {
  if (_androidLifecycleEvents == null) {
    print("call");
    _androidLifecycleEvents = _androidLifecycleEventChannel.receiveBroadcastStream();
  }
  return _androidLifecycleEvents;
}
