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
package com.plugin.server.reduce.constant;

/**
 * Description:
 * default basic table info
 * @author dennisit@163.com
 * @version 1.0
 */
public final class Constants {

    /**default table name*/
    public static final String  TABLE_NAME  = "tb_module_switch";

    /**default primary key*/
    public static final String  PRIMARY_KEY = "id" ;

    /**default server module name*/
    public static final String  MODULE_NAME =  "module_name";

    /**default server module switch*/
    public static final String  MODULE_SWITCH = "module_switch";

    /**default server module depict*/
    public static final String  MODULE_DEPICT = "module_depict";

    /**default server module token*/
    public static final String  MODULE_TOKEN = "module_token";


    public enum SwitchStatus{

        CLOSE("CLOSE",0), OPEN("OPEN",1);

        private String name;
        private int value;

        private SwitchStatus(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public enum ResultStatus{
        system_err, mudule_not_exists, token_error, open, close;
    }
}
