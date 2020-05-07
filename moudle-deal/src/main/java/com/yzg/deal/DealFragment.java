package com.yzg.deal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yzg.base.fragment.MvvmBaseFragment;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.base.viewmodel.IMvvmBaseViewModel;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.deal.databinding.DealFragmentMainBinding;
import com.yzg.deal.deal.DealMainActivity;

/**
 * 应用模块:
 * <p>
 * 类描述:交易首页
 * <p>
 *
 */
@Route(path = RouterFragmentPath.Deal.PAGER_DEAL)
public class DealFragment
        extends MvvmLazyFragment<DealFragmentMainBinding, IMvvmBaseViewModel> implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.deal_fragment_main;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        binding.tvBuy.setOnClickListener(this);
        binding.tvSale.setOnClickListener(this);
        binding.tvProduct.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_buy||view.getId() == R.id.tv_sale||view.getId() == R.id.tv_product) {
            startActivity(new Intent(getContext(), DealMainActivity.class));
        }
    }
}
