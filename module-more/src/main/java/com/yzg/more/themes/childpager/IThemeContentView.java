package com.yzg.more.themes.childpager;

import java.util.ArrayList;

import com.yzg.base.activity.IBasePagingView;
import com.yzg.common.contract.BaseCustomViewModel;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public interface IThemeContentView extends IBasePagingView {
    /**
     * 数据加载完成
     * @param viewModels data
     * */
    void onDataLoaded(ArrayList<BaseCustomViewModel> viewModels);
}
