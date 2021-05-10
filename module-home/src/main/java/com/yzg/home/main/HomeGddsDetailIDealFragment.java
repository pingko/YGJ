package com.yzg.home.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.yzg.base.fragment.MvvmBaseFragment;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentGddsDetailIdealBinding;
import com.yzg.home.gdds.HomeGddsDetailIDealViewModel;

public class HomeGddsDetailIDealFragment extends MvvmLazyFragment<HomeFragmentGddsDetailIdealBinding, HomeGddsDetailIDealViewModel> {

    public static HomeGddsDetailIDealFragment newInstance() {
        return new HomeGddsDetailIDealFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_gdds_detail_ideal;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected HomeGddsDetailIDealViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeGddsDetailIDealViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewModel.getDetail("2");
    }
}
