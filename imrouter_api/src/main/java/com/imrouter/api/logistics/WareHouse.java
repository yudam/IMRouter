package com.imrouter.api.logistics;

import java.util.HashMap;

/**
 * User: maodayu
 * Date: 2019/9/4
 * Time: 14:51
 */
public class WareHouse {


    public static HashMap<String, WareHouse> groupMap     = new HashMap<>();
    public static HashMap<String, String>    groupNameMap = new HashMap<>();

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

    //根据组名获取指定的WareHouse
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

    //根据指定的root名 获取IRouterRoot对象
    public Class<? extends IRouterRoot> loadTarget() {
        return rootClass.get(routerGroupName).getTargetObject();
    }

}
