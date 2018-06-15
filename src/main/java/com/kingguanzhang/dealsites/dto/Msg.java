package com.kingguanzhang.dealsites.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于包装后端发送给前端的json信息的类;
 * @author Administrator
 *
 */
public class Msg {

    private int code;
    private String msg;
    private Map<String, Object> extend = new HashMap<String, Object>();


    public static Msg success() {
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("失败");
        return result;
    }

    /**
     * 这个add方法,是为了让其它类调用Msg时可以这样链式操作:Msg.success().add("1",1).add("2",2);
     * @param key
     * @param value
     * @return
     */
    public Msg add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }
    public Msg setCode(int code) {
        this.code = code;
        return this;
    }
    public String getMsg() {
        return msg;
    }
    public Msg setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    public Map<String, Object> getExtend() {
        return extend;
    }
    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public Msg(int code, String msg, Map<String, Object> extend) {
        this.code = code;
        this.msg = msg;
        this.extend = extend;
    }
    public Msg() {
    }


}