package com.imrouter.api;

import android.content.Context;
import android.os.Bundle;

import com.imrouter.annotation.RouterParam;

/**
 * User: maodayu
 * Date: 2019/9/3
 * Time: 15:36
 */
public class IMData {

    private RouterParam mParam;
    private int         requestCode;
    private Bundle      mBundle;

    private Context context;

    public IMData(RouterParam routerParam) {
        this(routerParam, 1);
    }

    public IMData(RouterParam routerParam, int requestCode) {
        this(null,routerParam,requestCode);
    }

    public IMData(Context context,RouterParam routerParam, int requestCode){
        this.context=context;
        this.mParam = routerParam;
        this.requestCode = requestCode;
        mBundle = new Bundle();
    }

    public IMData withRequestCode(int requestCode){
        this.requestCode = requestCode;
        return this;
    }

    public IMData withInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public void start() {
        IMRouter.getInstance().start(context,this);
    }

    public RouterParam getParam() {
        return mParam;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    @Override
    public String toString() {
        return "IMData{" +
                "mParam=" + mParam +
                ", requestCode=" + requestCode +
                ", mBundle=" + mBundle +
                ", context=" + context +
                '}';
    }
}
