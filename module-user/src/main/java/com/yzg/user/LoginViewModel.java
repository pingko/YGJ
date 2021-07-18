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
import com.yzg.user.bean.LoginResponseBean;

import java.util.TreeMap;

public class LoginViewModel extends MvvmBaseViewModel<IBaseView> {

    public MutableLiveData<LoginResponseBean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void login(String username, String password, String rememberMe) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("rememberMe", rememberMe);

        OkGo.<String>post(HttpService.LOGIN)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        JSONObject jsonObject = JSON.parseObject(response.body());

                        if (jsonObject != null && jsonObject.getIntValue("code") == 0) {
                            LoginResponseBean result = JSONObject.parseObject(jsonObject.getString("data"), LoginResponseBean.class);
                            successData.setValue(result);
                        } else {
                            errorLiveData.setValue(jsonObject.getString("msg"));
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue("登录失败");
                    }
                });
    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);

    }
}
