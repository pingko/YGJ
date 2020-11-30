package com.yzg.user.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.model.UserInfoBean;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.user.BindAliPayActivity;
import com.yzg.user.LoginActivity;
import com.yzg.user.R;
import com.yzg.user.address.UserAddressActivity;
import com.yzg.user.databinding.UserActivitySettingBinding;
import com.zhpan.idea.utils.LogUtils;

/**
 * @author darryrzhoong
 */
public class UserSettingActivity extends MvvmBaseActivity<UserActivitySettingBinding, UserSettingModel> {

    public static String token;
    UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected UserSettingModel getViewModel() {
        return ViewModelProviders.of(this).get(UserSettingModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_setting;
    }

    @Override
    protected void onRetryBtnClick() {

    }


    private void initData() {
        token = MmkvHelper.getInstance().getMmkv().decodeString("token");
        binding.tvQuit.setVisibility(!TextUtils.isEmpty(token) ? View.VISIBLE : View.GONE);
        binding.ivBack.setOnClickListener(view -> finish());
        viewModel.getUser();
    }

    private void initView() {
        LiveEventBus
                .get("editUser", Integer.class)
                .observe(this, s -> {
                    viewModel.getUser();
                });
        binding.tvQuit.setOnClickListener(view -> {
            RxToast.normal("退出成功");
            MmkvHelper.getInstance().getMmkv().clearAll();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        binding.rlPay.setOnClickListener(view -> {
            Intent intent = new Intent(UserSettingActivity.this, BindAliPayActivity.class);
            String payNo = "";
            String loginName = "";
            if (userInfoBean != null) {
                payNo = userInfoBean.getUser().getPayNo() + "";
                loginName = userInfoBean.getUser().getLoginName() + "";
            }
            ARouter.getInstance().build(RouterActivityPath.User.PAGER_BINDALIPAY)
                    .withString("loginName", loginName)
                    .withString("payNo", payNo)
                    .navigation();

        });
        binding.rlAddress.setOnClickListener(view -> {
            startActivity(new Intent(UserSettingActivity.this, UserAddressActivity.class));
        });

        viewModel.userInfoLiveData.observe(this, userInfo -> {
            if (userInfo != null) {
                userInfoBean = userInfo;
                binding.tvPhone.setText(userInfo.getUser().getPhonenumber().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
                Object payNo = userInfo.getUser().getPayNo();
                if (payNo != null) {
                    String[] s = payNo.toString().split("=");
                    String payPhone = s[0].length() == 11 ? s[0] : s[1];
                    binding.tvPay.setText(payPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
                }

            } else {
                Logger.e("获取用户信息失败");
            }
        });
    }


}
