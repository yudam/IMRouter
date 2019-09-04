package com.imrouter.api.logistics;

/**
 * User: maodayu
 * Date: 2019/9/4
 * Time: 19:12
 */
public class WareHouseImpl {

    private String routerClassName;

    private Class<? extends IRouterRoot> targetObject;

    public WareHouseImpl(String routerClassName, Class<? extends IRouterRoot> targetObject) {
        this.routerClassName = routerClassName;
        this.targetObject = targetObject;
    }

    public String getRouterClassName() {
        return routerClassName;
    }

    public Class<? extends IRouterRoot> getTargetObject() {
        return targetObject;
    }
}
