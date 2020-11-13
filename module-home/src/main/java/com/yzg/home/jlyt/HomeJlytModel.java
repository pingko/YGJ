package com.yzg.home.jlyt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.http.HttpLog;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.BasePagingModel;
import com.yzg.base.storage.MmkvHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import io.reactivex.disposables.Disposable;

public class HomeJlytModel<T> extends BasePagingModel<T> {


    public TreeMap map = new TreeMap();

    public void setMap(TreeMap map) {
        this.map = map;
    }

    @Override
    protected void load() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", MmkvHelper.getInstance().getMmkv().decodeString("token",""));
        TreeMap<String,String> map = new TreeMap<>();
        map.put("token", MmkvHelper.getInstance().getMmkv().decodeString("token",""));
        OkGo.<String>post(HttpService.Gold_info_list)
//                .params(map)
                .upJson(jsonObject.toJSONString())
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

    }

    public void refresh() {
        isRefresh = true;
        load();
    }

    private void parseJson(String s) {
        List<JlytBean> viewModels = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(s);
        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("1")){
            HttpLog.e("没有登录");
        }else {
            int total = jsonObject.getInteger("total").intValue();
            List<JlytBean> beans =  JSONArray.parseArray(jsonObject.getString("rows"), JlytBean.class);
            viewModels.addAll(beans);
            loadSuccess((T) viewModels, total == 0, isRefresh);
        }

    }


    @Override
    public void cancel() {
        super.cancel();
//        EasyHttp.cancelSubscription(disposable);
    }
}
