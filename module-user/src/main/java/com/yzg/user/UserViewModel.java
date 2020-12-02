package com.yzg.user;

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
import com.yzg.user.bean.UserStoreBean;

import java.util.TreeMap;

public class UserViewModel extends MvvmBaseViewModel<IBaseView> {


    public MutableLiveData<UserStoreBean> userBean = new MutableLiveData<>();

    protected void loadData() {
        TreeMap<String, String> map = new TreeMap<>();
        OkGo.<String>post(HttpService.Gold_custom_stock)
//                .params(map)
                .upJson(new JSONObject().toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserStoreBean bean =null;
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            bean = JSONObject.parseObject(jsonObject.getString("data"),UserStoreBean.class);
                            userBean.setValue(bean);
                        } else {
                            if(jsonObject!=null &&jsonObject.containsKey("msg")){
                                HttpLog.e(jsonObject.getString("msg"));
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
