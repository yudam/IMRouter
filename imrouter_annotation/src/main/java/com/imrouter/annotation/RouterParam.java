package com.imrouter.annotation;

import javax.lang.model.element.TypeElement;

/**
 * User: maodayu
 * Date: 2019/9/3
 * Time: 10:25
 */
public class RouterParam {

    public static final int         TARGET_ACTIVITY  = 0x12;
    public static final int         TARGET_FRAGMENT  = 0x13;
    public static final int         TARGET_SERVICE   = 0x14;
    public static final int         TARGET_BROADCAST = 0x15;
    private             String      routerPath;
    private             String      targetSite;
    private             TypeElement targetElement;
    private             Class<?>    targetObject;
    private             int         target_type;

    public RouterParam(String routerPath, TypeElement targetElement, String targetSite, int target_type) {
        this.routerPath = routerPath;
        this.targetElement = targetElement;
        this.targetSite = targetSite;
        this.target_type = target_type;
    }

    public RouterParam(String routerPath, Class<?> targetObject, String targetSite, int target_type) {
        this.routerPath = routerPath;
        this.targetObject = targetObject;
        this.targetSite = targetSite;
        this.target_type = target_type;
    }

    public static RouterParam build(String routerPath, Class<?> targetObject, String targetSite, int target_type) {
        return new RouterParam(routerPath, targetObject, targetSite, target_type);
    }

    public String getRouterPath() {
        return routerPath;
    }

    public String getTargetSite() {
        return targetSite;
    }

    public int getTarget_type() {
        return target_type;
    }

    public TypeElement getTargetElement() {
        return targetElement;
    }

    public Class<?> getTargetObject() {
        return targetObject;
    }

    @Override
    public String toString() {
        return "RouterParam{" +
                "routerPath='" + routerPath + '\'' +
                ", targetSite='" + targetSite + '\'' +
                ", targetElement=" + targetElement +
                ", targetObject=" + targetObject +
                ", target_type=" + target_type +
                '}';
    }
}
