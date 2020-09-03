package com.yzg.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.user.bean.TokenBean;
import com.yzg.user.databinding.UserActivityLoginBinding;

import java.util.TreeMap;

/**
 * @author darryrzhoong
 */
@Route(path = RouterActivityPath.User.PAGER_LOGIN)
public class LoginActivity extends MvvmBaseActivity<UserActivityLoginBinding, LoginViewModel> implements IUserLoginView {

    @Autowired(name = "splashLogin")
    public int splashLogin;//是否是启动页跳转过来的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected LoginViewModel getViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_login;
    }

    @Override
    protected void onRetryBtnClick() {

    }


    private void initData() {
        binding.ivBack.setOnClickListener(view -> finish());
//        setLoadSir(binding.tvLogin);
        showLoading();
        viewModel.initModel();
    }

    private void initView() {
        binding.etPhone.setText("1861234567");
        binding.etPwd.setText("123456");
        binding.tvLogin.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etPhone.getText().toString())) {
                RxToast.info("请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(binding.etPwd.getText().toString())) {
                RxToast.info("请输入验证码");
                return;
            }
            TreeMap map = new TreeMap();
            map.put("username", binding.etPhone.getText().toString());
            map.put("password", binding.etPwd.getText().toString());
            map.put("rememberMe", "1");
            viewModel.setRequestParams(map);
            viewModel.tryToRefresh();

        });

        binding.tvRegister.setOnClickListener(view -> {
            ARouter.getInstance()
                    .build(RouterActivityPath.User.PAGER_REGISTER)
                    .navigation();
        });


    }


    @Override
    public void onDataLoadFinish(BaseCustomViewModel viewModel) {
        TokenBean tokenBean = (TokenBean) viewModel;
        MmkvHelper.getInstance().getMmkv().encode("token", tokenBean.getToken());
        RxToast.normal("登录成功");
        if (splashLogin == 1) {
            Log.e("ss", "sss");
            ARouter.getInstance()
                    .build(RouterActivityPath.Main.PAGER_MAIN)
                    .navigation();
        } else {
            setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public void showFailure(String message) {
        super.showFailure(message);
        RxToast.normal(message);
    }
}
