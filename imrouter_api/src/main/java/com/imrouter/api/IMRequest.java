package com.imrouter.api;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.imrouter.annotation.RouterParam;
import com.imrouter.api.logistics.IRouterGroup;
import com.imrouter.api.logistics.WareHouse;
import com.imrouter.api.utils.ClassUtils;

import java.util.Map;
import java.util.Set;

import androidx.core.app.ActivityCompat;

/**
 * User: maodayu
 * Date: 2019/9/3
 * Time: 15:35
 */
public class IMRequest {
    public  Map<String, RouterParam> routerParamMap;
    private Context                  mContext;
    private Handler mHandler;

    public static IMRequest getInstance() {
        return SingleIMRequest.imRequest;
    }

    public void inits(Application application) {
        mContext = application;
        loadAllRouter();
        mHandler=new Handler(Looper.getMainLooper());
    }

    public IMData build(String path) {
        IMData imData = new IMData(path, 1);
        return imData;
    }

    public IMData build(Context context, String path) {
        IMData imData = new IMData(context, path, 1);
        return imData;
    }

    public IMData build(String path, int requestCode) {
        IMData imData = new IMData(path, requestCode);
        return imData;
    }

    public void start(Context context, final IMData imData) {
        final Context aContext = context == null ? mContext : context;
        RouterParam routerParam = imData.getParam();
        switch (routerParam.getTarget_type()) {
            case RouterParam.TARGET_ACTIVITY:
                Log.i("MDY", "start="+routerParam.getTargetObject());
                final Intent intent = new Intent(aContext, routerParam.getTargetObject());
                intent.putExtras(imData.getBundle());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(aContext, intent, imData);
                    }
                });
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

    private void loadAllRouter() {
        try {
            Set<String> packageSet = ClassUtils.getFileNameByPackageName(mContext, "com.imrouter.api");
            for (String className : packageSet) {
                if (className.endsWith("$Group")) {
                    IRouterGroup iRouterGroup = (IRouterGroup) (Class.forName(className).getConstructor().newInstance());
                    iRouterGroup.initGroup();
                    iRouterGroup.initPath();
                }
            }
        } catch (Exception e) {

        }
    }

    public static class SingleIMRequest {
        public static IMRequest imRequest = new IMRequest();
    }

}
