package com.yzg.community;

import com.yzg.base.model.BaseModel;
import com.yzg.base.model.IModelListener;
import com.yzg.base.viewmodel.MvmBaseViewModel;
import com.yzg.community.bean.Tabs;

import java.util.ArrayList;


/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-22
 */
public class QuotationFragmentViewModel
    extends MvmBaseViewModel<IQuotationView, QuotationModel>
    implements IModelListener<ArrayList<Tabs>>
{
    
    @Override
    protected void initModel()
    {
        model = new QuotationModel();
        model.register(this);
        model.getCacheDataAndLoad();
    }

    @Override
    public void detachUi() {
        super.detachUi();
        if (model != null){
            model.unRegister(this);
        }
    }

    @Override
    public void onLoadFinish(BaseModel model, ArrayList<Tabs> data)
    {
        if (getPageView() != null)
        {
            if (data != null)
            {
                getPageView().onDataLoaded(data);
            }
            else
            {
                getPageView().showEmpty();
            }
        }
    }
    
    @Override
    public void onLoadFail(BaseModel model, String prompt)
    {
        if (getPageView() != null)
        {
            getPageView().showFailure(prompt);
        }
    }
}
