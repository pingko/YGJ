package com.yzg.common.contract;

public class TestApi extends BaseCustomViewModel {


    /**
     * msg : 用户注册'1861234567'失败，登录账号已存在
     * code : 500
     */

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
