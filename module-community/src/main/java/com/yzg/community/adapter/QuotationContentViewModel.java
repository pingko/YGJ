package com.yzg.community.adapter;

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
import com.yzg.base.model.BasePagingModel;
import com.yzg.base.model.IPagingModelListener;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.community.recommend.bean.QuotationBean;

import java.util.ArrayList;
import java.util.TreeMap;


/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
//public class QuotationContentViewModel
//        extends MvmBaseViewModel<IQuotationContentView, QuotationContentModel>
//        implements IPagingModelListener<ArrayList<BaseCustomViewModel>> {
public class QuotationContentViewModel
        extends MvvmBaseViewModel<IBaseView> {
//    @Override
//    protected void initModel() {
//
//    }
//
//    public void initModel(String typeName) {
//        model = new QuotationContentModel(typeName);
//        model.register(this);
////        model.getCacheDataAndLoad();
//    }
//
//    @Override
//    public void onLoadFinish(BasePagingModel model,
//                             ArrayList<BaseCustomViewModel> data, boolean isEmpty,
//                             boolean isFirstPage) {
//        if (getPageView() != null) {
//            if (isEmpty) {
//                if (isFirstPage) {
//                    getPageView().showEmpty();
//                } else {
//                    getPageView().onLoadMoreEmpty();
//                }
//            } else {
//                getPageView().onDataLoaded(data,isFirstPage);
//            }
//
//        }
//
//    }
//
//    @Override
//    public void onLoadFail(BasePagingModel model, String prompt,
//                           boolean isFirstPage) {
//        if (getPageView() != null) {
//            if (isFirstPage) {
//                getPageView().showFailure(prompt);
//            } else {
//                getPageView().onLoadMoreFailure(prompt);
//            }
//        }
//    }
//
//    @Override
//    public void detachUi() {
//        super.detachUi();
//        if (model != null) {
//            model.unRegister(this);
//        }
//    }
//
//    public void tryRefresh() {
//        model.refresh();
//    }
//
//    public void loadMore() {
//        model.loadMore();
//    }


    public MutableLiveData<QuotationBean> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MutableLiveData<String> buyResponse = new MutableLiveData<>();

    public void loadData(String productId) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("pageNum","1");
        map.put("pageSize","10");
        OkGo.<String>post(HttpService.EB_Quotation_priceList)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
//                            JSONObject jsonObject = JSON.parseObject(response.body());
//                            lastPrice.setValue(jsonObject.getFloatValue("lastPrice") / 1000);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        errorLiveData.setValue(response.body());
                    }
                });
    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);

    }
}
