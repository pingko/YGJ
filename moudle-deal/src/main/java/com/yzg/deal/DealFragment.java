package com.yzg.deal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yzg.base.fragment.MvvmBaseFragment;
import com.yzg.base.viewmodel.IMvvmBaseViewModel;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.deal.databinding.DealFragmentLayoutBinding;

/**
 * 应用模块:
 * <p>
 * 类描述:交易首页
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-28
 */
@Route(path = RouterFragmentPath.Deal.PAGER_DEAL)
public class DealFragment
        extends MvvmBaseFragment<DealFragmentLayoutBinding, IMvvmBaseViewModel> {

//    private RecyclerAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.deal_fragment_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
    }

    private void start(Context context) {
    }

    private void initView() {
    }


    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected IMvvmBaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected void onRetryBtnClick() {

    }
}
