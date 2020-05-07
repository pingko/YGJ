package com.yzg.user.register;

import androidx.lifecycle.MutableLiveData;

import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.user.IUserLoginView;

import java.util.TreeMap;

public class RegisterViewModel extends MvmBaseViewModel<IUserLoginView, RegisterModel>
        implements IModelListener<BaseCustomViewModel> {


    public void setRequestParams(TreeMap map) {
        model.setMap(map);
    }

    public MutableLiveData<Integer> liveData = new MutableLiveData<>();



    @Override
    public void onLoadFinish(BaseModel model, BaseCustomViewModel data) {
        if (getPageView() != null) {
            if (data != null) {
                getPageView().onDataLoadFinish(data);
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
        model = new RegisterModel();
        model.register(this);
//        model.getCacheDataAndLoad();
    }

    @Override
    public void detachUi() {
        super.detachUi();
        if (model != null) {
            model.unRegister(this);
        }

    }
}
