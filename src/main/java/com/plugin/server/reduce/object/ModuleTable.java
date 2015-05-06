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
package com.plugin.server.reduce.object;

import com.plugin.server.reduce.constant.Constants;
/**
 * Description:table config mapping
 * @author dennisit@163.com
 * @version 1.0
 */
public class ModuleTable {

    /**table name*/
    private String  tableName;

    /**primary key*/
    private String  primaryKey;

    /**server module name*/
    private String  moduleName;

    /**server module switch*/
    private String  moduleSwitch;

    /**server module depict*/
    private String  moduleDepict;

    /**server module token*/
    private String  moduleToken;


    public ModuleTable() {
        this(Constants.TABLE_NAME,
            Constants.PRIMARY_KEY,
            Constants.MODULE_NAME,
            Constants.MODULE_SWITCH,
            Constants.MODULE_DEPICT,
            Constants.MODULE_TOKEN);
    }

    public ModuleTable(String tableName, String primaryKey, String moduleName, String moduleSwitch, String moduleDepict, String moduleToken) {
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.moduleName = moduleName;
        this.moduleSwitch = moduleSwitch;
        this.moduleDepict = moduleDepict;
        this.moduleToken = moduleToken;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleSwitch() {
        return moduleSwitch;
    }

    public void setModuleSwitch(String moduleSwitch) {
        this.moduleSwitch = moduleSwitch;
    }

    public String getModuleDepict() {
        return moduleDepict;
    }

    public void setModuleDepict(String moduleDepict) {
        this.moduleDepict = moduleDepict;
    }

    public String getModuleToken() {
        return moduleToken;
    }

    public void setModuleToken(String moduleToken) {
        this.moduleToken = moduleToken;
    }
}
