package com.yzg.home.jlyt;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mmkv.MMKV;
import com.yzg.base.model.BasePagingModel;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.router.RouterActivityPath;
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
        jsonObject.put("token", MmkvHelper.getInstance().getMmkv().decodeString("token",""));
        disposable = EasyHttp.post(HttpService.Gold_info_list)
                .upJson(jsonObject.toJSONString())
                .addCookie("token", MmkvHelper.getInstance().getMmkv().decodeString("token",""))
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
        EasyHttp.cancelSubscription(disposable);
    }
}
