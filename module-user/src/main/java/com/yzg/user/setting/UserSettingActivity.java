package com.yzg.user.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.contract.TestApi;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.user.IUserLoginView;
import com.yzg.user.LoginActivity;
import com.yzg.user.R;
import com.yzg.user.address.UserAddressActivity;
import com.yzg.user.databinding.UserActivitySettingBinding;

/**
 * @author darryrzhoong
 */
public class UserSettingActivity extends MvvmBaseActivity<UserActivitySettingBinding, UserSettingViewModel> implements IUserLoginView {

    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected UserSettingViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UserSettingViewModel.class);
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
        showLoading();
        viewModel.initModel();
        binding.ivBack.setOnClickListener(view -> finish());
        binding.tvAddress.setOnClickListener(view -> startActivity(new Intent(UserSettingActivity.this, UserAddressActivity.class)));

    }

    private void initView() {
        binding.tvQuit.setOnClickListener(view -> {
            RxToast.normal("退出成功");
            MmkvHelper.getInstance().getMmkv().clearAll();
//            ARouter.getInstance().build(RouterActivityPath.User.PAGER_LOGIN).navigation();
//            ActivityCompat.finishAfterTransition(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            setResult(RESULT_OK);
//            finish();
        });

    }


    @Override
    public void onDataLoadFinish(BaseCustomViewModel viewModel) {
        RxToast.normal(((TestApi) viewModel).getMsg());
    }


    @Override
    public void showFailure(String message) {
        super.showFailure(message);
        RxToast.normal(message);
    }
}
