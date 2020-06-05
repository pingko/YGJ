package com.yzg.home.jlyt;

import android.graphics.Shader;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.yzg.base.model.BaseModel;
import com.yzg.common.contract.TestApi;
import com.yzg.common.utils.SharedPreferenceUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.api.HttpService;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import java.util.TreeMap;

import io.reactivex.disposables.Disposable;

public class HomeJlytModel<T> extends BaseModel<T> {

    private Disposable disposable;

    public TreeMap map = new TreeMap();

    public void setMap(TreeMap map) {
        this.map = map;
    }

    @Override
    protected void load() {

        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("token", SharedPreferenceUtil.getToken());
        disposable = EasyHttp.post(HttpService.Gold_info_list)
                .headers("token",SharedPreferenceUtil.getToken())
                .upJson(jsonObject.toJSONString())
                .cacheKey(getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        loadFail(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        HttpLog.e(s);
//                        parseJson(s);
                    }
                });
    }

    private void parseJson(String s) {

        try {
            TestApi api = JSONObject.parseObject(s, TestApi.class);
            if (api.getCode() == 0) {
                loadSuccess((T) api);
            } else {
                loadFail(api.getMsg());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
    }
}
