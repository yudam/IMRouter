package com.imrouter.api;

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

    public IMData(RouterParam routerParam) {
        this(routerParam, 1);
    }

    public IMData(RouterParam routerParam, int requestCode) {
        this.mParam = routerParam;
        this.requestCode = requestCode;
        mBundle = new Bundle();
    }

    public IMData withInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public void start() {
        IMRouter.getInstance().start(this);
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
}
