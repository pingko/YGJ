package com.yzg.home.gdds;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpLog;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.gdds.gddslist.GddsBean;
import com.yzg.home.jlyt.JlytBean;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class HomeGDDSListViewModel
        extends MvvmBaseViewModel<IBaseView> {
    public ArrayList<Integer> banners;
    public MutableLiveData<Boolean> successData = new MutableLiveData<>();
    public MutableLiveData<List<GddsBean>> gddsBeanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void getList(String account, String name, String loginName) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("token", MmkvHelper.getInstance().getMmkv().decodeString("token",""));
        TreeMap<String, String> map = new TreeMap<>();
//        map.put("token", MmkvHelper.getInstance().getMmkv().decodeString("token",""));
        map.put("type", "1");
        OkGo.<String>post(HttpService.EB_jslist)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        if (!TextUtils.isEmpty(data)) {
                            if (data.startsWith("{")) {
                                JSONObject jsonObject = JSON.parseObject(data);
                                int total = jsonObject.getInteger("total").intValue();
                                List<GddsBean> beans = JSONArray.parseArray(jsonObject.getString("rows"), GddsBean.class);
                                gddsBeanMutableLiveData.setValue(beans);
                            } else {
                                LiveEventBus.get("reLogin").post(0);
                            }
                        }
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

}