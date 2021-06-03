package com.examples.restaurantmap;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class MarkerDemo extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;


    private final BitmapDescriptor bitmapE = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        mMapView = findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(13.0f);
        mBaiduMap.setMapStatus(mapStatusUpdate);

        initMarker();
        initListener();
    }

    public void initMarker() {

        List<LatLng> latLngs = new ArrayList<>();

        LatLng lng = AddActivity.latLng;
        if (lng!=null){
            latLngs.add(lng);
        }else{
            latLngs.addAll(Utils.getList(MarkerDemo.this));
        }

        if (latLngs.size()==0){
            Toast.makeText(MarkerDemo.this,"Please add",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLngs.get(0)));

        for (LatLng latLng : latLngs) {

            MarkerOptions markerOptionsA = new MarkerOptions()
                    .position(latLng)
                    .icon(bitmapE)
                    .zIndex(9)
                    .draggable(true);
            markerOptionsA.animateType(MarkerOptions.MarkerAnimateType.drop);

            mBaiduMap.addOverlay(markerOptionsA);

        }


    }

    public void initListener() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        bitmapE.recycle();
        mBaiduMap.clear();
        mMapView.onDestroy();
    }
}
