package com.yzg.home.jlyt;

import com.yzg.base.model.BasePagingModel;
import com.yzg.base.model.IPagingModelListener;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.ArrayList;
import java.util.TreeMap;

public class HomeJlytViewModel extends MvmBaseViewModel<IJLYTView, HomeJlytModel>
        implements IPagingModelListener<ArrayList<BaseCustomViewModel>> {

    @Override
    public void onLoadFinish(BasePagingModel model,
                             ArrayList<BaseCustomViewModel> data, boolean isEmpty,
                             boolean isFirstPage) {
        if (getPageView() != null) {
            if (isEmpty) {
                if (isFirstPage) {
                    getPageView().showEmpty();
                } else {
                    getPageView().onLoadMoreEmpty();
                }
            } else {
                getPageView().onDataLoadFinish(data, isFirstPage);
            }
        }

    }

    @Override
    public void onLoadFail(BasePagingModel model, String prompt,
                           boolean isRefresh) {
        if (getPageView() != null) {
            if (isRefresh) {
                getPageView().showFailure(prompt);
            } else {
                getPageView().onLoadMoreFailure(prompt);
            }
        }
    }


    public void setRequestParams(TreeMap map) {
        model.setMap(map);
    }


    public void tryToRefresh() {
        model.refresh();

    }


    @Override
    public void initModel() {
        model = new HomeJlytModel();
        model.register(this);
        model.getCacheDataAndLoad();
    }

    @Override
    public void detachUi() {
        super.detachUi();
        if (model != null) {
            model.unRegister(this);
        }

    }
}

