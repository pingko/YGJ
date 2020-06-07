package com.yzg.user;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.yzg.base.http.HttpLog;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.BaseModel;
import com.yzg.user.bean.TokenBean;

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
        JSONObject jsonObject = new JSONObject();
        OkGo.<String>post(HttpService.LOGIN)
                .params(map)
//                .upJson(jsonObject.toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
//                        HttpLog.e(response.body());
                        parseJson(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

//        disposable = EasyHttp.post(HttpService.LOGIN)
//                .params(map)
//                .cacheKey(getClass().getSimpleName())
//                .execute(new SimpleCallBack<String>() {
//
//                    @Override
//                    public void onError(ApiException e) {
//                        loadFail(e.getMessage());
//                    }
//
//
//                    @Override
//                    public void onSuccess(String s) {
//                        parseJson(s);
//                    }
//
//                });
    }

    private void parseJson(String s) {
        Logger.e(s);
        TokenBean result = JSONObject.parseObject(JSONObject.parseObject(s).getString("data"), TokenBean.class);
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
//        EasyHttp.cancelSubscription(disposable);
        OkGo.getInstance().cancelTag(this);
    }
}
