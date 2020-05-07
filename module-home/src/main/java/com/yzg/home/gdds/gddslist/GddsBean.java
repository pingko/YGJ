package com.yzg.home.gdds.gddslist;

import java.io.Serializable;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class GddsBean implements Serializable {

    public String headUrl;
    public String name;
    public String rate;
    public String fans;
    public String time;
    public String subscribeNum;
    public boolean isSubs;

    public GddsBean(String headUrl, String name, String rate, String fans, String time, String subscribeNum, boolean isSubs) {
        this.headUrl = headUrl;
        this.name = name;
        this.rate = rate;
        this.fans = fans;
        this.time = time;
        this.subscribeNum = subscribeNum;
        this.isSubs = isSubs;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubscribeNum() {
        return subscribeNum;
    }

    public void setSubscribeNum(String subscribeNum) {
        this.subscribeNum = subscribeNum;
    }

    public boolean isSubs() {
        return isSubs;
    }

    public void setSubs(boolean subs) {
        isSubs = subs;
    }
}
