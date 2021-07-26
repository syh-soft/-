
import 'dart:async';

import 'package:flutter/services.dart';

class Untitled1 {
  static const MethodChannel _channel =
      const MethodChannel('Untitled1Plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<int>  getResult(int a, int b) async {
    Map<String, dynamic> map = {"a": a, "b": b};
    int result = await _channel.invokeMethod("getResult", map);
    print(result.toString()+"----------aa--");
    return result;
  }
}
