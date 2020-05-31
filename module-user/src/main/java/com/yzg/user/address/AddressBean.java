package com.yzg.user.address;

import com.yzg.common.contract.BaseCustomViewModel;

public class AddressBean extends BaseCustomViewModel {

    String name;
    String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
