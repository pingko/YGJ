package com.yzg.user.register;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.http.HttpService;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.user.IUserRegisterView;

import java.util.TreeMap;

public class RegisterViewModel extends MvvmBaseViewModel<IUserRegisterView> {

    protected void loadData(TreeMap map) {
        OkGo.<String>get(HttpService.REGISTER)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String data = null;

                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            data = "注册成功";
                        }
                        if (getPageView() != null) {
                            if (data != null) {
                                getPageView().onDataLoadFinish(data);
                            } else {
                                getPageView().showEmpty();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (getPageView() != null) {
                            getPageView().showFailure(response.body());
                        }
                    }


                });

    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);
    }
}
