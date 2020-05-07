package com.yzg.user;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.yzg.base.model.BaseModel;
import com.yzg.user.bean.TokenBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.api.HttpService;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.TreeMap;

import io.reactivex.disposables.Disposable;

public class UserModel<T> extends BaseModel<T> {

    private Disposable disposable;

    public TreeMap map = new TreeMap();

    public void setMap(TreeMap map) {
        this.map = map;
    }
    @Override
    protected void load() {

        disposable = EasyHttp.post(HttpService.LOGIN)
                .params(map)
                .cacheKey(getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {

                    @Override
                    public void onError(ApiException e) {
                        loadFail(e.getMessage());
                    }


                    @Override
                    public void onSuccess(String s) {
                        parseJson(s);
                    }

                });
    }

    private void parseJson(String s) {
        Logger.d(s);
        TokenBean result = JSONObject.parseObject(JSONObject.parseObject(s).getString("data"),TokenBean.class);
        loadSuccess((T) result);

//        try {
//            TokenBean bean = GsonUtils.fromLocalJson(String.valueOf(new JSONObject(s).getJSONObject("data")), TokenBean.class);
//            loadSuccess((T) bean);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }


    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
    }
}
