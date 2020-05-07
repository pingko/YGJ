package com.yzg.community.adapter;

import com.yzg.base.activity.IBasePagingView;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.ArrayList;
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
public interface IQuotationContentView extends IBasePagingView {
    /**
     * 数据加载完成
     * @param  data
     * */
    void onDataLoaded(List<BaseCustomViewModel> data, boolean isFirstPage);
}
