package com.yzg.home.jlyt;

import com.yzg.common.contract.BaseCustomViewModel;

public class JlytBean extends BaseCustomViewModel {

    String name;
    String rate;

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
}
