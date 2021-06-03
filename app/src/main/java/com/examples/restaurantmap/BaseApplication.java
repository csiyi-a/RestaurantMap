package com.examples.restaurantmap;

import android.app.Application;

import android.content.Context;

import android.util.DisplayMetrics;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;


/**
 * BaseApplication
 */
public class BaseApplication extends Application {
    public static BaseApplication application;
    public static BaseApplication getAppContext(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        SDKInitializer.initialize(this);

        SDKInitializer.setCoordType(CoordType.BD09LL);

    }

}
