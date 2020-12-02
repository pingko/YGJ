package com.yzg.user;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpService;
import com.yzg.base.viewmodel.MvvmBaseViewModel;

import java.util.TreeMap;

public class BindAlipayViewModel extends MvvmBaseViewModel<IBaseView> {

    public MutableLiveData<Boolean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void editUser(String account, String name, String loginName) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("payNo", account + "=" + name);
        map.put("loginName", loginName);

        OkGo.<String>post(HttpService.EB_editUser)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        JSONObject jsonObject = JSON.parseObject(response.body());

                        if (jsonObject != null && jsonObject.getIntValue("code") == 0) {
                            successData.setValue(true);
                        } else {
                            errorLiveData.setValue(jsonObject.getString("msg"));
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.message());
                    }
                });
    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);

    }
}
