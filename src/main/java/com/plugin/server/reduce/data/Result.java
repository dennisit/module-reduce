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
package com.plugin.server.reduce.data;
import com.plugin.server.reduce.constant.Constants;

/**
 * Description:
 * @author dennisit@163.com
 * @version 1.0
 */
public class Result<T> {

    private String  message;
    private Constants.ResultStatus status;
    private boolean success;
    private T item;

    public Result() {

    }

    public Result(boolean success,Constants.ResultStatus status,String message,T item) {
        this.message = message;
        this.status = status;
        this.success = success;
        this.item = item;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Constants.ResultStatus getStatus() {
        return status;
    }

    public void setStatus(Constants.ResultStatus status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
