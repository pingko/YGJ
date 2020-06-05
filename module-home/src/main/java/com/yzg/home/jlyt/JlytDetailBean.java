package com.yzg.home.jlyt;

import java.io.Serializable;

public class JlytDetailBean {

    /**
     * searchValue : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * remark : null
     * params : {}
     * productId : 1
     * productName : 租赁产品第一期
     * productState : 0
     * productType : 2
     * productSize : 12
     * rate : 12
     * productStart : 2020-03-11
     * productEnd : 2020-03-11
     * raiseStart : 2020-03-05
     * startTime : 12:20
     * raiseEnd : 2020-03-12
     * endTime : 12:30
     * length : 11
     * point : 21.022
     * yuliu : 1
     * productLimit : 1
     */

    private Object searchValue;
    private Object createBy;
    private Object createTime;
    private Object updateBy;
    private Object updateTime;
    private Object remark;
    private ParamsBean params;
    private String productId;
    private String productName;
    private String productState;
    private String productType;
    private int productSize;
    private int rate;
    private String productStart;
    private String productEnd;
    private String raiseStart;
    private String startTime;
    private String raiseEnd;
    private String endTime;
    private int length;
    private double point;
    private int yuliu;
    private int productLimit;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getProductSize() {
        return productSize;
    }

    public void setProductSize(int productSize) {
        this.productSize = productSize;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getProductStart() {
        return productStart;
    }

    public void setProductStart(String productStart) {
        this.productStart = productStart;
    }

    public String getProductEnd() {
        return productEnd;
    }

    public void setProductEnd(String productEnd) {
        this.productEnd = productEnd;
    }

    public String getRaiseStart() {
        return raiseStart;
    }

    public void setRaiseStart(String raiseStart) {
        this.raiseStart = raiseStart;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRaiseEnd() {
        return raiseEnd;
    }

    public void setRaiseEnd(String raiseEnd) {
        this.raiseEnd = raiseEnd;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getYuliu() {
        return yuliu;
    }

    public void setYuliu(int yuliu) {
        this.yuliu = yuliu;
    }

    public int getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(int productLimit) {
        this.productLimit = productLimit;
    }

    public static class ParamsBean implements Serializable {
    }
}
