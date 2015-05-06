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
package com.plugin.server.reduce;

import com.plugin.server.reduce.client.ModuleReduceClient;
import com.plugin.server.reduce.object.ModuleTable;
import org.springframework.beans.factory.FactoryBean;

import javax.sql.DataSource;
/**
 * Description: support spring factory bean
 * @author dennisit@163.com
 * @version 1.0
 */
public class ModuleReduceFacotyBean implements FactoryBean {

    // dataSource
    private DataSource dataSource;

    // module table
    private ModuleTable moduleTable;

    @Override
    public Object getObject() throws Exception {
        if(null == dataSource){
            throw new RuntimeException("dataSource is null !");
        }
        if(null == moduleTable){
            return new ModuleReduceClient(dataSource);
        }
        return new ModuleReduceClient(dataSource,moduleTable);
    }

    @Override
    public Class getObjectType() {
        return ModuleReduceClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setModuleTable(ModuleTable moduleTable) {
        this.moduleTable = moduleTable;
    }
}
