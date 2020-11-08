package com.yzg.home.main;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.model.MarkettBean;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.jlyt.JlytBean;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class HomeMainViewModel
        extends MvvmBaseViewModel<IBaseView> {
    public ArrayList<Integer> banners;


    public MutableLiveData<List<MarkettBean>> successData = new MutableLiveData<>();
    public MutableLiveData<List<JlytBean>> jlytBeansData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MutableLiveData<String> buyResponse = new MutableLiveData<>();

    public void loadMarkets(int num,int size,String variety) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("pageNum",String.valueOf(num));
        map.put("pageSize",String.valueOf(size));
        map.put("variety",variety);
        OkGo.<String>post(HttpService.EB_Quotation_priceList)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (jsonObject != null && jsonObject.containsKey("rows")) {
                                List<MarkettBean> beans = JSONObject.parseArray(jsonObject.getString("rows"), MarkettBean.class);
                                successData.setValue(beans);
                            } else {
                                errorLiveData.setValue(response.message());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        errorLiveData.setValue(response.body());
                    }
                });
    }

    public void loadJlyt() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", MmkvHelper.getInstance().getMmkv().decodeString("token", ""));
        OkGo.<String>post(HttpService.Gold_info_list)
                .upJson(jsonObject.toJSONString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            int total = jsonObject.getInteger("total").intValue();
                            List<JlytBean> beans = JSONArray.parseArray(jsonObject.getString("rows"), JlytBean.class);
                            jlytBeansData.setValue(beans);
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
