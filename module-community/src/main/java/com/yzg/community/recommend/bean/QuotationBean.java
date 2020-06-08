package com.yzg.community.recommend.bean;

import java.io.Serializable;

public class QuotationBean implements Serializable {

    /**
     * searchValue : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * remark : null
     * params : {}
     * orderNo : 12534
     * varietynm : 黄金T+D
     * variety : AuT+D
     * volume : 27066
     * lastPrice : 383.72
     * highPrice : 384.39
     * changeMargin : -0.06%
     * changePrice : -0.22
     * openPrice : 383.5
     * yesyPrice : 383.94
     * lowPrice : 382.15
     * uptime : 2020-06-08 22:49:56
     */

    private Object searchValue;
    private Object createBy;
    private Object createTime;
    private Object updateBy;
    private Object updateTime;
    private Object remark;
    private ParamsBean params;
    private int orderNo;
    private String varietynm;
    private String variety;
    private int volume;
    private double lastPrice;
    private double highPrice;
    private String changeMargin;
    private double changePrice;
    private double openPrice;
    private double yesyPrice;
    private double lowPrice;
    private String uptime;

    public Object getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public Object getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Object createBy) {
        this.createBy = createBy;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getVarietynm() {
        return varietynm;
    }

    public void setVarietynm(String varietynm) {
        this.varietynm = varietynm;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public String getChangeMargin() {
        return changeMargin;
    }

    public void setChangeMargin(String changeMargin) {
        this.changeMargin = changeMargin;
    }

    public double getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(double changePrice) {
        this.changePrice = changePrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getYesyPrice() {
        return yesyPrice;
    }

    public void setYesyPrice(double yesyPrice) {
        this.yesyPrice = yesyPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public static class ParamsBean {
    }
}
