package com.yzg.user.address;

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
public interface IAddressView extends IBaseView {

    /**
     * 数据加载完成
     *
     * @param viewModel data
     */
    void onDataLoadFinish(BaseCustomViewModel viewModel);

}
