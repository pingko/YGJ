package com.yzg.user;

import com.yzg.base.activity.IBaseView;
import com.yzg.common.contract.BaseCustomViewModel;


/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public interface IUserRegisterView extends IBaseView {

    /**
     * 数据加载完成
     *
     * @param viewModel data
     */
    void onDataLoadFinish(String viewModel);

}
