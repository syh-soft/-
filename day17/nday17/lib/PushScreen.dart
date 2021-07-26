

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:jpush_flutter/jpush_flutter.dart';

String tag;

class PushScreen extends StatefulWidget{
  String result;
  PushScreen({Key key, @required this.result}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    tag = result;
    return _PushScreenState();
  }

}

class _PushScreenState extends State<PushScreen> {

  Response response;
  Dio dio = Dio();
  JPush jPush = new JPush();
  String debugLabel = "";
  String content = "";
  List widgets = [];
  List<String> widgeta = [];
  dynamic a;
  dynamic b;



  Future<void> initPlatFormState() async{
    String platform = "";
    jPush.setTags([tag]);

    try {
      jPush.addEventHandler(
          onReceiveNotification: (Map<String, dynamic> message) async {
            print("接收到的通知是:$message");
            a = message["title"];
            b = message["extras"]["cn.jpush.android.ALERT"];
            setState(() {
              widgeta.add(a.toString());
              widgets.add(b.toString());
            });
          },
          onOpenNotification: (Map<String, dynamic> message) async {
            print("通过点击推送进入app：$message");

          },
          onReceiveMessage: (Map<String, dynamic> message) async {
            print("接收到自定义消息：$message");
          },
          onReceiveNotificationAuthorization: (
              Map<String, dynamic> message) async {
            print("通知权限发生变更：$message");
          });
    }on PlatformException{
      platform = "平台版本获取失败";
    }

    jPush.setup(
      appKey: "e3e657823f3f32d6b13e97e5",
      channel: "theChannel",
      production: false,
      debug: true
    );

    jPush.applyPushAuthority(new NotificationSettingsIOS(sound: true,alert: true,badge: true));

    jPush.getRegistrationID().then((rid) {
      print("获得的注册id为："+rid);
      setState(() {
        debugLabel = "当前注册id为：$rid";
      });
    });


  }



  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    initPlatFormState();
  }

  Future<void> send() async {
    response = await dio.post("http://192.168.43.195:8080/demo/user/sendMessage", data: {
      "id":152,
      "name":"孙雨寒",
      "password":content,
      "phone":tag,
      "sex":"1514"
    });
  }


  void sendMessage(){
/*    var fireDate = DateTime.fromMillisecondsSinceEpoch(DateTime.now().millisecondsSinceEpoch+3000);

    var localNotification = LocalNotification(id: 1, title: "定时推送", content: "发送内容",
        fireTime: fireDate,badge: 5,extra: {"data":"附带数据"});

    jPush.sendLocalNotification(localNotification);*/

    var fireDate = DateTime.fromMillisecondsSinceEpoch(DateTime.now().millisecondsSinceEpoch + 3000);
    var localNotification = LocalNotification(
        id: 1,
        title: '孙雨寒',
        buildId: 1,
        content: '三秒后本地推送',
        fireTime: fireDate,
        subtitle: '子标题',
        badge: 5,
        extra: {"data": "附带数据"}
    );
    jPush.sendLocalNotification(localNotification);

  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("推送测试"),
      ),
      body: Container(
        width: MediaQuery
            .of(context)
            .size
            .width,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Container(
              height: 10,
            ),
            Container(
              width: MediaQuery
                  .of(context)
                  .size
                  .width,
              height: 400,
              child: new ListView.builder(
                  itemCount: widgets.length,
                  itemBuilder: (BuildContext context, int position) {
                    if(widgeta[position] == "孙雨寒"){
                      return ListTile(
                          trailing: Icon(Icons.person),
                          title: Transform(
                          transform: Matrix4.translationValues(0, 20, 0.0),
                          child: Text(widgets[position].toString(),
                              style: TextStyle(fontSize: 18)),
                           ),
                          subtitle: Transform(
                          transform: Matrix4.translationValues(300, 10.0, 0.0),
                          child: Text(widgeta[position],
                              style: TextStyle(fontSize: 10)),
                        ),


                      );
                    }
                    else{
                      return ListTile(
                          leading: Icon(Icons.person),
                        title: Transform(
                          transform: Matrix4.translationValues(0, 20, 0.0),
                          child: Text(widgets[position].toString(),
                              style: TextStyle(fontSize: 18)),
                        ),
                          subtitle: Transform(
                          transform: Matrix4.translationValues(-60, 10.0, 0.0),
                          child: Text(widgeta[position],
                              style: TextStyle(fontSize: 10)),
                        ),
                      );
                    }
                  })
              ),
            Row(
              children: <Widget>[
                Expanded(
                  flex: 2,
                  child: TextField(
                  autofocus: true,
                  decoration: InputDecoration(
                    hintText: "请输入内容",
                  ),
                  onChanged:(v){
                    content = v.toString();
                  },
                ),),
                Expanded(
                  flex: 1,
                  child: ElevatedButton(onPressed: () {
                  send();
                }, child: Text("发送消息")),)
              ],
            ),

          ],
        ),
      ),
      resizeToAvoidBottomInset: false,
    );
  }


}
