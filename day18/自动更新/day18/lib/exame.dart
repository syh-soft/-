

import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:install_plugin/install_plugin.dart';
import 'package:package_info/package_info.dart';
import 'package:path_provider/path_provider.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:progress_dialog/progress_dialog.dart';

class Exame extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _ExamineState();
  }
}

class _ExamineState extends State<Exame> {

  String localVersion;
  var dio = new Dio();
  Response response;
  String serverAndroidVersion;
  PackageInfo packageInfo;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    judgeVersion();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("测试"),
      ),
      body: Container(
        child: TextButton(
          child: Text("点击"),
          onPressed: judgeVersion,
        ),
      ),
    );
  }

  Future<void> judgeVersion() async {
    packageInfo = await PackageInfo.fromPlatform();
    localVersion = packageInfo.version;
    response = await dio.get('http://192.168.43.195:8080/demo/user/queryAPK');
    serverAndroidVersion = response.data["versionname"];
    int c = serverAndroidVersion.compareTo(localVersion);
    if(c != 1){
      showUpdate(context, serverAndroidVersion,"http://192.168.43.195:8080/demo/user/download");
     }


  }

  Future<void> showUpdate(BuildContext context, String version ,String url) async {
    return showDialog<void>(
      context: context,
      barrierDismissible: true,
      builder: (BuildContext context) {
        return CupertinoAlertDialog(
          title: Text('检测到新版本 v$version'),
          content: Text('是否要更新到最新版本?'),
          actions: <Widget>[
            FlatButton(
              child: Text('下次在说'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            FlatButton(
              child: Text('立即更新'),
              onPressed: () {
               installApk(url);
              },
            ),
          ],
        );
      },
    );
  }

  Future<bool> checkPermission() async {
    PermissionStatus permission = await PermissionHandler()
        .checkPermissionStatus(PermissionGroup.storage);
    if (permission != PermissionStatus.granted) {
      Map<PermissionGroup, PermissionStatus> permissions =
          await PermissionHandler()
              .requestPermissions([PermissionGroup.storage]);
      if (permissions[PermissionGroup.storage] == PermissionStatus.granted) {
        return true;
      }
    } else {
      return true;
    }
    return false;
  }

  Future<Null> installApk(String url) async {
    File apk = await downloadAndroid(url);
    print(123);
    String _apkFilePath = apk.path;
    if (_apkFilePath.isEmpty) {
      print('make sure the apk file is set');
      return;
    }
    InstallPlugin.installApk(_apkFilePath,"com.example.day18").then((result) {
      print('install apk $result');
    }).catchError((error) {
      print('install apk error: $error');
    });
  }

  Future<File> downloadAndroid(String url) async {
    Navigator.of(context).pop();
    checkPermission();
    /// 创建存储文件
    Directory storageDir = await getExternalStorageDirectory();
    String storagePath = storageDir.path+"/";
    String appName = '${packageInfo.appName}v${serverAndroidVersion}.apk';
    //File file = new File('$storagePath/${packageInfo.appName}v${serverAndroidVersion}.apk');
    double currentProgress =0.0;
    print(storagePath+appName);
    Response response1 = await dio.download(
        url, "$storagePath$appName", onReceiveProgress: (received, total) {
      if (total != -1) {
        ///当前下载的百分比例
        print(123);
        print((received / total * 100).toStringAsFixed(0) + "%");
        // CircularProgressIndicator(value: currentProgress,) 进度 0-1
        currentProgress = received / total;
        setState(() {
        });
      }
    });
    File file1 = new File("$storagePath$appName");
    return file1;
  }

}

  // Future<Response> dioDownload(String url, progressCallback) async {
  //   Dio dio = Dio();
  //   CancelToken cancelToken = CancelToken();//可以用来取消操作
  //   String docPath = await Application.fileUtil.getDocPath();//获取document目录
  //   String file = docPath + '/' + Application.util.guid() + extension(url);//本地文件名
  //   Response response = await dio.download(url, file,
  //       onReceiveProgress: progressCallback == null
  //           ? null
  //           : (int count, int total) {
  //         if (total == -1) {
  //           //不知道进度的默认50%
  //           total = count * 2;
  //         }
  //         progressCallback(count, total, cancelToken);
  //       },
  //       cancelToken: cancelToken);
  //   response.extra = <String, dynamic>{"localPath": file};
  //   return response;
  // }



