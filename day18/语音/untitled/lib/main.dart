import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_downloader/flutter_downloader.dart';
import 'package:package_info/package_info.dart';
import 'package:path_provider/path_provider.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:untitled/utils/mic_manage.dart';
import 'package:untitled/utils/xf_manage.dart';

void main() {
  runApp(MaterialApp(
    home: HomePage(),
  ));
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: WsWidgetPage(),
    );
  }
}

const host = 'iat-api.xfyun.cn';
const appId = '581f68b6';
const apiKey = 'b871f6ec2bbacb16d44b7c30e1d2a13f';
const apiSecret = 'ZWMwNTI0YWQ2ZTJjYWZjYzFiYTMxNmQz';


class WsWidgetPage extends StatefulWidget {
  @override
  _WsWidgetPageState createState() => _WsWidgetPageState();
}

class _WsWidgetPageState extends State<WsWidgetPage> {
  String _msg = '等待中...';
  XfManage _xf;

  @override
  void dispose() {
    _xf?.close();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('讯飞语音转文字测试'),
      ),
      body: Column(
        children: [
          RaisedButton(
            onPressed: () {
              MicRecord.startListening();
              setState(() {
                _msg = '录音中..';
              });
            },
            child: Text('开始录音'),
          ),
          RaisedButton(
            onPressed: connect,
            child: Text('停止录音'),
          ),
          Container(
            height: 20,
          ),
          Center(child: Text(_msg)),
        ],
      ),
    );
  }

  connect() async {
    MicRecord.stopListening();
    setState(() {
      _msg = '录音停止,正在语音转文字...';
    });

    _xf = XfManage.connect(
      host,
      apiKey,
      apiSecret,
      appId,
      await MicRecord.currentSamples(),
      (msg) {
        setState(() {
          _msg = '语音转文字完成: \r\n$msg';
        });
      },
    );
  }

}
