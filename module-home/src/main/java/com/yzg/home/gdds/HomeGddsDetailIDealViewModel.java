package com.yzg.home.gdds;

import androidx.lifecycle.MutableLiveData;

import com.lzy.okgo.OkGo;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.ArrayList;

public class HomeGddsDetailIDealViewModel
        extends MvvmBaseViewModel<IBaseView> {
    public MutableLiveData<Boolean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void getList(String account, String name, String loginName) {
        successData.setValue(true);
//        TreeMap<String, String> map = new TreeMap<>();
//        map.put("payNo", account + "=" + name);
//        map.put("loginName", loginName);
//
//        OkGo.<String>post(HttpService.EB_editUser)
//                .params(map)
//                .tag(this)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//
//                        JSONObject jsonObject = JSON.parseObject(response.body());
//
//                        if (jsonObject != null && jsonObject.getIntValue("code") == 0) {
//                            successData.setValue(true);
//                        } else {
//                            errorLiveData.setValue(jsonObject.getString("msg"));
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                        errorLiveData.setValue(response.message());
//                    }
//                });
    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);
    }
}
