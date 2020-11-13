package com.yzg.user;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.common.recyclerview.RecyclerItemDecoration;
import com.yzg.common.utils.DensityUtils;
import com.yzg.user.bean.UserDealBean;
import com.yzg.user.databinding.UserActivityAccountBinding;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

public class UserDealAccountActivity extends MvvmBaseActivity<UserActivityAccountBinding, UserDealAccountViewModel> {

    private CommonAdapter dealAdapter;
    private ArrayList<UserDealBean> dealList;

    @Override
    protected UserDealAccountViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UserDealAccountViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        dealList = new ArrayList<>();

        dealAdapter = new CommonAdapter<UserDealBean>(this, R.layout.user_account_item, dealList) {
            @Override
            protected void convert(ViewHolder holder, UserDealBean bean, int position) {
                holder.setText(R.id.tv_id, TextUtils.isEmpty(bean.getOrderNo()) ? "-" : bean.getOrderNo());
                holder.setText(R.id.tv_status, bean.getDealStat().equals("1") ? "成交" : "未成交");
                holder.setText(R.id.tv_price, bean.getAipPrice() == null ? "-" : bean.getAipPrice() + "");
                holder.setText(R.id.tv_num, bean.getAipAmount() == null ? "-" : bean.getAipAmount() + "");
                holder.setText(R.id.tv_money, bean.getAipMoney() == null ? "-" : bean.getAipMoney() + "");
                holder.setText(R.id.tv_date, bean.getExchDate() == null ? "-" : bean.getExchDate() + "");
            }
        };
        binding.rvJlyt.setLayoutManager(new LinearLayoutManager(this));
        binding.rvJlyt.setAdapter(dealAdapter);
//        binding.refreshLayout.setOnRefreshListener(refreshLayout -> binding.refreshLayout.finishRefresh(500));
//        binding.refreshLayout
//                .setRefreshHeader(new ClassicsHeader(this));
//        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
////            viewModel.tryToRefresh();
//            binding.refreshLayout.finishRefresh(500);
//        });
        binding.ivBack.setOnClickListener(view -> finish());

        viewModel.loadData();

        viewModel.successData.observe(this, userDealBeans -> {
            dealList.clear();
            dealList.addAll(userDealBeans);
            dealAdapter.notifyDataSetChanged();
        });

    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_account;
    }

    @Override
    protected void onRetryBtnClick() {

    }
}
