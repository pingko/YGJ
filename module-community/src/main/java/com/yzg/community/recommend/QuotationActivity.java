package com.yzg.community.recommend;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.community.R;
import com.yzg.community.databinding.CommunityActivityQuotationBinding;


@Route(path = RouterActivityPath.Quotation.Quotation_main)
public class QuotationActivity extends MvvmBaseActivity<CommunityActivityQuotationBinding, QuotationViewModel> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        viewModel.loadData();
        viewModel.successData.observe(this, bean -> {
            if (bean != null) {
                binding.tvTitle.setText(bean.getVarietynm());
                binding.tvPrice.setText(bean.getOrderNo() + "");
                binding.tvChangeNumber.setText(bean.getChangePrice() + "   " + bean.getChangeMargin());
                binding.tvChangeNumber.setTextColor(bean.getChangePrice() > 0 ? getResources().getColor(R.color.community_red) : getResources().getColor(R.color.community_green));
                binding.tvPrice.setTextColor(bean.getChangePrice() > 0 ? getResources().getColor(R.color.community_red) : getResources().getColor(R.color.community_green));
                binding.tvJj.setText(bean.getYesyPrice() + "");
                binding.tvZd.setText(bean.getLowPrice() + "");
                binding.tvJk.setText(bean.getOpenPrice() + "");
                binding.tvZs.setText(bean.getLastPrice() + "");
                binding.tvZg.setText(bean.getHighPrice() + "");

            }
        });
    }

    @Override
    protected QuotationViewModel getViewModel() {
        return ViewModelProviders.of(this).get(QuotationViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.community_activity_quotation;
    }

    @Override
    protected void onRetryBtnClick() {

    }
}
