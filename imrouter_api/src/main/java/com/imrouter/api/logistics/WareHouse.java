package com.imrouter.api.logistics;

import java.util.HashMap;

/**
 * User: maodayu
 * Date: 2019/9/4
 * Time: 14:51
 */
public class WareHouse {


    /**
     * key:对应组名
     * value：WareHouse包含组内的rootClass
     */
    public static HashMap<String, WareHouse> groupMap = new HashMap<>();

    /**
     * key:对应每一个路径
     * value：对应路径所属的WareHouseImpl的组别
     */
    public static HashMap<String, String> groupNameMap = new HashMap<>();

    /**
     * key:注解生成的组名
     * value:WareHouseImpl包APT生成类的标准名称、class
     */
    public HashMap<String, WareHouseImpl> rootClass = new HashMap<>();

    private String routerGroupName;

    public WareHouse(String groupName) {
        this.routerGroupName = groupName;
    }


    /**
     * @param routerClassName 按照组来划分的组名称
     * @param targetObject    该组下的一个具体的类
     */
    public static void put(String groupName, String routerClassName, Class<? extends IRouterRoot> targetObject) {
        WareHouse wareHouse = groupMap.get(groupName);
        if (wareHouse == null) {
            wareHouse = new WareHouse(groupName);
            groupMap.put(groupName, wareHouse);
        }
        wareHouse.putTarget(routerClassName, targetObject);
    }

    public static void put(String rootPath, String groupName) {
        if (groupNameMap.get(rootPath) == null) {
            groupNameMap.put(rootPath, groupName);
        }
    }

    //根据路由获取组别，根据组别获取具体的路由映射包裹类WareHouse
    public static WareHouse loadWareHouse(String rootPath) {
        if (groupNameMap.get(rootPath) == null) {
            return null;
        }
        return groupMap.get(groupNameMap.get(rootPath));
    }

    /**
     * @param routerClassName 指定的名称
     * @param targetObject    映射的对象
     */
    public void putTarget(String routerClassName, Class<? extends IRouterRoot> targetObject) {
        rootClass.put(routerGroupName, new WareHouseImpl(routerClassName, targetObject));
    }

    //根据组名获取WareHouseImpl，在获取WareHouseImpl内的具体路由映射类
    public Class<? extends IRouterRoot> loadTarget() {
        return rootClass.get(routerGroupName).getTargetObject();
    }

}
