package com.yzg.home.main;

import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.ArrayList;

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
        extends MvmBaseViewModel<IHomeMainView, HomeMainModel>
        implements IModelListener<ArrayList<BaseCustomViewModel>> {
//    public ArrayList<String> banners;
    public ArrayList<Integer> banners;


    /**
     * 获取到数据
     * @param model model
     * @param data 数据
     */
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
    protected void initModel() {
        model = new HomeMainModel();
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
