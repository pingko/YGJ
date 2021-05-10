package com.yzg.home.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.yzg.base.fragment.MvvmBaseFragment;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentGddsDetailDealBinding;
import com.yzg.home.gdds.HomeGddsDetailDealViewModel;

public class HomeGddsDetailDealFragment extends MvvmLazyFragment<HomeFragmentGddsDetailDealBinding, HomeGddsDetailDealViewModel> {

    public static HomeGddsDetailDealFragment newInstance() {
        return new HomeGddsDetailDealFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_gdds_detail_deal;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected HomeGddsDetailDealViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeGddsDetailDealViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getDetail("2");
    }

    @Override
    protected void onRetryBtnClick() {

    }
}
