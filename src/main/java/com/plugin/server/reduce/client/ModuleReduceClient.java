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
package com.plugin.server.reduce.client;

import com.plugin.server.reduce.constant.Constants;
import com.plugin.server.reduce.data.Page;
import com.plugin.server.reduce.data.Result;
import com.plugin.server.reduce.object.ModuleObject;
import com.plugin.server.reduce.object.ModuleTable;
import com.plugin.server.reduce.sql.SQLBuilder;
import com.plugin.server.reduce.sql.SQLUtil;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:module-reduce-client
 * @author dennisit@163.com
 * @version 1.0
 */
public class ModuleReduceClient {

    private static final Logger LOG = Logger.getLogger(ModuleReduceClient.class);

    // dataSource
    private DataSource dataSource;

    // module table
    private ModuleTable moduleTable;

    /**
     * 添加服务开关
     * @param moduleObject
     * @return
     */
    public synchronized boolean insertModule(ModuleObject moduleObject){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            //insert into tb_module_switch ( module_name , module_switch , module_depict , module_token )  values ( ? , ? , ? , ?)
            statement = connection.prepareStatement(SQLBuilder.buildInsert(moduleTable));
            statement.setString(1, moduleObject.getModuleName());
            statement.setInt(2, moduleObject.getModuleSwitch());
            statement.setString( 3, moduleObject.getModuleDepict() );
            statement.setString( 4, moduleObject.getModuleToken() );
            int resultSet = statement.executeUpdate();
            if(resultSet > 1){
                return true;
            }
        } catch (Exception e) {
            LOG.error("insertModule in db error! " + e.getMessage(), e);
        } finally {
            SQLUtil.close(connection, statement, null);
        }
        return false;
    }

    /**
     * 服务降级-开关关闭
     * @param moduleName
     * @param moduleToken
     * @return
     */
    public synchronized boolean closeModule(String moduleName,String moduleToken){
        return switchModule(moduleName,moduleToken,Constants.SwitchStatus.CLOSE);
    }

    /**
     * 服务启动-开关打开
     * @param moduleName
     * @param moduleToken
     * @return
     */
    public synchronized boolean openModule(String moduleName,String moduleToken){
        return switchModule(moduleName,moduleToken,Constants.SwitchStatus.OPEN);
    }

    /**
     * 查询module的服务状态
     * @param moduleName
     * @param moduleToken
     * @return
     */
    public synchronized Result queryModuleSwitch(String moduleName,String moduleToken) {
        Result result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            ModuleObject moduleObject = queryModuleOnlyName(moduleName);
            if( null == moduleObject){
                result = new Result(true, Constants.ResultStatus.mudule_not_exists, moduleName + " is not exists!",null);
                return result;
            }
            //ModuleObject tokenModule = queryModuleWithToken(moduleName,moduleToken);
            if(!moduleObject.getModuleToken().equals(moduleToken)){
                result = new Result(true, Constants.ResultStatus.token_error, "token error!",null);
                return result;
            }
            if(moduleObject.getModuleSwitch().intValue() == Constants.SwitchStatus.OPEN.getValue()){
                result = new Result(true, Constants.ResultStatus.open, moduleName + " switch is open!", moduleObject);
                return result;
            }else{
                result = new Result(true, Constants.ResultStatus.close, moduleName + " switch is close!",moduleObject);
                return result;
            }
        } catch (Exception e) {
            result = new Result(false, Constants.ResultStatus.system_err, "system_err!",null);
            LOG.error("queryModuleSwitch error! " + e.getMessage(), e);
        } finally {
            SQLUtil.close(connection, statement, resultSet);
        }
        return result;
    }

    /**
     * 带有认证查询
     * @param moduleName
     * @param moduleToken
     * @return
     */
    public synchronized ModuleObject queryModuleWithToken(String moduleName,String moduleToken){
        ModuleObject moduleObject = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dataSource.getConnection();
            //SELECT module_switch FROM tb_module_switch WHERE module_name =  ? AND module_token = ?
            statement = connection.prepareStatement(SQLBuilder.buildQueryModuleToken(moduleTable));
            statement.setString(1,moduleName);
            statement.setString(2,moduleToken);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                moduleObject = new ModuleObject();
                moduleObject.setPrimaryKey(resultSet.getLong(moduleTable.getPrimaryKey()));
                moduleObject.setModuleSwitch(resultSet.getInt(moduleTable.getModuleSwitch()));
                moduleObject.setModuleName(resultSet.getString(moduleTable.getModuleName()));
                moduleObject.setModuleDepict(resultSet.getString(moduleTable.getModuleDepict()));
                moduleObject.setModuleToken(resultSet.getString(moduleTable.getModuleToken()));
            }
        } catch (Exception e) {
            LOG.error("ModuleObject queryModuleWithToken(...) error! " + e.getMessage(), e);
        } finally {
            SQLUtil.close(connection, statement, resultSet);
        }
        return moduleObject;
    }

    /**
     * 分页查询
     * @param moduleObject
     * @param pageNow
     * @param pageSize
     * @return
     */
    public synchronized Page<ModuleObject> queryModules(ModuleObject moduleObject, int pageNow, int pageSize){
        Page<ModuleObject> page = null;
        try{
            int recordTotal = queryTotal(moduleObject);
            List<ModuleObject> lists = queryPage(moduleObject,pageNow,pageSize);
            page = new Page<ModuleObject>(lists,recordTotal,pageNow,pageSize);
        }catch (Exception e){
            LOG.error("Page<ModuleObject> queryModules error!" + e.getMessage() ,e);
        }
        return page;
    }

    /**
     * 分页查询modules
     * @param pageNow
     * @param pageSize
     * @return
     */
    private List<ModuleObject> queryPage(ModuleObject queryCondition, int pageNow, int pageSize){
        List<ModuleObject> items = new ArrayList<ModuleObject>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(SQLBuilder.buildQueryPager(moduleTable,queryCondition));
            statement.setInt(1, (pageNow-1)*pageSize);
            statement.setInt(2,pageSize);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                ModuleObject moduleObject = new ModuleObject();
                moduleObject.setPrimaryKey(resultSet.getLong(moduleTable.getPrimaryKey()));
                moduleObject.setModuleSwitch(resultSet.getInt(moduleTable.getModuleSwitch()));
                moduleObject.setModuleName(resultSet.getString(moduleTable.getModuleName()));
                moduleObject.setModuleDepict(resultSet.getString(moduleTable.getModuleDepict()));
                moduleObject.setModuleToken(resultSet.getString(moduleTable.getModuleToken()));
                items.add(moduleObject);
            }
            return items;
        } catch (Exception e) {
            LOG.error("List<ModuleObject> queryPage(...) error! " + e.getMessage(), e);
        } finally {
            SQLUtil.close(connection, statement, resultSet);
        }
        return items;
    }

    /**
     * 获取库中的所有记录总数
     * @return
     */
    private int queryTotal(ModuleObject queryCondition){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(SQLBuilder.buildQueryTotal(moduleTable, queryCondition));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            LOG.error("int queryTotal(...) error! " + e.getMessage(), e);
        } finally {
            SQLUtil.close(connection, statement, resultSet);
        }
        return 0;
    }

    /**
     * 服务开关切换操作
     * @param moduleName
     * @param moduleToken
     * @param switchStatus
     * @return
     */
    private boolean switchModule(String moduleName,String moduleToken,Constants.SwitchStatus switchStatus){
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
        try {
            connection = this.dataSource.getConnection();
            //UPDATE tb_module_switch SET module_switch = ? WHERE module_name = ? AND module_token = ?
            statement = connection.prepareStatement(SQLBuilder.buildUpdateSwitch(moduleTable));
            statement.setInt(1,switchStatus== Constants.SwitchStatus.OPEN ? 1 : 0);
            statement.setString(2,moduleName);
            statement.setString(3,moduleToken);
            resultSet = statement.executeUpdate();
            if(resultSet > 0){
                return true;
            }
        } catch (Exception e) {
            LOG.error("queryModuleSwitch in db error! " + e.getMessage(), e);
        } finally {
            SQLUtil.close(connection, statement, null);
        }
        return false;
    }


    /**
     * 根据moduleName查询
     * @param moduleName
     * @return
     */
    private ModuleObject queryModuleOnlyName(String moduleName){
        ModuleObject moduleObject = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dataSource.getConnection();
            // SELECT * FROM tb_module_switch WHERE module_name = ?
            statement = connection.prepareStatement(SQLBuilder.buildQueryOnlyModule(moduleTable));
            statement.setString(1, moduleName);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                moduleObject = new ModuleObject();
                moduleObject.setPrimaryKey(resultSet.getLong(moduleTable.getPrimaryKey()));
                moduleObject.setModuleSwitch(resultSet.getInt(moduleTable.getModuleSwitch()));
                moduleObject.setModuleName(resultSet.getString(moduleTable.getModuleName()));
                moduleObject.setModuleDepict(resultSet.getString(moduleTable.getModuleDepict()));
                moduleObject.setModuleToken(resultSet.getString(moduleTable.getModuleToken()));
            }
            return moduleObject;
        } catch (Exception e) {
            LOG.error("ModuleObject queryModuleOnlyName(...)  error! " + e.getMessage(), e);
        } finally {
            SQLUtil.close(connection, statement, resultSet);
        }
        return moduleObject;
    }


    public ModuleReduceClient() {

    }

    public ModuleReduceClient(DataSource dataSource) {
        this(dataSource,new ModuleTable());
    }


    public ModuleReduceClient(DataSource dataSource, ModuleTable moduleTable) {
        this.dataSource = dataSource;
        this.moduleTable = moduleTable;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ModuleTable getModuleTable() {
        return moduleTable;
    }

    public void setModuleTable(ModuleTable moduleTable) {
        this.moduleTable = moduleTable;
    }


}
