package com.yzg.user.bean;

import com.yzg.common.contract.BaseCustomViewModel;


public class LoginResponseBean extends BaseCustomViewModel {
    public String token;
    /**
     * sellPoint : 0
     * loginName : 1861234567
     * buyPoint : 1
     */

    private String sellPoint;
    private String loginName;
    private String buyPoint;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getBuyPoint() {
        return buyPoint;
    }

    public void setBuyPoint(String buyPoint) {
        this.buyPoint = buyPoint;
    }
}
