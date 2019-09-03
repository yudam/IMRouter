package com.imrouter.annotation;

/**
 * User: maodayu
 * Date: 2019/9/3
 * Time: 10:25
 */
public class RouterParam {

    public static final int    TARGET_ACTIVITY  = 0x12;
    public static final int    TARGET_FRAGMENT  = 0x13;
    public static final int    TARGET_SERVICE   = 0x14;
    public static final int    TARGET_BROADCAST = 0x15;
    private             String routerPath;
    private             String targetSite;
    private             int    target_type;

    public RouterParam(String routerPath, String targetSite, int target_type) {
        this.routerPath = routerPath;
        this.targetSite = targetSite;
        this.target_type = target_type;
    }

    public static RouterParam build(String routerPath, String targetSite, int target_type) {
        return new RouterParam(routerPath, targetSite, target_type);
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
}
