package com.yzg.user.register;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tamsiree.rxkit.RxKeyboardTool;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.user.IUserRegisterView;
import com.yzg.user.R;
import com.yzg.user.databinding.UserActivityRegisterBinding;

import java.util.TreeMap;

/**
 * @author darryrzhoong
 */
@Route(path = RouterActivityPath.User.PAGER_REGISTER)
public class RegisterActivity extends MvvmBaseActivity<UserActivityRegisterBinding, RegisterViewModel>  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected RegisterViewModel getViewModel() {
        return ViewModelProviders.of(this).get(RegisterViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_register;
    }

    @Override
    protected void onRetryBtnClick() {

    }


    private void initData() {
//        setLoadSir(binding.tvLogin);
        showLoading();
        binding.ivBack.setOnClickListener(view -> finish());


    }

    private void initView() {
//        binding.etPhone.setText("1861234567");
//        binding.etPwd.setText("123456");
//        binding.etPwd2.setText("123456");
//        binding.etName.setText("大富翁");
        binding.tvRegis.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etName.getText().toString())) {
                RxToast.normal("请输入昵称");
                return;
            }
            if (TextUtils.isEmpty(binding.etPhone.getText().toString())) {
                RxToast.normal("请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(binding.etPwd.getText().toString())) {
                RxToast.normal("请输入密码");
                return;
            }
            if (TextUtils.isEmpty(binding.etPwd2.getText().toString())) {
                RxToast.normal("请再次输入密码");
                return;
            }
            if (!binding.etPwd.getText().toString().equals(binding.etPwd2.getText().toString())){
                RxToast.normal("请输入相同密码");
                return;
            }

            RxKeyboardTool.hideSoftInput(this);
            String recommenderName= binding.etRecommenderName.getText().toString();
            TreeMap map = new TreeMap();
            map.put("loginName",binding.etPhone.getText().toString());
            map.put("phonenumber",binding.etPhone.getText().toString());
            map.put("userName",binding.etName.getText().toString());
            map.put("password",binding.etPwd.getText().toString());
//            map.put("avatar",binding.etPwd.getText().toString());
            if (!TextUtils.isEmpty(recommenderName)) {
                map.put("avatar", recommenderName);
            }
            viewModel.loadData(map);
//            setResult(RESULT_OK);
//            finish();
        });

        viewModel.successData.observe(this, aBoolean -> {
            if (aBoolean){
                RxToast.showToast("注册成功");
                finish();
            }
        });

        viewModel.errorLiveData.observe(this, s -> RxToast.showToast(s));

    }


}
