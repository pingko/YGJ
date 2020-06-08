package com.yzg.deal;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpLog;
import com.yzg.base.http.HttpService;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.deal.deal.UserStoreBean;

import java.util.TreeMap;

public class DealViewModel extends MvvmBaseViewModel<IBaseView> {


    public MutableLiveData<UserStoreBean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    protected void loadData() {
        TreeMap<String, String> map = new TreeMap<>();
        OkGo.<String>post(HttpService.Gold_custom_stock)
                .params(map)
//                .upJson(jsonObject.toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserStoreBean bean =null;
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            bean = JSONObject.parseObject(jsonObject.getString("data"),UserStoreBean.class);
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


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);
    }
}
