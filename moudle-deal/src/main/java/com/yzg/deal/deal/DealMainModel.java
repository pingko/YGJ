package com.yzg.deal.deal;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yzg.base.model.BaseModel;
import com.yzg.common.contract.TestApi;
import com.zhouyou.http.EasyHttp;

import java.util.TreeMap;

import io.reactivex.disposables.Disposable;

public class DealMainModel<T> extends BaseModel<T> {

    private Disposable disposable;

    public TreeMap map = new TreeMap();

    public void setMap(TreeMap map) {
        this.map = map;
    }

    @Override
    protected void load() {

//        disposable = EasyHttp.get(HttpService.REGISTER)
//                .params(map)
//                .cacheKey(getClass().getSimpleName())
//                .execute(new SimpleCallBack<String>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        loadFail(e.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                        parseJson(s);
//                    }
//                });
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
