package com.yzg.deal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tamsiree.rxkit.view.RxToast;
import com.tamsiree.rxui.view.dialog.RxDialogSureCancel;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.base.http.HttpLog;
import com.yzg.base.model.UserInfoBean;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.deal.databinding.DealFragmentMainBinding;
import com.yzg.deal.deal.DealMainActivity;

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
    int currCanUse;

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
        binding.tvSale.setOnClickListener(view -> {
            if (currCanUse == 0) {
                RxToast.showToast("当前没有活期份额，请先买入");
            } else {
                viewModel.getUser();
            }
        });
        binding.tvTake.setOnClickListener(this);
        token = MmkvHelper.getInstance().getMmkv().decodeString("token");

        viewModel.successData.observe(this, userStoreBean -> {
            if (userStoreBean != null) {
                currCanUse = userStoreBean.getCurrCanUse();
                binding.tvCcMonney.setText(userStoreBean.getCurrAmt() + "");
                binding.tvFe.setText(currCanUse + "");
//                acctNo = userStoreBean.getAcctNo();
            } else {
                binding.tvCcMonney.setText("0");
                binding.tvFe.setText("0");
            }

        });
        LiveEventBus
                .get("takeSuccess", Integer.class)
                .observe(this, s -> {
                    HttpLog.e("检测到买出成功");
                    viewModel.loadData();
                });
        LiveEventBus
                .get("saleSuccess", Integer.class)
                .observe(this, s -> {
                    HttpLog.e("检测到卖出成功");
                    viewModel.loadData();
                });
        LiveEventBus
                .get("buySuccess", Integer.class)
                .observe(this, s -> {
                    HttpLog.e("接收到购买成功，刷新持仓");
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
        viewModel.errorLiveData.observe(this, s -> RxToast.showToast(s));
        viewModel.userInfoLiveData.observe(this, userInfoBean -> {
            if (userInfoBean != null && userInfoBean.getUser() != null && userInfoBean.getUser().getPayNo() != null) {
                Intent intent = new Intent(getContext(), DealMainActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("sirverPrice", viewModel.lastPrice.getValue());
                acctNo = MmkvHelper.getInstance().getMmkv().decodeString("accno");
                intent.putExtra("acctNo", acctNo);
                intent.putExtra("currCanUse", currCanUse);
                startActivity(intent);
            } else {
                initDialogSurePermission(userInfoBean, getContext(), "卖出需要绑定支付宝账号，是否绑定?");
            }
        });
    }

    public static void initDialogSurePermission(UserInfoBean userInfoBean, final Context mContext, String str) {
        final RxDialogSureCancel rxDialogSure = new RxDialogSureCancel(mContext);
        rxDialogSure.getLogoView().setVisibility(View.GONE);
        rxDialogSure.getTitleView().setVisibility(View.GONE);
        rxDialogSure.setContent(str);
        rxDialogSure.getContentView().setTextSize(18);
        rxDialogSure.getContentView().setTextColor(ContextCompat.getColor(mContext, R.color.common_color_main));
        rxDialogSure.getContentView().setGravity(Gravity.CENTER);
        rxDialogSure.setCanceledOnTouchOutside(true);
        rxDialogSure.setSureListener(v -> {
            rxDialogSure.cancel();
            String loginName = "";
            if (userInfoBean != null) {
                loginName = userInfoBean.getUser().getLoginName() + "";
            }
            ARouter.getInstance().build(RouterActivityPath.User.PAGER_BINDALIPAY)
                    .withString("loginName", loginName)
                    .navigation();

        });
        rxDialogSure.setCancelListener(view -> {
            rxDialogSure.cancel();
        });
        rxDialogSure.show();
    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        Log.e("DealFragment", "first");

    }


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
//                viewModel.getUser();
//                return;
//                intent.putExtra("type", 1);
//                intent.putExtra("sirverPrice", viewModel.lastPrice.getValue());
            } else if (view.getId() == R.id.tv_take) {
                intent.putExtra("type", 2);
                intent.putExtra("sirverPrice", viewModel.lastPrice.getValue());
            }
            acctNo = MmkvHelper.getInstance().getMmkv().decodeString("accno");
            intent.putExtra("acctNo", acctNo);
            startActivity(intent);
        }
    }
}
