package com.yzg.deal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.deal.databinding.DealFragmentMainBinding;
import com.yzg.deal.deal.DealMainActivity;
import com.zhpan.idea.utils.LogUtils;

/**
 * 应用模块:
 * <p>
 * 类描述:交易首页
 * <p>
 */
@Route(path = RouterFragmentPath.Deal.PAGER_DEAL)
public class DealFragment
        extends MvvmLazyFragment<DealFragmentMainBinding, DealViewModel> implements View.OnClickListener {

    String acctNo;
    String token;

    @Override
    public int getLayoutId() {
        return R.layout.deal_fragment_main;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        binding.tvBuy.setOnClickListener(this);
        binding.tvSale.setOnClickListener(this);
        binding.tvTake.setOnClickListener(this);
        token = MmkvHelper.getInstance().getMmkv().decodeString("token");

        viewModel.successData.observe(this, userStoreBean -> {
            if (userStoreBean != null) {
                binding.tvCcMonney.setText(userStoreBean.getCurrCanUse() + "");
                binding.tvFe.setText(userStoreBean.getTakeFrozAmt() + "");
                acctNo = userStoreBean.getAcctNo();
            }
            Log.e("DealFragment", acctNo + "");
        });
        LiveEventBus
                .get("takeSuccess", Integer.class)
                .observe(this, s -> {
                    viewModel.loadData();
                });
        LiveEventBus
                .get("buySuccess", Integer.class)
                .observe(this, s -> {
                    LogUtils.e("接收到购买成功，刷新持仓");
                    viewModel.loadData();
                });
        viewModel.lastPrice.observe(this
                , aDouble -> {
                    binding.tvPrice.setText(aDouble + "");
                });

        if (!TextUtils.isEmpty(token)) {
            viewModel.loadData();
            viewModel.loadTodayPrice();
        }
    }



    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        Log.e("DealFragment","first");

    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        Log.e("DealFragment",hidden+"");
//        if (!hidden){
//            viewModel.loadData();
//            viewModel.loadTodayPrice();
//        }
//    }


    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected DealViewModel getViewModel() {
        return ViewModelProviders.of(this).get(DealViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_buy || view.getId() == R.id.tv_take) {
            Intent intent = new Intent(getContext(), DealMainActivity.class);
            if (view.getId() == R.id.tv_buy) {
                intent.putExtra("type", 0);
                intent.putExtra("sirverPrice", viewModel.lastPrice.getValue());
            } else if (view.getId() == R.id.tv_sale) {
                RxToast.showToast("开发中，暂不支持");
                intent.putExtra("type", 1);
                return;
            } else if (view.getId() == R.id.tv_take) {
                intent.putExtra("type", 2);
                intent.putExtra("sirverPrice", viewModel.lastPrice.getValue());
            }
            intent.putExtra("acctNo", acctNo);
            startActivity(intent);
        }
    }
}
