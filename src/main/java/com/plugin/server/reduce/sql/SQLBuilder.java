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
package com.plugin.server.reduce.sql;

import com.plugin.server.reduce.constant.Constants;
import com.plugin.server.reduce.object.ModuleObject;
import com.plugin.server.reduce.object.ModuleTable;
import org.apache.commons.lang3.StringUtils;

/**
 * Description:builder sql
 * @author dennisit@163.com
 * @version 1.0
 */
public class SQLBuilder {


    private SQLBuilder(){
        throw  new UnsupportedOperationException();
    }

    /**
     * SQL:
     * insert into tb_module_switch ( module_name , module_switch , module_depict , module_token )  values ( ? , ? , ? , ?)
     * @param moduleTable
     * @return
     */
    public static String buildInsert(ModuleTable moduleTable){
        return new StringBuffer()
                .append(" INSERT INTO ").append(moduleTable.getTableName())
                .append(" ( ")
                .append(moduleTable.getModuleName()).append(" , ")
                .append(moduleTable.getModuleSwitch()).append(" , ")
                .append(moduleTable.getModuleDepict()).append(" , ")
                .append(moduleTable.getModuleToken())
                .append(" ) ")
                .append(" VALUES ( ? , ? , ? , ?)")
                .toString();
    }

    /**
     * SQL: UPDATE tb_module_switch SET module_switch = ? WHERE module_name = ? AND module_token = ?
     * @param moduleTable
     * @return
     */
    public static String buildUpdateSwitch(ModuleTable moduleTable){
        return new StringBuffer()
                .append(" UPDATE ").append(moduleTable.getTableName())
                .append(" SET ").append(moduleTable.getModuleSwitch())
                .append(" = ?")
                .append(" WHERE ")
                .append(moduleTable.getModuleName()).append(" = ? AND ")
                .append(moduleTable.getModuleToken()).append(" = ? ")
                .toString();
    }

    /**
     * SQL: SELECT module_switch FROM tb_module_switch WHERE module_name =  ? AND module_token = ?
     * @return
     */
    public static String buildQueryModuleToken(ModuleTable moduleTable){
        return new StringBuilder()
                .append(" SELECT  * FROM ")
                .append(moduleTable.getTableName())
                .append(" WHERE ").append(moduleTable.getModuleName())
                .append(" =  ? AND ")
                .append(moduleTable.getModuleToken())
                .append(" = ?")
                .toString();
    }

    /**
     * SQL: SELECT * FROM tb_module_switch WHERE module_name = ?
     * @param moduleTable
     * @return
     */
    public static String buildQueryOnlyModule(ModuleTable moduleTable){
        return new StringBuilder()
                .append(" SELECT * FROM ")
                .append(moduleTable.getTableName())
                .append(" WHERE ").append(moduleTable.getModuleName())
                .append(" = ? ")
                .toString();
    }

    /**
     * SQL: SELECT * FROM tb_module_switch
     * @param moduleTable
     * @return
     */
    public static String buildQueryAll(ModuleTable moduleTable){
        return new StringBuilder("SELECT * FROM ")
                .append(moduleTable.getTableName())
                .toString();
    }

    /**
     * SQL: SELECT * FROM tb_module_switch WHERE 1=1  AND module_name = moduleNm AND module_depict = dffd AND module_switch = 1 AND id = 1 LIMIT ?,?
     * @param moduleTable
     * @return
     */
    public static String buildQueryPager(ModuleTable moduleTable,ModuleObject condition){
        StringBuilder builder = new StringBuilder("SELECT * FROM ").append(moduleTable.getTableName()).append(" WHERE 1=1 ");
        if(null != condition){
            builder.append(buildQueryWhere(moduleTable, condition));
        }
        return builder.append(" LIMIT ?,? ").toString();
    }


    /**
     * SQL: SELECT COUNT(1) FROM tb_module_switch WHERE 1=1  AND module_name = moduleNm AND module_depict = dffd AND module_switch = 1 AND id = 1
     * @param moduleTable
     * @return
     */
    public static String buildQueryTotal(ModuleTable moduleTable,ModuleObject condition){
        StringBuilder builder = new StringBuilder(" SELECT COUNT(1) FROM ").append(moduleTable.getTableName()).append(" WHERE 1=1 ");
        if(null != condition){
            builder.append(buildQueryWhere(moduleTable, condition));
        }
        return builder.toString();
    }

    /**
     * build query where condition
     * @param moduleTable
     * @param condition
     * @return
     */
    public static String buildQueryWhere(ModuleTable moduleTable,ModuleObject condition){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isNotBlank(condition.getModuleName())){
            builder.append(" AND ").append(moduleTable.getModuleName()).append(" = ").append(condition.getModuleName());
        }
        if(StringUtils.isNotBlank(condition.getModuleDepict())){
            builder.append(" AND ").append(moduleTable.getModuleDepict()).append(" = ").append(condition.getModuleDepict());
        }
        if(null!= condition.getModuleSwitch()){
            if(Constants.SwitchStatus.OPEN.getValue() == condition.getModuleSwitch() || Constants.SwitchStatus.CLOSE.getValue() == condition.getModuleSwitch()){
                builder.append(" AND ").append(moduleTable.getModuleSwitch()).append(" = ").append(condition.getModuleSwitch());
            }
        }
        if(StringUtils.isNotBlank(condition.getModuleToken())){
            builder.append(" AND ").append(moduleTable.getModuleToken()).append(" = ").append(condition.getModuleToken());
        }
        if(null != condition.getPrimaryKey()){
            builder.append(" AND ").append(moduleTable.getPrimaryKey()).append(" = ").append(condition.getPrimaryKey());
        }
        return builder.toString();
    }


}
