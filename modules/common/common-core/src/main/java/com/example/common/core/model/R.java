package com.example.common.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R<T> {
    
    private Integer code;
    
    private String msg;
    
    private T data;
    
    public static <T> R<T> ok() {
        return new R<T>().setCode(200).setMsg("操作成功");
    }
    
    public static <T> R<T> ok(T data) {
        return new R<T>().setCode(200).setMsg("操作成功").setData(data);
    }
    
    public static <T> R<T> fail(String msg) {
        return new R<T>().setCode(500).setMsg(msg);
    }
    
    public static <T> R<T> fail(Integer code, String msg) {
        return new R<T>().setCode(code).setMsg(msg);
    }
} 