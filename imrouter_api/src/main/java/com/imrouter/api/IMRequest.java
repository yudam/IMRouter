package com.imrouter.api;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.imrouter.annotation.RouterParam;

import java.util.Map;

import androidx.core.app.ActivityCompat;

/**
 * User: maodayu
 * Date: 2019/9/3
 * Time: 15:35
 */
public class IMRequest {
    public Map<String, RouterParam> routerParamMap;
    private Context mContext;

    public static IMRequest getInstance() {
        return SingleIMRequest.imRequest;
    }

    public void inits(Application application, Map<String, RouterParam> paramMap) {
        mContext = application;
        routerParamMap = paramMap;
    }

    public IMData build(String path) {
        RouterParam routerParam = routerParamMap.get(path);
        IMData imData = new IMData(routerParam, 1);
        return imData;
    }

    public IMData build(Context context, String path) {
        RouterParam routerParam = routerParamMap.get(path);
        if (routerParam == null) {
            //TODO 未匹配到指定页面，抛出异常或提示
        }
        IMData imData = new IMData(context, routerParam, 1);
        return imData;
    }

    public IMData build(String path, int requestCode) {
        RouterParam routerParam = routerParamMap.get(path);
        IMData imData = new IMData(routerParam, requestCode);
        return imData;
    }

    public void start(Context context, IMData imData) {
        Context aContext = context == null ? mContext : context;
        RouterParam routerParam = imData.getParam();
        switch (routerParam.getTarget_type()) {
            case RouterParam.TARGET_ACTIVITY:
                Intent intent = new Intent(aContext, routerParam.getTargetObject());
                intent.putExtras(imData.getBundle());
                startActivity(aContext, intent, imData);
                break;
            case RouterParam.TARGET_FRAGMENT:
                break;
            case RouterParam.TARGET_SERVICE:
                break;
            case RouterParam.TARGET_BROADCAST:
                break;
        }
    }

    private void startActivity(Context context, Intent intent, IMData imData) {
        if (imData.getRequestCode() == 1) {
            ActivityCompat.startActivity(context, intent, null);
        } else {
            if (context instanceof Activity) {
                ActivityCompat.startActivityForResult((Activity) context, intent, imData.getRequestCode(), null);
            } else {
                //TODO 抛出异常，提示
            }
        }
    }

    public static class SingleIMRequest {
        public static IMRequest imRequest = new IMRequest();
    }
}
