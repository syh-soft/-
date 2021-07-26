import 'dart:convert';
import 'dart:io';
import 'package:share/share.dart';

import 'package:day15/watermarker.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:image_crop/image_crop.dart';
import 'package:image_picker/image_picker.dart';


class ImageCropScreen extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return _ImageSropScreenState();
  }

}

class _ImageSropScreenState extends State<ImageCropScreen>{

  List<AreaModel> _data =[] ;
  File _image;
  final cropKey = GlobalKey<CropState>();
  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    // Future<String> loadString = DefaultAssetBundle.of(context).loadString("data/currency.json");

    Future<String> loadString = DefaultAssetBundle.of(context).loadString("assets/data/area_data");

    loadString.then((value) => initData(value));


  }

  void initData(String data){
    List<dynamic> _data = json.decode(data);
    for(var i=0;i<_data.length;i++){
      AreaModel  area = AreaModel.formMap(_data[i]);

      this._data.add(area);
      //  _data.add();
    }

    print("====="+this._data[0].children.length.toString());

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("图片处理"),
      ),
      body: Container(
        width: MediaQuery.of(context).size.width,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            _image==null?Text("请选择图片"): 
            Container(
              width: 400,height: 400,
              child: Crop.file(_image,key: cropKey),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                ElevatedButton(onPressed: (){
                  ImagePicker.pickImage(source: ImageSource.gallery).then((value){
                    setState(() {
                      _image = value;
                    });
                  });
                }, child: Text("选择图片")),
                ElevatedButton(onPressed: (){
                  shareImage();
                }, child: Text("分享图片")),
                ElevatedButton(onPressed: (){
                  showCupertinePicker();
                }, child: Text("添加水印"))
              ],
            )
          ],

        ),
      ),
    );
  }




  void cropImage()async{


    final sampledFile = await ImageCrop.sampleImage(
      file: _image,
      preferredWidth: (1024 / cropKey.currentState.scale).round(),
      preferredHeight: (4096 / cropKey.currentState.scale).round(),
    );

    final croppedFile = await ImageCrop.cropImage(
      file: sampledFile,
      area: cropKey.currentState.area,
    );

    setState(() {
      _image = croppedFile;
    });


  }

  FixedExtentScrollController controller1 = FixedExtentScrollController();
  FixedExtentScrollController controller2 =  FixedExtentScrollController();
  int selectPos1 = 0;
  int count=0;
  String name1 = "";
  String name2 = "";
  String name3 = "";


  StateSetter imgsetter;

  void showCupertinePicker(){
    showModalBottomSheet(context: context, builder: (context){
      return StatefulBuilder(builder: (context,state){

        imgsetter=state;
       return Container(
         width: MediaQuery.of(context).size.width,
         padding: EdgeInsets.all(16),
         height: 400,
         child: Column(
           children: <Widget>[
             Row(
               crossAxisAlignment: CrossAxisAlignment.center,
               mainAxisAlignment: MainAxisAlignment.spaceBetween,
               children: [
                 GestureDetector(
                   onTap: (){
                     String s= name1+name2+name3;
                     Navigator.pop(context);
                     addWater(s);

                   },
                   child: Text(
                       "确定"
                   ),
                 ),
                 GestureDetector(
                   onTap: (){
                     imgsetter((){
                       if(name1!=""){
                         name1 = "";
                         count--;
                       }
                     });
                   },
                   child: Text(
                       name1
                   ),
                 ),
                 GestureDetector(
                   onTap: (){
                     imgsetter((){
                       if(name2!=""){
                         name2 = "";
                         count--;
                       }
                     });
                   },
                   child: Text(
                       name2
                   ),
                 ),
                 GestureDetector(
                   onTap: (){
                     imgsetter((){
                       if(name3!=""){
                         name3 = "";
                         count--;
                       }
                     });
                   },
                   child: Text(
                       name3
                   ),
                 ),
                 GestureDetector(
                   onTap: (){
                     Navigator.pop(context);
                   },
                   child: Text(
                     "取消",
                     style: TextStyle(
                         color: Colors.red
                     ),
                   ),
                 )
               ],
             ),
             Expanded(child: Row(
               children: [
                 Expanded(flex:1,
                     child: CupertinoPicker(itemExtent: 48,
                         onSelectedItemChanged: (position){
                           state(() {
                             selectPos1 = position;


                           });

                           controller2.jumpToItem(0);


                         },
                         diameterRatio: 1.1,
                         scrollController: controller1,
                         children: [
                           ...cretaeItem()

                         ])),
                 Expanded(
                   flex:1,
                     child: Column(
                       children: [
                         ...cretaeItem(pos1: selectPos1)
                       ],
                     ),
                 ),
               ],
             ))
           ],
         ),
       );
      });
    });
  }

  List<Widget> cretaeItem({pos1=-1}){
    List<Widget> list = [];

    List<AreaModel> temp = this._data;
    if(pos1!=-1){
      temp = this._data[pos1].children;
    }
    if(pos1!=-1){
      for(int i=0;i<temp.length;i++){
        list.add(
          SizedBox(
            height: 42,
            child: TextButton(
              onPressed: (){

                imgsetter((){
                  change(temp[i].name);
                });

              },
              child: Text(
                temp[i].name,
                style: TextStyle(fontSize: 14.0),
              ),
            ),
          )
        );
      }
    }else{
      for(int i=0;i<temp.length;i++){
        list.add(
            Container(
              padding: EdgeInsets.only(top: 14.0,bottom: 10.0),
              child: Text(
                temp[i].name,
                style: TextStyle(fontSize: 14.0),
              ),
            )
        );
      }
    }


    return list;




  }

  void change(String s){
    if(count<3){
      if(name1==""){
        setState(() {
          name1 = s;
        });

      }
      else if(name2==""){
        setState(() {
          name2 = s;
        });
      }
      else{
        setState(() {
          name3 = s;
        });
      }
      count++;
    }
    else{
      print("已满");
    }
  }

  void addWater( String s) async{
    print(_image);
    String str1 = _image.toString().split("'")[1] ;
    final croppedFile = await imageAddWaterMark(str1,s);
    setState(() {
      _image = croppedFile;
    });

    print(_image);
  }

  void shareImage(){
    Share.shareFiles([_image.path]);
  }


}

class AreaModel{
  String id;
  String parentId;
  String name;
  List<AreaModel> children;
  AreaModel();


  static AreaModel formMap(Map<String,dynamic> map){
    AreaModel area = AreaModel();

    area.id = map['id'].toString();
    area.parentId = map['parentId'].toString();
    area.name = map['name'].toString();
    area.children = List()..addAll(
        (map['children'] as List ?? []).map((e) => AreaModel.formMap(e))
    );

    return area;

  }
}