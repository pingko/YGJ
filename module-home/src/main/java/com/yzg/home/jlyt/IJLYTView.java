package com.yzg.home.jlyt;

import com.yzg.base.activity.IBasePagingView;
import com.yzg.base.activity.IBaseView;
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
public interface IJLYTView extends IBasePagingView {

    /**
     * 数据加载完成
     *
     * @param viewModel data
     */
    /**
     * 数据加载完成
     *
     * @param viewModels data
     */
    void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels,
                          boolean isFirstPage);

}
