package com.yzg.home.jlyt;

import android.graphics.Shader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.yzg.base.model.BaseModel;
import com.yzg.base.model.BasePagingModel;
import com.yzg.common.contract.TestApi;
import com.yzg.common.utils.SharedPreferenceUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.api.HttpService;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import io.reactivex.disposables.Disposable;

public class HomeJlytModel<T> extends BasePagingModel<T> {

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
                .headers("token", SharedPreferenceUtil.getToken())
                .upJson(jsonObject.toJSONString())
                .cacheKey(getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        loadFail(e.getMessage(), isRefresh);
                    }

                    @Override
                    public void onSuccess(String s) {
                        parseJson(s);
                    }
                });
    }

    public void refresh() {
        isRefresh = true;
        load();
    }

    private void parseJson(String s) {

        JSONObject jsonObject = JSON.parseObject(s);
        int total = jsonObject.getInteger("total").intValue();

        ArrayList<JlytBean> beans = (ArrayList<JlytBean>) JSONArray.parseArray(jsonObject.getString("rows"), JlytBean.class);

        HttpLog.e(beans.size()+"");
        loadSuccess((T) beans, total > 0, isRefresh);

    }


    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
    }
}
