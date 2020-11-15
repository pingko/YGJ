package com.yzg.home.jlyt;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpLog;
import com.yzg.base.http.HttpService;
import com.yzg.base.viewmodel.MvvmBaseViewModel;

import java.util.TreeMap;

public class HomeJlytDetailViewModel extends MvvmBaseViewModel<IBaseView> {


    public MutableLiveData<JlytDetailBean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MutableLiveData<String> buyResponse = new MutableLiveData<>();

    protected void loadData(String productId) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("productId", productId);
        OkGo.<String>post(HttpService.Gold_info_detail)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JlytDetailBean detailBean = null;
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            detailBean = JSONObject.parseObject(jsonObject.getString("data"), JlytDetailBean.class);
                        } else {
                            HttpLog.e("没有登录");
                        }
                        successData.setValue(detailBean);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.body());
                    }
                });
    }

    protected void buyjLYT(String productId, String weight) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("productId", productId);
        map.put("aipAmount", weight);
        OkGo.<String>post(HttpService.EB_BuyJLYT)
                .params(map)
//                .upJson(jsonObject.toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JlytDetailBean detailBean = new JlytDetailBean();
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code")) {
                            String code = jsonObject.getString("code");
                            String msg;
                            if ("0".equals(code)) {
                                buyResponse.setValue("购买成功");
                            } else {
                                msg = jsonObject.getString("msg");
                                buyResponse.setValue(msg);
                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.body());
                    }
                });
    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);
    }
}
