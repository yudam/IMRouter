package com.imrouter.api.logistics;

import com.imrouter.annotation.RouterParam;

import java.util.Map;

/**
 * User: maodayu
 * Date: 2019/9/4
 * Time: 14:53
 */
public interface IRouterRoot {
    Map<String, RouterParam> loadInfo();
}
