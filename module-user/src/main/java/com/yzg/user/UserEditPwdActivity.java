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
import com.yzg.common.utils.StringUtils;
import com.yzg.user.databinding.UserActivityBindBinding;
import com.yzg.user.databinding.UserActivityEditpwdBinding;

@Route(path = RouterActivityPath.User.PAGER_EDITPASSWORD)
public class UserEditPwdActivity extends MvvmBaseActivity<UserActivityEditpwdBinding, UserEditPwdViewModel> {

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
    protected UserEditPwdViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UserEditPwdViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_editpwd;
    }

    @Override
    protected void onRetryBtnClick() {

    }


    private void initView() {
        binding.ivBack.setOnClickListener(view -> finish());

        binding.tvSure.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etOld.getText().toString())) {
                RxToast.normal("请输入旧密码");
                return;
            }
            String pwd = binding.etPwd.getText().toString();
            String pwd1 = binding.etPwd1.getText().toString();
            if (TextUtils.isEmpty(pwd)) {
                RxToast.normal("请输入新密码");
                return;
            }
            if (TextUtils.isEmpty(pwd1)) {
                RxToast.normal("请再次输入新密码");
                return;
            }

            if (!pwd.equals(pwd1)) {
                RxToast.normal("密码不一致，请重新输入");
                return;
            }
            if (!StringUtils.checkPwd(pwd)) {
                RxToast.normal("密码数字、字母、符号6-10位,必须包含其中至少两种");
                return;
            }
            viewModel.editUser(binding.etOld.getText().toString(), binding.etPwd.getText().toString());
        });


        viewModel.successData.observe(this, tokenBean -> {
            RxToast.showToast("修改成功");
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
