package com.yzg.deal.deal;

import android.net.MacAddress;
import android.text.TextUtils;

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
import com.yzg.common.contract.AddressBean;

import java.util.HashMap;
import java.util.TreeMap;

public class DealMainViewModel extends MvvmBaseViewModel<IBaseView> {

    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MutableLiveData<String> saleResponse = new MutableLiveData<>();
    public MutableLiveData<String> takeResponse = new MutableLiveData<>();
    public MutableLiveData<String> takeOrderNo = new MutableLiveData<>();//提货交易流水号
    public MutableLiveData<String> buyResponse = new MutableLiveData<>();//买入操作
    public MutableLiveData<String> buySuccessResponse = new MutableLiveData<>();


    protected void paySuccess(String trade_no, String acctNo, String orderNo) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("dealStat", trade_no.length() > 0 ? "1" : "0");
        map.put("acctNo", acctNo);
        map.put("custUm", trade_no);
        map.put("orderNo", orderNo);
        OkGo.<String>post(HttpService.EB_Pay_Success)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code")) {
                            String code = jsonObject.getString("code");
                            String msg;
                            if ("0".equals(code)) {
                                buySuccessResponse.setValue("buySuccess");
                            } else {
                                if (jsonObject.containsKey("msg")) {
                                    buySuccessResponse.setValue(jsonObject.getString("msg"));
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

    protected void takeSuccess(String trade_no, String acctNo, String orderNo, String weight, boolean isSucc) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("dealStat", isSucc ? (trade_no.length() > 0 ? "1" : "0") : "0");
        map.put("acctNo", acctNo);
        map.put("tradeNo", trade_no);
        map.put("aipAmount", weight);
        map.put("orderNo", isSucc ? orderNo : trade_no);
        OkGo.<String>post(HttpService.EB_Take_Success)
                .upJson(JSON.toJSONString(map))
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
                                    takeResponse.setValue(jsonObject.getString("msg"));
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


    protected void take(String aipAmount, String takeCharge, String sirverPrice, AddressBean addressBean) {
        HashMap<String, String> map = new HashMap<>();
        map.put("aipAmount", aipAmount);
        map.put("weight", aipAmount);
        map.put("charge", takeCharge);
        map.put("price", sirverPrice + "");
        map.put("receiveUserName", addressBean.getName());
        map.put("receivePhone", addressBean.getPhone());
        map.put("receiveProvince", addressBean.getProvince());
        map.put("receiveCity", addressBean.getCity());
        map.put("receiveArea", addressBean.getDistrict());
        map.put("receiveDetail", addressBean.getProvince() + addressBean.getCity() + addressBean.getDistrict() + addressBean.getAddress());


        OkGo.<String>post(HttpService.EB_Take)
                .upJson(JSON.toJSONString(map))
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code")) {
                            String code = jsonObject.getString("code");
                            String msg;
                            if ("0".equals(code)) {
//                                takeResponse.setValue("提货成功");
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = JSON.parseObject(data);
                                String orderNo = jsonObject1.getString("orderNo");
                                takeOrderNo.setValue(orderNo);
                            } else {
                                if (jsonObject.containsKey("msg")) {
                                    msg = jsonObject.getString("msg");
//                                    takeResponse.setValue(msg);
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.body());
                        takeOrderNo.setValue("error");
                    }
                });
    }

    protected void sale(String aipAmount) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("aipAmount", aipAmount);
        OkGo.<String>post(HttpService.EB_Sale)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code")) {
                            String code = jsonObject.getString("code");
                            String msg;
                            if ("0".equals(code)) {
                                saleResponse.setValue("卖出成功");
                            } else {
                                if (jsonObject.containsKey("msg")) {
                                    msg = jsonObject.getString("msg");
                                    saleResponse.setValue(msg);
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

    /**
     * @param sirverPrice 近日实时银价
     * @param weight      购买多少克
     * @param money       总价多少钱
     * @param acctNo      账号
     */
    protected void buySirver(double sirverPrice, String weight, String money, String acctNo) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("aipPrice", sirverPrice + "");
        map.put("aipAmount", weight);
        map.put("acctNo", acctNo);
        map.put("aipAmountFare", "0");
        map.put("aipMoneyFare", "0");
        map.put("aipMoney", money);
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
                                    buyResponse.setValue(msg);
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

    public MutableLiveData<UserStoreBean> userDuccessData = new MutableLiveData<>();
    public MutableLiveData<Float> lastPrice = new MutableLiveData<>();

    public void loadUserData() {
        TreeMap<String, String> map = new TreeMap<>();
        OkGo.<String>post(HttpService.Gold_custom_stock)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserStoreBean bean = null;
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("0")) {
                            bean = JSONObject.parseObject(jsonObject.getString("data"), UserStoreBean.class);
                        } else {
                            HttpLog.e("没有登录");
                        }
                        userDuccessData.setValue(bean);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.body());
                    }
                });
    }

    public void loadTodayPrice() {
        TreeMap<String, String> map = new TreeMap<>();
        OkGo.<String>get(HttpService.EB_Quotation_price)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            lastPrice.setValue(jsonObject.getFloatValue("lastPrice") / 1000);
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
