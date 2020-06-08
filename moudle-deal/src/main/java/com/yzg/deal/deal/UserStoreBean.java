package com.yzg.deal.deal;

import java.io.Serializable;

public class UserStoreBean implements Serializable {

    /**
     * searchValue : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * remark : null
     * params : {}
     * acctNo : 1861234567
     * currAmt : 0
     * currCanUse : 0
     * sellFrozAmt : 0
     * takeFrozAmt : 0
     */

    private Object searchValue;
    private Object createBy;
    private Object createTime;
    private Object updateBy;
    private Object updateTime;
    private Object remark;
    private ParamsBean params;
    private String acctNo;
    private int currAmt;
    private int currCanUse;
    private int sellFrozAmt;
    private int takeFrozAmt;

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

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public int getCurrAmt() {
        return currAmt;
    }

    public void setCurrAmt(int currAmt) {
        this.currAmt = currAmt;
    }

    public int getCurrCanUse() {
        return currCanUse;
    }

    public void setCurrCanUse(int currCanUse) {
        this.currCanUse = currCanUse;
    }

    public int getSellFrozAmt() {
        return sellFrozAmt;
    }

    public void setSellFrozAmt(int sellFrozAmt) {
        this.sellFrozAmt = sellFrozAmt;
    }

    public int getTakeFrozAmt() {
        return takeFrozAmt;
    }

    public void setTakeFrozAmt(int takeFrozAmt) {
        this.takeFrozAmt = takeFrozAmt;
    }

    public static class ParamsBean {
    }
}
