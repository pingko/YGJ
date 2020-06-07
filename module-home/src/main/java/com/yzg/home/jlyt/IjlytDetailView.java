package com.yzg.home.jlyt;

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
public interface IjlytDetailView extends IBaseView {

    /**
     * 数据加载完成
     *
     * @param viewModel data
     */
    void onDataLoadFinish(JlytDetailBean viewModel);

}
