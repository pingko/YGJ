package com.yzg.home.discover.adapter;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.discover.adapter.provider.GDDSSubscribeProvider;
import com.yzg.home.discover.adapter.provider.IDisCoverItemType;
import com.yzg.home.discover.adapter.provider.SubjectGDDSProvider;
import com.yzg.home.discover.bean.SubjectCardBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * 应用模块:
 * <p>
 * 类描述: 首页-发现 数据控制adapter
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class ProviderGDDSSubsAdapter
        extends BaseProviderMultiAdapter<BaseCustomViewModel> {

    public ProviderGDDSSubsAdapter() {
        super();
        // 注册具体业务提供者
        addItemProvider(new GDDSSubscribeProvider());


    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseCustomViewModel> data,
                              int position) {

        if (data.get(position) instanceof SubjectCardBean) {
            return IDisCoverItemType.THEME_GDDS_SUBS_VIEW;
        }
        return -1;
    }

}
