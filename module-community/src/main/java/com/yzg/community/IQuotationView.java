package com.yzg.community;

import com.yzg.base.activity.IBasePagingView;
import com.yzg.base.activity.IBaseView;
import com.yzg.common.contract.BaseCustomViewModel;
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
public interface IQuotationView extends IBaseView {

    /**
     * 数据加载完成
     * @param viewModels data
     * */
//    void onDataLoaded(ArrayList<BaseCustomViewModel> viewModels);
    /**
     * 数据加载完成
     * @param tabs tabs
     * */
    void onDataLoaded(ArrayList<Tabs> tabs);
}
