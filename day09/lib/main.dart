import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

void main() {
  runApp(MyApp());
}

class User{
  final String username;
  final String description;
  User(this.username,this.description);

}

class Detail extends StatelessWidget{
  final User user;
  Detail({Key key,@required this.user}):super(key:key);

  @override
  Widget build(BuildContext context){
    return new Scaffold(
      appBar: AppBar(
        title:Text('${user.username}')
      ),
      body: Center(child: Text('${user.description}'),)
    );
  }
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
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final List<String> items = List<String>.generate(50, (index) => null);


  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: ListView.builder(
              itemCount: 50,
              itemBuilder: (context,index){
                print(index);
              var a = index  + 1;
              final item = items[index];
              User user = new User('No.$a', '这是第$a个用户');
              return Dismissible(
                  key: Key(item),
                  onDismissed: (direction) {
                    if(DismissDirection.endToStart == direction){
                      Fluttertoast.showToast(
                          msg: "已删除",
                          toastLength: Toast.LENGTH_SHORT,
                          gravity: ToastGravity.BOTTOM,
                          timeInSecForIosWeb: 1,
                          backgroundColor: Colors.black45,
                          textColor: Colors.white,
                          fontSize: 16.0
                      );
                        items.removeAt(index);

                    }

                  },
                  secondaryBackground: Container(
                    child: Text("左滑删除"),
                    color: Colors.red,
                    padding: EdgeInsets.only(right: 30),
                    alignment: Alignment.center,
                  ),
                  background: Container(
                    color: Colors.white,
                    padding: EdgeInsets.only(right: 30),
                    alignment: Alignment.center,
                  ),
                  confirmDismiss: (direction) async {
                   if(DismissDirection.endToStart == direction){
                    return true;
                  }
                    else{
                    return false;
                  }

                  },
                  child: ListTile(
                    leading: Image.asset("images/${index%3+1}.jpg"),
                    title: Text('No.$a'),
                    subtitle: Text('这是第$a个用户'),
                    trailing: Icon(Icons.arrow_forward_ios),
                    onTap: (){
                      Navigator.push(context, MaterialPageRoute(
                            builder: (context) => Detail(
                              user: user,
                          )
                      )
                      );
                    },
                  ),
              );

           },
        ),
      )

    );
  }
}

