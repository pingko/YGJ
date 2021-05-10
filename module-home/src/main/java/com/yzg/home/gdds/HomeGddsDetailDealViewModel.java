package com.yzg.home.gdds;

import androidx.lifecycle.MutableLiveData;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpService;
import com.yzg.base.viewmodel.MvvmBaseViewModel;

import java.util.TreeMap;

public class HomeGddsDetailDealViewModel
        extends MvvmBaseViewModel<IBaseView> {
    public MutableLiveData<Boolean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void getDetail(String id) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("token", MmkvHelper.getInstance().getMmkv().decodeString("token",""));
        TreeMap<String, String> map = new TreeMap<>();
//        map.put("token", MmkvHelper.getInstance().getMmkv().decodeString("token",""));
        map.put("id", "2");
        OkGo.<String>get(HttpService.EB_js_detail)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
//                        String data = response.body();
//                        if (!TextUtils.isEmpty(data)) {
//                            if (data.startsWith("{")) {
//                                JSONObject jsonObject = JSON.parseObject(data);
//                                int total = jsonObject.getInteger("total").intValue();
//                                List<GddsBean> beans = JSONArray.parseArray(jsonObject.getString("rows"), GddsBean.class);
//                                gddsBeanMutableLiveData.setValue(beans);
//                            } else {
//                                LiveEventBus.get("reLogin").post(0);
//                            }
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);
    }

//    {
//        "total":1,
//            "rows":[
//        {
//            "id":2,
//                "userId":8,
//                "userName":"test",
//                "userBrief":"用户简介字段，非必填",
//                "userImg":null,
//                "attentionAmount":0,
//                "fansAmount":0,
//                "subscribeAmount":2,
//                "yieldRate":"0.0",
//                "createDate":"2021-01-28 21:59:38",
//                "audit":1,
//                "auditUserId":90,
//                "auditUserName":"lee",
//                "updateDate":"2021-01-31 21:01:22",
//                "pageIndex":null,
//                "pageSize":null,
//                "type":null,
//                "virtualGold":"500.0",
//                "isSubscribe":null
//        }
//    ],
//        "code":0,
//            "msg":0,
//            "message":null
//    }
}