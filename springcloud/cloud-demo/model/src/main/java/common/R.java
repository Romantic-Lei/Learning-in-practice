package common;

import lombok.Data;

@Data
public class R {

    private Integer code;

    private String msg;

    private Object data;

    public static R ok(){
        return ok(null, null);
    }

    public static R ok(String msg, Object data){
        return ok(200, msg, data);
    }

    public static R ok(Integer code, String msg, Object data){
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static R error(){
        return error(500, null, null);
    }

    public static R error(Integer code, String msg){
        return error(code, msg, "");
    }

    public static R error(String msg, Object data){
        return error(500, msg, data);
    }

    public static R error(Integer code, String msg, Object data){
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
