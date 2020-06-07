package com.yzg.user;

import androidx.databinding.ObservableBoolean;

import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.TreeMap;

public class LoginViewModel extends MvmBaseViewModel<IUserLoginView, UserLoginModel>
        implements IModelListener<BaseCustomViewModel> {

//    public MutableLiveData<Boolean> isLoginLivedata = new MutableLiveData<>();
    public ObservableBoolean isLoginLivedata=new ObservableBoolean(false);


    public void setRequestParams(TreeMap map) {
        model.setMap(map);
    }
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
        model = new UserLoginModel();
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
