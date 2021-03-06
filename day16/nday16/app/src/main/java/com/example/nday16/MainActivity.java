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
        mapView.onCreate(savedInstanceState);// ?????????????????????
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
                    Toast.makeText(MainActivity.this,"??????????????????",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this,"??????????????????",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this,"??????????????????",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this,"??????????????????",Toast.LENGTH_LONG).show();
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
                                //??????????????????
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
                                                String str = "???????????????"+amapLocation.getErrorCode();
                                                Log.i("zq",str);
                                            }
                                        }

                                    }
                                });
                                //??????????????????????????????
                                mLocationOption.setOnceLocation(true);
                                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                                //??????????????????
                                mlocationClient.setLocationOption(mLocationOption);
                                // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                // ??????????????????????????????????????????????????????????????????2000ms?????????????????????????????????stopLocation()???????????????????????????
                                // ???????????????????????????????????????????????????onDestroy()??????
                                // ?????????????????????????????????????????????????????????????????????stopLocation()???????????????????????????sdk???????????????
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

            aMap.getUiSettings().setMyLocationButtonEnabled(true);// ????????????????????????????????????
            aMap.setMyLocationEnabled(true);// ?????????true??????????????????????????????????????????false??????????????????????????????????????????????????????false
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        }

    }


    protected void doSearchQuery() {
//        Log.i("MY", "doSearchQuery");

        PoiSearch.Query query = new PoiSearch.Query(s1, s2);
        // ????????????????????????????????????????????????????????????poi????????????????????????????????????poi??????????????????????????????????????????
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
                        //????????????????????????
                        .setUseTexture(true)
                        //??????????????????
                        .geodesic(false)
                        //??????????????????
                        .setCustomTexture(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground)))
                        //?????????????????????
                        .color(Color.argb(200, 0, 0, 0)));
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {}

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {}
        });

        if(myLating!=null){
            //?????????????????????????????????????????????
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                    new LatLonPoint(myLating.latitude, myLating.longitude),
                    new LatLonPoint(_end.latitude, _end.longitude));

// fromAndTo???????????????????????????????????????drivingMode??????????????????
// ?????????????????????????????????????????????16?????????  ????????????????????????????????????????????????32??????????????????????????????????????????

            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(
                    fromAndTo, DRIVING_SINGLE_DEFAULT, null, null, "");
            routeSearch.calculateDriveRouteAsyn(query);
        }else{
            Toast.makeText(this,"??????????????????",Toast.LENGTH_LONG).show();
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
                            ToastUtils.showShort("?????????????????????????????????????????????");*/
                        } else {
                            //  ToastUtils.showShort("???????????????");
                        }
                    }
                });

    }


    public void fresh(){
        adapter.clear();
        data.clear();
    }
}