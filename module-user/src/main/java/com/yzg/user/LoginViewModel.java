package com.yzg.user;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.model.MarkettBean;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.user.bean.TokenBean;

import java.util.List;
import java.util.TreeMap;

public class LoginViewModel extends MvvmBaseViewModel<IBaseView> {

    public MutableLiveData<TokenBean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void login(String username, String password, String recommenderName, String rememberMe) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("rememberMe", rememberMe);
        if (!TextUtils.isEmpty(recommenderName)) {
            map.put("recommenderName", recommenderName);
        }
        OkGo.<String>post(HttpService.LOGIN)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        JSONObject jsonObject = JSON.parseObject(response.body());

                        if (jsonObject != null && jsonObject.getIntValue("code") == 0) {
                            TokenBean result = JSONObject.parseObject(jsonObject.getString("data"), TokenBean.class);
                            successData.setValue(result);
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
