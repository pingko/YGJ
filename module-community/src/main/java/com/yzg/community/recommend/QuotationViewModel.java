package com.yzg.community.recommend;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpService;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.community.recommend.bean.QuotationBean;

import java.util.TreeMap;

public class QuotationViewModel extends MvvmBaseViewModel<IBaseView> {


    public MutableLiveData<QuotationBean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    protected void loadData() {
        TreeMap<String, String> map = new TreeMap<>();
        OkGo.<String>get(HttpService.EB_Quotation_price)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        QuotationBean bean =JSONObject.parseObject(response.body(),QuotationBean.class);
                        successData.setValue(bean);
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
