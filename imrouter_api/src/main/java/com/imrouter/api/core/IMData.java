package com.imrouter.api.core;

import android.content.Context;
import android.os.Bundle;

import com.imrouter.annotation.RouterParam;
import com.imrouter.api.IMRouter;
import com.imrouter.api.exception.QueryFailedException;
import com.imrouter.api.logistics.IRouterRoot;
import com.imrouter.api.logistics.WareHouse;

import java.util.Map;

/**
 * User: maodayu
 * Date: 2019/9/3
 * Time: 15:36
 */
public class IMData {

    private RouterParam mParam;
    private String      rootPath;
    private int         requestCode;
    private int         mFlags = -1;
    private Bundle      mBundle;

    private Context context;

    public IMData(String path) {
        this(path, 1);
    }

    public IMData(String path, int requestCode) {
        this(null, path, requestCode);
    }

    public IMData(Context context, String rootPath, int requestCode) {
        this.context = context;
        this.rootPath = rootPath;
        this.requestCode = requestCode;
        mBundle = new Bundle();
    }

    public IMData withRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public IMData withFlag(int flags) {
        mFlags = flags;
        return this;
    }


    public IMData withInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public void start() {
        WareHouse wareHouse = WareHouse.loadWareHouse(rootPath);
        if (wareHouse == null) {
            throw new QueryFailedException("The specified path was not queried！");
        } else {
            try {
                IRouterRoot iRouterRoot = wareHouse.loadTarget().getConstructor().newInstance();
                Map<String, RouterParam> paramMap = iRouterRoot.loadInfo();
                mParam = paramMap.get(rootPath);
            } catch (Exception e) {

            }
        }
        IMRouter.getInstance().start(context, this);
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

    public int getFlags() {
        return mFlags;
    }

}
