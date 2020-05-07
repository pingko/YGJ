package com.yzg.home.gdds.gddslist;

import com.yzg.base.activity.IBasePagingView;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public interface IGddsRankListView extends IBasePagingView {

    /**
     * @param data 数据
     * @param isFirstPage 是否是第一页
     * */
    void onDataLoaded(List<BaseCustomViewModel> data, boolean isFirstPage);
}
