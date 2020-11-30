package com.yzg.user.setting;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.UserInfoBean;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.viewmodel.MvvmBaseViewModel;

import java.util.TreeMap;

public class UserSettingModel extends MvvmBaseViewModel<IBaseView> {

    public MutableLiveData<UserInfoBean> userInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void getUser() {
        TreeMap<String, String> map = new TreeMap<>();
        String acctNo = MmkvHelper.getInstance().getMmkv().decodeString("acctNo");
        map.put("loginName", acctNo);
        OkGo.<String>get(HttpService.EB_getUser)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (jsonObject != null && jsonObject.containsKey("code") && 0 == jsonObject.getIntValue("code")) {
                                UserInfoBean userInfoBean = JSON.parseObject(jsonObject.getString("data"), UserInfoBean.class);
                                userInfoLiveData.setValue(userInfoBean);
                            } else {
                                errorLiveData.setValue(response.message());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.body());
                    }
                });
    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);

    }
}
