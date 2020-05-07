package com.yzg.home.main;

import androidx.lifecycle.ViewModelProviders;

import com.yzg.base.fragment.MvvmBaseFragment;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentGddsDetailIdealBinding;
import com.yzg.home.gdds.HomeGddsDetailIDealViewModel;

public class HomeGddsDetailIDealFragment extends MvvmBaseFragment<HomeFragmentGddsDetailIdealBinding, HomeGddsDetailIDealViewModel> {

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
}
