package com.yzg.home.daily;

import com.yzg.base.activity.IBasePagingView;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.ArrayList;


/**
 * 应用模块: daily
 * <p>
 * 类描述: UI 更新
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-14
 */
public interface IDailyView  extends IBasePagingView {

    /**
     * 数据加载完成
     *
     * @param viewModels data
     */
    void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels,
                          boolean isFirstPage);

}
