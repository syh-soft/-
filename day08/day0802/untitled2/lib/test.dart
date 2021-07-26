import 'package:flutter/material.dart';

class Test extends StatefulWidget{
  @override
  State<StatefulWidget> createState(){
    print('create state');
    return TestState();
  }
}

class TestState extends State<Test>{

  @override
  void initState(){
    print('init state');
    super.initState();
  }

  @override
  void didChangeDependencies(){
    print("did change dependencies");
    super.didChangeDependencies();
  }
  @override
  void didUpdateWidget(Test oldWidget){
    print("did update widget");
    super.didUpdateWidget(oldWidget);
  }

  @override
  void deactivate() {
    print('deactivate');// TODO: implement deactivate
    super.deactivate();
  }

  @override
  void dispose() {
    print('dispose');// TODO: implement dispose
    super.dispose();
  }

  @override
  void reassemble() {
    print('reassemble');// TODO: implement reassemble
    super.reassemble();
  }

  void get() {
    setState((){
      print('set state');
    });
  }
  @override
  Widget build(BuildContext context) {
    print('build');// TODO: implement build
    return TextButton(
        onPressed: get,
        child: Text("生命周期测试"));
    throw UnimplementedError();
  }


}