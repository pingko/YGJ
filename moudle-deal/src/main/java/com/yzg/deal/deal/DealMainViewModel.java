package com.yzg.deal.deal;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpService;
import com.yzg.base.viewmodel.MvvmBaseViewModel;

import java.util.TreeMap;

public class DealMainViewModel extends MvvmBaseViewModel<IBaseView> {

    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MutableLiveData<String> takeResponse = new MutableLiveData<>();
    public MutableLiveData<String> buyResponse = new MutableLiveData<>();
    public MutableLiveData<Boolean> buySuccessResponse = new MutableLiveData<>();

    protected void paySuccess(String trade_no, String acctNo,String orderNo) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("dealStat", trade_no.length() > 0 ? "1" : "0");
        map.put("acctNo", acctNo);
        map.put("custUm", trade_no);
        map.put("orderNo", orderNo);
        OkGo.<String>post(HttpService.EB_Pay_Success)
                .params(map)
//                .upJson(jsonObject.toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code")) {
                            String code = jsonObject.getString("code");
                            String msg;
                            if ("0".equals(code)) {
                                buySuccessResponse.setValue(true);
                            } else {
                                if (jsonObject.containsKey("msg")) {
                                    msg = jsonObject.getString("msg");
                                }
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


    protected void take(String aipAmount) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("aipAmount", aipAmount);
        OkGo.<String>post(HttpService.EB_Take)
                .params(map)
//                .upJson(jsonObject.toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code")) {
                            String code = jsonObject.getString("code");
                            String msg;
                            if ("0".equals(code)) {
                                takeResponse.setValue("提货成功");
                            } else {
                                if (jsonObject.containsKey("msg")) {
                                    msg = jsonObject.getString("msg");
                                    takeResponse.setValue(msg);
                                }
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

    protected void buySirver(String weight, double money, String acctNo) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("aipAmount", weight);
        map.put("acctNo", acctNo);
        map.put("aipAmountFare", "0");
        map.put("aipMoneyFare", "0");
        map.put("aipMoney", String.valueOf(money));
        map.put("bsFlag", "");
        map.put("subAccountNo", "");
        OkGo.<String>post(HttpService.EB_Pay)
                .params(map)
//                .upJson(jsonObject.toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code")) {
                            String code = jsonObject.getString("code");
                            String msg;
                            if ("0".equals(code)) {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = JSON.parseObject(data);
                                String orderNo = jsonObject1.getString("orderNo");
                                buyResponse.setValue(orderNo);
                            } else {
                                if (jsonObject.containsKey("msg")) {
                                    msg = jsonObject.getString("msg");
                                    buyResponse.setValue("error " + msg);
                                }
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
