package com.yzg.user.register;

import android.text.TextUtils;

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

public class RegisterViewModel extends MvvmBaseViewModel<IBaseView> {
    public MutableLiveData<Boolean> successData = new MutableLiveData<>();
    public MutableLiveData<Boolean> successCheckCodeData = new MutableLiveData<>();
    public MutableLiveData<Boolean> successSendCodeData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    protected void loadData(TreeMap map) {
        OkGo.<String>get(HttpService.REGISTER)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            successData.setValue(true);
                        } else {
                            if (jsonObject.containsKey("msg")) {
                                errorLiveData.setValue(jsonObject.getString("msg"));
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (!TextUtils.isEmpty(response.message()))
                            errorLiveData.setValue(response.message());
                    }


                });

    }

    protected void sendCode(String phone, String code) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("phone", phone);
        if (!TextUtils.isEmpty(code))
            map.put("num", code);
        OkGo.<String>get(HttpService.EB_sendCode)
                .tag(this)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            if (TextUtils.isEmpty(code)) {
                                successSendCodeData.setValue(true);
                            } else {
                                successCheckCodeData.setValue(true);
                            }
                        } else {
                            if (jsonObject.containsKey("msg")) {
                                errorLiveData.setValue(jsonObject.getString("msg"));
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (!TextUtils.isEmpty(response.message()))
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
