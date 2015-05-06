//--------------------------------------------------------------------------
// Copyright (c) 2010-2020, En.dennisit or Cn.苏若年
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
// Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
// Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
// Neither the name of the dennisit nor the names of its contributors
// may be used to endorse or promote products derived from this software
// without specific prior written permission.
// Author: dennisit@163.com | dobby | 苏若年
//--------------------------------------------------------------------------
package com.plugin.server.reduce.test;

import com.google.gson.Gson;
import com.plugin.server.reduce.client.ModuleReduceClient;
import com.plugin.server.reduce.constant.Constants;
import com.plugin.server.reduce.data.Page;
import com.plugin.server.reduce.data.Result;
import com.plugin.server.reduce.object.ModuleObject;
import com.plugin.server.reduce.sql.SQLBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Description: Test Class
 * @author dennisit@163.com
 * @version 1.0
 */
public class ModuleReduceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-config-datasource.xml");

    private ModuleReduceClient moduleReduceClient;

    @Before
    public void init(){
        moduleReduceClient = (ModuleReduceClient) context.getBean("moduleReduceClient");
        if(null != moduleReduceClient ){
            System.out.println("init moduleReduceClient finish!");
        }
    }

    @Test
    public void buildSQL(){
        ModuleObject moduleObject3 = new ModuleObject();
        moduleObject3.setPrimaryKey(1L);
        moduleObject3.setModuleSwitch(1);
        moduleObject3.setModuleDepict("服务模块描述");
        moduleObject3.setModuleName("服务名称");
        System.out.println(">> " + SQLBuilder.buildQueryPager(moduleReduceClient.getModuleTable(), moduleObject3));
        System.out.println(">> " + SQLBuilder.buildQueryTotal(moduleReduceClient.getModuleTable(),moduleObject3));
    }

    @Test
    public void insertModule(){
        ModuleObject moduleObject = new ModuleObject();
        moduleObject.setModuleName("module-name-test");
        moduleObject.setModuleSwitch(Constants.SwitchStatus.OPEN.getValue());
        moduleObject.setModuleToken("module-name-token");
        moduleObject.setModuleDepict("测试数据");
        boolean result = moduleReduceClient.insertModule(moduleObject);
        System.out.println("添加新的服务开关:" + result);
    }

    @Test
    public void switchModuleClose(){
        String moduleName = "module-name-test";
        String moduleToken = "module-name-token";
        boolean resultDown = moduleReduceClient.closeModule(moduleName, moduleToken);
        System.out.println("服务降级:" + resultDown);
    }

    @Test
    public void switchModuleOpen(){
        String moduleName = "module-name-test";
        String moduleToken = "module-name-token";
        boolean resultDown = moduleReduceClient.openModule(moduleName, moduleToken);
        System.out.println("服务开启:" + resultDown);
    }

    @Test
    public void queryModuleSwitch(){
        String moduleName = "module-name-test";
        String moduleToken = "module-name-token";
        Result result = moduleReduceClient.queryModuleSwitch(moduleName, moduleToken);
        System.out.println("查询开关：" + new Gson().toJson(result));
    }

    @Test
    public void queryModules(){
        Page<ModuleObject> pages = moduleReduceClient.queryModules(new ModuleObject(),3,5);
        System.out.println("所有记录:" + new Gson().toJson(pages));
    }

}
