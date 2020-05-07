package com.yzg.home.gdds;

import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.ArrayList;

public class HomeGddsDetailIDealViewModel
        extends MvmBaseViewModel<IHomeGDDSListView, HomeGddsDetailIDealModel>
        implements IModelListener<ArrayList<BaseCustomViewModel>> {

    @Override
    public void onLoadFinish(BaseModel model,
                             ArrayList<BaseCustomViewModel> data) {
        if (getPageView() != null) {
            if (data != null && data.size() > 0) {
                getPageView().onDataLoadFinish(data, false);
            } else {
                getPageView().showEmpty();
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        if (getPageView() != null) {
            getPageView().showFailure(prompt);
        }
    }

    public void tryToRefresh() {
        model.load();
    }

    @Override
    public void initModel() {
        model = new HomeGddsDetailIDealModel();
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
