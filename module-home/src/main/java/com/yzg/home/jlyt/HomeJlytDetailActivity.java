package com.yzg.home.jlyt;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeActivityJlytDetailBinding;

public class HomeJlytDetailActivity extends MvvmBaseActivity<HomeActivityJlytDetailBinding, HomeJlytDetailViewModel> implements IJLYTView {


    private JlytBean bean;

    @Override
    protected HomeJlytDetailViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeJlytDetailViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = (JlytBean) getIntent().getSerializableExtra("JlytBean");
        initData();
    }

    private void initData() {
        binding.ivBack.setOnClickListener(view -> finish());
        if (bean != null)
            binding.tvTitle.setText(bean.getName());

    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity_jlyt_detail;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataLoadFinish(BaseCustomViewModel viewModel) {

    }
}
