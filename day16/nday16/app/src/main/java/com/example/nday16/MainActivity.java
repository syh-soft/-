package com.example.nday16;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.amap.api.services.route.RouteSearch.DRIVING_SINGLE_DEFAULT;

public class MainActivity extends AppCompatActivity {
    AMap aMap = null;
    MapView mapView = null;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    LocationSource.OnLocationChangedListener mListener;
    LatLng myLating  = null;
    RouteSearch routeSearch;
    private LatLonPoint searchLatlonPoint;
    private PoiSearch poiSearch;
    String s1;
    String s2;
    PoiResult relt;


    private ArrayList<String> data = new ArrayList<String>();
    private ListView listView = null;
    private ArrayAdapter<String> adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initPermissions();
        init();
        Button bt1 = findViewById(R.id.bt1);
        Button bt2 = findViewById(R.id.bt2);
        Button bt3 = findViewById(R.id.bt3);
        Button bt4 = findViewById(R.id.bt4);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fresh();
                EditText editText = findViewById(R.id.text);
                if(editText.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this,"请输入目的地",Toast.LENGTH_LONG).show();
                }
                else{
                    s1 = editText.getText().toString();
                    s2 = bt1.getText().toString();
                    doSearchQuery();
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fresh();
                EditText editText = findViewById(R.id.text);
                if(editText.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this,"请输入目的地",Toast.LENGTH_LONG).show();
                }
                else{
                    s1 = editText.getText().toString();
                    s2 = bt2.getText().toString();
                    doSearchQuery();
                }
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fresh();
                EditText editText = findViewById(R.id.text);
                if(editText.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this,"请输入目的地",Toast.LENGTH_LONG).show();
                }
                else{
                    s1 = editText.getText().toString();
                    s2 = bt3.getText().toString();
                    doSearchQuery();
                }
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fresh();
                EditText editText = findViewById(R.id.text);
                if(editText.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this,"请输入目的地",Toast.LENGTH_LONG).show();
                }
                else{
                    s1 = editText.getText().toString();
                    s2 = bt4.getText().toString();
                    doSearchQuery();
                }
            }
        });

        listView = (ListView)findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatLng a = new LatLng(relt.getPois().get(position).getLatLonPoint().getLatitude(),relt.getPois().get(position).getLatLonPoint().getLongitude());
                router(a);


                }
            }
        );




    }


    private void init(){
        if(aMap == null){
            aMap = mapView.getMap();
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.setLocationSource(
                    new LocationSource() {
                        @Override
                        public void activate(OnLocationChangedListener listener) {
                            mListener = listener;
                            if (mlocationClient == null) {
                                mlocationClient = new AMapLocationClient(MainActivity.this);
                                mLocationOption = new AMapLocationClientOption();
                                //设置定位监听
                                mlocationClient.setLocationListener(new AMapLocationListener(){

                                    @Override
                                    public void onLocationChanged(AMapLocation amapLocation) {
                                        if(mListener!=null && amapLocation!=null){
                                            if(amapLocation!=null && amapLocation.getErrorCode()==0){
                                                mListener.onLocationChanged(amapLocation);
                                                LatLng curLalng = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                                                myLating = curLalng;
                                                searchLatlonPoint = new LatLonPoint(curLalng.latitude, curLalng.longitude);
                                                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLalng,16f));
                                            }else {
                                                String str = "定位失败："+amapLocation.getErrorCode();
                                                Log.i("zq",str);
                                            }
                                        }

                                    }
                                });
                                //设置为高精度定位模式
                                mLocationOption.setOnceLocation(true);
                                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                                //设置定位参数
                                mlocationClient.setLocationOption(mLocationOption);
                                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                                mlocationClient.startLocation();
                            }
                        }

                        @Override
                        public void deactivate() {
                        }
                    }
            );
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    //doSearchQuery();
                }
            });

            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        }

    }


    protected void doSearchQuery() {
//        Log.i("MY", "doSearchQuery");

        PoiSearch.Query query = new PoiSearch.Query(s1, s2);
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setCityLimit(true);
        query.setPageSize(20);
        query.setPageNum(1);
        query.setExtensions(PoiSearch.EXTENSIONS_ALL);
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener(){
                @Override
                public void onPoiSearched(PoiResult poiResult, int i) {
                    if(poiResult!=null && poiResult.getQuery()!=null){
                        if(poiResult.getQuery().equals(query)){
                            relt = poiResult;
                            for(int j = 0; j < poiResult.getPois().size();j++){
                                data.add(poiResult.getPois().get(j).toString());
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();
    }



    public void router(LatLng _end){
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener(){
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {}

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                Log.e("CF", "onDriveRouteSearched: " + i);
                List<DrivePath> pathList = driveRouteResult.getPaths();
                List<LatLng> driverPath = new ArrayList<>();
                for (DrivePath dp : pathList) {
                    List<DriveStep> stepList = dp.getSteps();
                    for (DriveStep ds : stepList) {
                        List<LatLonPoint> points = ds.getPolyline();
                        for (LatLonPoint llp : points) {
                            driverPath.add(new LatLng(llp.getLatitude(), llp.getLongitude()));
                        }
                    }
                }

                aMap.clear();
                aMap.addPolyline(new PolylineOptions()
                        .addAll(driverPath)
                        .width(40)
                        //是否开启纹理贴图
                        .setUseTexture(true)
                        //绘制成大地线
                        .geodesic(false)
                        //设置纹理样式
                        .setCustomTexture(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground)))
                        //设置画线的颜色
                        .color(Color.argb(200, 0, 0, 0)));
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {}

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {}
        });

        if(myLating!=null){
            //构建开始点与终止点的经纬度数据
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                    new LatLonPoint(myLating.latitude, myLating.longitude),
                    new LatLonPoint(_end.latitude, _end.longitude));

// fromAndTo包含路径规划的起点和终点，drivingMode表示驾车模式
// 第三个参数表示途经点（最多支持16个），  第四个参数表示避让区域（最多支持32个），第五个参数表示避让道路

            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(
                    fromAndTo, DRIVING_SINGLE_DEFAULT, null, null, "");
            routeSearch.calculateDriveRouteAsyn(query);
        }else{
            Toast.makeText(this,"当前位置为空",Toast.LENGTH_LONG).show();
        }
    }

    public void initPermissions(){
        RxPermissions rxPermissions = new RxPermissions( MainActivity.this);
        rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                      /*      initPosition();
                            ToastUtils.showShort("相机权限已经打开，直接跳入相机");*/
                        } else {
                            //  ToastUtils.showShort("权限被拒绝");
                        }
                    }
                });

    }


    public void fresh(){
        adapter.clear();
        data.clear();
    }
}