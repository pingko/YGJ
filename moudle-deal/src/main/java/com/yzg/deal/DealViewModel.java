package com.yzg.deal;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpLog;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.UserInfoBean;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.deal.deal.UserStoreBean;

import java.util.TreeMap;

public class DealViewModel extends MvvmBaseViewModel<IBaseView> {


    public MutableLiveData<UserStoreBean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MutableLiveData<Float> lastPrice = new MutableLiveData<>();


    protected void loadData() {
        TreeMap<String, String> map = new TreeMap<>();
        OkGo.<String>post(HttpService.Gold_custom_stock)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserStoreBean bean = null;
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            bean = JSONObject.parseObject(jsonObject.getString("data"), UserStoreBean.class);
                        } else {
                            HttpLog.e("没有登录");
                        }
                        successData.setValue(bean);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.body());
                    }
                });
    }

    protected void loadTodayPrice() {
        TreeMap<String, String> map = new TreeMap<>();
        OkGo.<String>get(HttpService.EB_Quotation_price)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            lastPrice.setValue(jsonObject.getFloatValue("lastPrice") / 1000);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.body());
                    }
                });
    }

    public MutableLiveData<UserInfoBean> userInfoLiveData = new MutableLiveData<>();

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
                                if (jsonObject != null && jsonObject.containsKey("msg")) {
                                    errorLiveData.setValue(jsonObject.getString("msg"));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);
    }
}
