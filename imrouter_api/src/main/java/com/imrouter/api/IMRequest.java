package com.imrouter.api;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.imrouter.annotation.RouterParam;

import java.util.Map;

import androidx.core.app.ActivityCompat;

/**
 * User: maodayu
 * Date: 2019/9/3
 * Time: 15:35
 */
public class IMRequest {
    public  Map<String, RouterParam> routerParamMap;
    private Context                  mContext;

    public static IMRequest getInstance() {
        return SingleIMRequest.imRequest;
    }

    public void build(String path) {
        build(path, 1);
    }

    public IMData build(String path, int requestCode) {
        RouterParam routerParam = routerParamMap.get(path);
        IMData imData = new IMData(routerParam, requestCode);
        return imData;
    }

    public void start(IMData imData) {
        RouterParam routerParam = imData.getParam();
        switch (routerParam.getTarget_type()) {
            case RouterParam.TARGET_ACTIVITY:
                Intent intent = new Intent(mContext, routerParam.getTargetObject());
                intent.putExtras(imData.getBundle());
                startActivity(mContext, intent, imData);
                break;
            case RouterParam.TARGET_FRAGMENT:
                break;
            case RouterParam.TARGET_SERVICE:
                break;
            case RouterParam.TARGET_BROADCAST:
                break;
        }
    }

    public void inits(Application application, Map<String, RouterParam> paramMap) {
        mContext = application;
        routerParamMap = paramMap;
    }

    private void startActivity(Context context, Intent intent, IMData imData) {
        if (imData.getRequestCode() == 1) {
            ActivityCompat.startActivity(context, intent, null);
        } else {
            ActivityCompat.startActivityForResult((Activity) context, intent, imData.getRequestCode(), null);
        }
    }

    public static class SingleIMRequest {
        public static IMRequest imRequest = new IMRequest();
    }
}
