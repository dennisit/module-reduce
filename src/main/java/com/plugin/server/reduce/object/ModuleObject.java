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

import java.io.Serializable;

/**
 * Description:
 * @author dennisit@163.com
 * @version 1.0
 */
public class ModuleObject implements Serializable{


    /**primary key*/
    private Long  primaryKey ;

    /**server module name*/
    private String  moduleName;

    /**server module switch*/
    private Integer  moduleSwitch;

    /**server module depict*/
    private String  moduleDepict;

    /**server module token*/
    private String  moduleToken;


    public Long getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Long primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getModuleSwitch() {
        return moduleSwitch;
    }

    public void setModuleSwitch(Integer moduleSwitch) {
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
