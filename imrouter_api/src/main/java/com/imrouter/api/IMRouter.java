package com.imrouter.api;

import android.app.Application;

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

    public static void initIndex(Application application, Map<String, RouterParam> paramMap) {
        IMRequest.getInstance().inits(application, paramMap);
    }

    public void build(String path) {
        IMRequest.getInstance().build(path);
    }

    public void start(IMData imData) {
        IMRequest.getInstance().start(imData);
    }

}
