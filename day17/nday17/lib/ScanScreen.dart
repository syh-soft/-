import 'package:flutter/material.dart';
import 'package:qrscan/qrscan.dart' as scanner;
import 'dart:typed_data';

import 'package:nday17/PushScreen.dart';

class ScanScreen extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return _ScanScreenState();
  }

}

class _ScanScreenState extends State<ScanScreen>{

  Uint8List bytes;


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("扫码测试"),
      ),
      body: Container(
        width: MediaQuery.of(context).size.width,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Text("结果是：${result}"),
            this.bytes==null?Text("图片待生成"):Image.memory(bytes),
            ElevatedButton(onPressed: (){
              scan();
            }, child: Text("进行扫码")),
            ElevatedButton(onPressed: (){
              if(result!=null){
                Navigator.push(context, MaterialPageRoute(builder: (context)=>PushScreen(result: result,)));
              }
            }, child: Text("跳转"))
          ],
        ),
      ),
    );
  }


  Future _generateBarCode(String inputCode) async {
    Uint8List result = await scanner.generateBarCode(inputCode);
    this.setState(() => this.bytes = result);
  }


  String result;

  void scan() async{
    String barcode = await scanner.scan();
    setState(() {
      result = barcode;
    });

    //String result= await scanner.scan();
  }

}