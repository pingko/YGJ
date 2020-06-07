package com.yzg.user;

import com.yzg.base.activity.IBaseView;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.user.bean.UserStoreBean;


/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public interface IUserMainView extends IBaseView {

    /**
     * 数据加载完成
     *
     * @param s data
     */
    void onDataLoadFinish(UserStoreBean s);

}
