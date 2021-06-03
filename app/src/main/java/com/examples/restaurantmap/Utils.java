package com.examples.restaurantmap;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String KEY_LOCATION = "KEY_LOCATION";

    public static void save(Context context, LatLng latLng) {

        List<MyLatLng> latLngs  = getMyLatLngList(context);

        MyLatLng myLatLng = new MyLatLng();
        myLatLng.setLatitude(latLng.latitude);
        myLatLng.setLongitude(latLng.longitude);

        latLngs.add(myLatLng);

        SharedPreferencesUtil.getInstance(context).putSP(KEY_LOCATION, JSON.toJSONString(latLngs));
    }

    public static List<LatLng> getList(Context context) {

        List<LatLng> latLngs = new ArrayList<>();
        List<MyLatLng>  myLatLngs = getMyLatLngList(context);

        for (MyLatLng myLatLng:myLatLngs){
            latLngs.add( new LatLng(myLatLng.getLatitude(),myLatLng.getLongitude()));
        }

        return latLngs;
    }

    private static List<MyLatLng> getMyLatLngList(Context context) {

        List<MyLatLng>  myLatLngs;
        String string = SharedPreferencesUtil.getInstance(context).getString(KEY_LOCATION);

        if (string != null && string.length() != 0) {
            myLatLngs = JSON.parseArray(string, MyLatLng.class);
        } else{
            myLatLngs = new ArrayList<>();
        }

        return myLatLngs;
    }

}
