package com.yzg.home.jlyt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.http.HttpLog;
import com.yzg.base.http.HttpService;
import com.yzg.base.viewmodel.MvvmBaseViewModel;

import java.util.TreeMap;

public class HomeJlytDetailViewModel extends MvvmBaseViewModel<IjlytDetailView> {


    protected void loadData(String productId) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("productId", productId);
        OkGo.<String>post(HttpService.Gold_info_detail)
                .params(map)
//                .upJson(jsonObject.toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        parseJson(response.body());
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

    private void parseJson(String s) {
        JlytDetailBean detailBean = null;
        JSONObject jsonObject = JSON.parseObject(s);
        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
            detailBean = JSONObject.parseObject(jsonObject.getString("data"), JlytDetailBean.class);
        } else {
            HttpLog.e("没有登录");
        }
        if (getPageView() != null) {
            if (detailBean != null) {
                getPageView().onDataLoadFinish(detailBean);
            } else {
                getPageView().showEmpty();
            }
        }
    }



    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);

    }
}
