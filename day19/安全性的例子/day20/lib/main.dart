import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "5555",
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: '向后端发送消息'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _username;
  String _password;
  String npassword=" ";
  String token = " ";
  String ntoken = " ";
  bool isShow = true;
  Response response;
  Response response1;
  Response response2;
  String respect=" ";

  GlobalKey<FormState> _loginKey = GlobalKey<FormState>();
  void _login(){
    var _loginForm = _loginKey.currentState;
    if(_loginForm.validate()){
      _loginForm.save();
      setState(() {
        npassword = encryption(_password);
      });

      print('name:$_username,password:$_password');
    }
  }

  Future<void> send() async {
    response = await Dio().post("http://192.168.43.195:8080/demo/user/saveOne", data: {"name":"孙雨寒",
      "password":npassword,
      "phone":"1665",
      "sex":"1514"
    });
  }

  Future<void> que() async {
    response1 = await Dio().post("http://192.168.43.195:8080/demo/user/getMessage", data: {"name":_username,
      "password":npassword,
      "phone":"1665",
      "sex":"1514"
    });
  }

  Future<void> judge() async {
    print(token);
    response2 = await Dio().post("http://192.168.43.195:8080/demo/user/judge", data: {"name":_username,
      "password":npassword,
      "phone":token,
      "sex":"1514"
    });
  }
  

  void get(){
    setState(() {
      respect = response1.toString();
    });
  }

  void get1(){
    setState(() {
      ntoken = response2.toString();
    });
  }

  /*加密*/
   String encryption(String s){
    s = "2020"+s;
    return s;
  }
  /*解密*/
  String decrypt(String s){
    return s.substring(4);
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Column(
        children: <Widget>[
          Container(
            padding: const EdgeInsets.all(10.0),
            child: Form(
              key: _loginKey,
              child: Column(
                children: <Widget>[
                  Container(
                    height: 100,
                  ),
                  TextFormField(
                    decoration: InputDecoration(
                      enabledBorder: OutlineInputBorder(
                        /*边角*/
                        borderRadius: BorderRadius.all(
                          Radius.circular(30), //边角为30
                        ),
                        borderSide: BorderSide(
                          color: Colors.blue, //边线颜色为黄
                          width: 2, //边线宽度为2
                        ),
                      ),
                      focusedBorder: OutlineInputBorder(
                          borderSide: BorderSide(
                            color: Colors.blue, //边框颜色为绿色
                            width: 5, //宽度为5
                          )
                      ),
                      labelText: "请输入用户名",
                      helperText: "账号为邮箱或者手机号",
                    ) ,
                    onSaved: (value){_username = value;},
                    onFieldSubmitted: (value){
                      print('onFieldSubmitted: $value');
                    },
                  ),
                  Container(
                    height: 10,
                  ),
                  TextFormField(
                    obscureText: isShow,
                    decoration: InputDecoration(
                      enabledBorder: OutlineInputBorder(
                        borderRadius: BorderRadius.all(
                          Radius.circular(30),
                        ),
                        borderSide: BorderSide(
                          color: Colors.blue,
                          width: 2,
                        ),
                      ),
                      focusedBorder: OutlineInputBorder(
                          borderSide: BorderSide(
                            color: Colors.blue,
                            width: 5,
                          )
                      ),
                      labelText: "请输入密码",
                      helperText: "密码不能小于六位",
                      suffixIcon: IconButton(
                        icon: Icon(
                          //根据passwordVisible状态显示不同的图标
                          isShow ? Icons.visibility_off : Icons.visibility,
                          color: Theme.of(context).primaryColorDark,
                        ),
                        onPressed: () {
                          //更新状态控制密码显示或隐藏
                          setState(() {
                            isShow = !isShow;
                          });
                        },
                      ),
                    ),
                    onSaved: (value){
                      _password = value;
                      print(1);
                    },
                    onFieldSubmitted: (value){
                      print('onFieldSubmitted: $value');
                    },
                    validator: (String value){
                      return value.length>=6?null:'密码最少6个字符';
                    },
                  ),
                  TextFormField(
                    decoration: InputDecoration(
                      enabledBorder: OutlineInputBorder(
                        /*边角*/
                        borderRadius: BorderRadius.all(
                          Radius.circular(30), //边角为30
                        ),
                        borderSide: BorderSide(
                          color: Colors.blue, //边线颜色为黄
                          width: 2, //边线宽度为2
                        ),
                      ),
                      focusedBorder: OutlineInputBorder(
                          borderSide: BorderSide(
                            color: Colors.blue, //边框颜色为绿色
                            width: 5, //宽度为5
                          )
                      ),
                      labelText: "请输入token",
                    ) ,
                    onChanged: (value){
                      setState(() {
                        token = value;
                      });
                     },
                    onFieldSubmitted: (value){
                      print('onFieldSubmitted: $value');
                    },
                  ),
                  Container(
                    height: 10,
                  ),
                  SizedBox(
                    width: 200,
                    height: 40,
                    child: FlatButton(
                      color: Colors.blue,
                      highlightColor: Colors.blue[700],
                      colorBrightness: Brightness.dark,
                      splashColor: Colors.grey,
                      child: Text("发送"),
                      shape:RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
                      onPressed: () {
                        _login();
                        send();
                      },
                    ),
                  ),
                  Text("加密后的密码是：$npassword"),
                  SizedBox(
                    width: 200,
                    height: 40,
                    child: FlatButton(
                      color: Colors.blue,
                      highlightColor: Colors.blue[700],
                      colorBrightness: Brightness.dark,
                      splashColor: Colors.grey,
                      child: Text("查询"),
                      shape:RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
                      onPressed: () {
                        _login();
                        que();
                        get();
                      },
                    ),
                  ),
                  Text(respect),
                  SizedBox(
                    width: 200,
                    height: 40,
                    child: FlatButton(
                      color: Colors.blue,
                      highlightColor: Colors.blue[700],
                      colorBrightness: Brightness.dark,
                      splashColor: Colors.grey,
                      child: Text("验证token"),
                      shape:RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
                      onPressed: () {
                        _login();
                        judge();
                        get1();
                      },
                    ),
                  ),
                  Text(ntoken)
                ],

              ),
            ),
          )
        ],
      ),
      resizeToAvoidBottomInset: false,
    );
  }
}
