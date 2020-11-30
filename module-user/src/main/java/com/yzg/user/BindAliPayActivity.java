package com.yzg.user;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.user.databinding.UserActivityBindBinding;

@Route(path = RouterActivityPath.User.PAGER_BINDALIPAY)
public class BindAliPayActivity extends MvvmBaseActivity<UserActivityBindBinding, BindAlipayViewModel> {

    //    String payNo = "";
//    String loginName = "";
    @Autowired(name = "loginName")
    public String loginName;//
    @Autowired(name = "payNo")
    public String payNo;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initView();
    }

    @Override
    protected BindAlipayViewModel getViewModel() {
        return ViewModelProviders.of(this).get(BindAlipayViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_bind;
    }

    @Override
    protected void onRetryBtnClick() {

    }


    private void initView() {
        binding.ivBack.setOnClickListener(view -> finish());

        if (!TextUtils.isEmpty(payNo)) {
            String[] s = payNo.split("=");
            binding.etAccount.setText(s[0]);
            binding.etName.setText(s[1]);
        }
        binding.tvSure.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etName.getText().toString())) {
                RxToast.normal("请输入姓名");
                return;
            }
            if (TextUtils.isEmpty(binding.etAccount.getText().toString())) {
                RxToast.normal("请输入账号");
                return;
            }
            viewModel.editUser(binding.etAccount.getText().toString(), binding.etName.getText().toString(), loginName);
        });


        viewModel.successData.observe(this, tokenBean -> {
            RxToast.showToast("绑定成功");
            LiveEventBus.get("editUser").post(0);
            finish();
        });

        viewModel.errorLiveData.observe(this, s -> RxToast.normal(s));

    }


    @Override
    public void showFailure(String message) {
        super.showFailure(message);
        RxToast.normal(message);
    }
}
