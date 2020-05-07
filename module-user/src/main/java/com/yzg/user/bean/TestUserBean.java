package com.yzg.user.bean;

import com.yzg.common.contract.BaseCustomViewModel;

import java.io.Serializable;

public class TestUserBean extends BaseCustomViewModel implements Serializable {
    public String name;
    public String sex;
    public String name1;
    public String name2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
