package com.yzg.user.register;

import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableInt;
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
    public MutableLiveData<Boolean> editSuccessData = new MutableLiveData<>();
    public MutableLiveData<Boolean> successCheckCodeData = new MutableLiveData<>();
    public MutableLiveData<Boolean> successSendCodeData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MutableLiveData<String> codeLiveData = new MutableLiveData<>();

    protected void loadData(TreeMap map, String url) {
        OkGo.<String>get(url)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            if (url.equals(HttpService.REGISTER)) {
                                successData.setValue(true);
                            } else {
                                editSuccessData.setValue(true);
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

    protected void sendCode(int type, String phone, String code) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("phone", phone);
        if (!TextUtils.isEmpty(code))
            map.put("num", code);
        map.put("type", type == 1 ? "2" : "1");
        OkGo.<String>get(TextUtils.isEmpty(code) ? HttpService.EB_sendCode : HttpService.EB_checkCode)
                .tag(this)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            Log.e("aaa", code);
                            if (TextUtils.isEmpty(code)) {
                                successSendCodeData.setValue(true);
                            } else {
                                codeLiveData.setValue(code);
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
