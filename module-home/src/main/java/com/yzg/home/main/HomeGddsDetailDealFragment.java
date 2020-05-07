package com.yzg.home.main;

import androidx.lifecycle.ViewModelProviders;

import com.yzg.base.fragment.MvvmBaseFragment;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentGddsDetailDealBinding;
import com.yzg.home.gdds.HomeGddsDetailDealViewModel;

public class HomeGddsDetailDealFragment extends MvvmBaseFragment<HomeFragmentGddsDetailDealBinding, HomeGddsDetailDealViewModel> {

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
    protected void onRetryBtnClick() {

    }
}
