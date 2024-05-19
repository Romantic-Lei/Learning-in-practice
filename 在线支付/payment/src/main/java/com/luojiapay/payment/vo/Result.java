package com.luojiapay.payment.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Result {

    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public static Result ok() {
        Result result = new Result();
        result.setCode(0);
        result.setMessage("成功");
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(-1);
        result.setMessage("失败");
        return result;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
