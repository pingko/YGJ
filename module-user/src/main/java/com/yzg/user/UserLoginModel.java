package com.yzg.user;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.BaseModel;
import com.yzg.user.bean.LoginResponseBean;

import java.util.TreeMap;

import io.reactivex.disposables.Disposable;

public class UserLoginModel<T> extends BaseModel<T> {

    private Disposable disposable;

    public TreeMap map = new TreeMap();

    public void setMap(TreeMap map) {
        this.map = map;
    }

    @Override
    protected void load() {
        OkGo.<String>post(HttpService.LOGIN)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        Logger.e(s);

                        LoginResponseBean result = JSONObject.parseObject(JSONObject.parseObject(s).getString("data"), LoginResponseBean.class);
                        loadSuccess((T) result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }



    @Override
    public void cancel() {
        super.cancel();
//        EasyHttp.cancelSubscription(disposable);
        OkGo.getInstance().cancelTag(this);
    }
}
