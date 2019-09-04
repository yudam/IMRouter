package com.imrouter.api;

import android.app.Application;
import android.content.Context;

import com.imrouter.annotation.RouterParam;

import java.util.Map;

/**
 * User: maodayu
 * Date: 2019/9/3
 * Time: 15:35
 */
public class IMRouter {

    private static IMRouter imRouter;

    private IMRouter() {
    }

    public static IMRouter getInstance() {
        if (imRouter == null) {
            synchronized (IMRouter.class) {
                if (imRouter == null) {
                    return new IMRouter();
                }
            }
        }
        return imRouter;
    }

    public static void initIndex(Application application) {
        IMRequest.getInstance().inits(application);
    }

    public IMData build(String path) {
        return IMRequest.getInstance().build(path);
    }

    public IMData build(Context context,String path){
        return IMRequest.getInstance().build(context,path);
    }


    public void start(Context context,IMData imData) {
        IMRequest.getInstance().start(context,imData);
    }

}
