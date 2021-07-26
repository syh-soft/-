import 'dart:async';
import 'dart:io';

import 'package:audioplayers/audio_cache.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:image_picker/image_picker.dart';
import 'package:audioplayers/audioplayers.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {


    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );

  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  //int _counter = 0;

  AudioCache audioCache = AudioCache();
  var judge = 0;
  var _imgPath;
  AudioPlayer audioPlayer = AudioPlayer();

  /*图片控件*/
  Widget _ImageView(imgPath) {
    if (imgPath == null) {
      return Center(
        child: Text("请选择图片或拍照"),
      );
    } else {
      return Image.file(
        imgPath,
      );
    }
  }
  //播放
  play() async {
    final result = await audioCache.play('music/1.mp3');
    if (result == 1) {
      judge = 1;// success
      print('play success');
    } else {
      print('play failed');
    }
  }
  //暂停
  pause() async {
    audioCache.fixedPlayer = audioPlayer;
    int result = await audioPlayer.pause();
    if (result == 1) {
      judge = 0;// success
      print('pause success');
    } else {
      print('pause failed');
    }
  }
  //停止
  stop() async {
    audioCache.fixedPlayer = audioPlayer;
    int result = await audioPlayer.stop();
    if (result == 1) {
      judge = 0;
      print('pause success');
    } else {
      print('pause failed');
    }
  }

  /*打开相册*/
  _openGallery() async {
    var image = await ImagePicker.pickImage(source: ImageSource.gallery);
    setState(() {
      _imgPath = image;
    });
  }



  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            TextButton(
              onPressed:(){
                play();
              },
                child: Text("播放")
            ),
            TextButton(
                onPressed:(){
                  pause();
                },
                child: Text("暂停")
            ),
            TextButton(
                onPressed:(){
                  stop();
                },
                child: Text("停止")
            ),
            Container(
              width: 128.0,
              height: 128.0,
              decoration: new BoxDecoration(
                color: Colors.lightBlueAccent[100],
              ),
              transform: Matrix4.rotationZ(1),
            ),
            FlatButton(
              color: Colors.blue,
              highlightColor: Colors.blue[700],
              colorBrightness: Brightness.dark,
              splashColor: Colors.grey,
              child: Text("选择照片"),
              shape:RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
              onPressed: _openGallery,
            ),
            _ImageView(_imgPath),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: (){
          showDialog(
              context: context,
              builder:(BuildContext context){
                return  AlertDialog(
                  title: Text("提示"),
                  content: Text("您确定要更新吗？"),
                  actions: <Widget>[
                    TextButton(
                      child: Text("确定"),
                      onPressed: () {
                        Fluttertoast.showToast(
                            msg: "更新完成",
                            toastLength: Toast.LENGTH_SHORT,
                            gravity: ToastGravity.BOTTOM,
                            timeInSecForIosWeb: 1,
                            backgroundColor: Colors.black45,
                            textColor: Colors.white,
                            fontSize: 16.0
                        );
                        Navigator.of(context).pop(true);
                      }, //关闭对话框
                    ),
                    TextButton(
                      child: Text("取消"),
                      onPressed: () {
                        Fluttertoast.showToast(
                            msg: "已取消",
                            toastLength: Toast.LENGTH_SHORT,
                            gravity: ToastGravity.BOTTOM,
                            timeInSecForIosWeb: 1,
                            backgroundColor: Colors.black45,
                            textColor: Colors.white,
                            fontSize: 16.0
                        );
                        Navigator.of(context).pop(true);
                      }, //关闭对话框
                    ),
                  ],
                ) ;
              }
          );
        },
        tooltip: 'Increment',
        child:Text("点击更新"),

      ),
      // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  @override
  void initState() {
    super.initState();
    // 避免调用Alert时没有加载完父级页面，延迟1秒调用
    Timer(Duration(seconds: 1), () => stop());
  }


}
