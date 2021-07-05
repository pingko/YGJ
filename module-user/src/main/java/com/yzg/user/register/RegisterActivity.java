package com.yzg.user.register;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tamsiree.rxkit.RxKeyboardTool;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.http.HttpService;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.common.utils.StringUtils;
import com.yzg.user.IUserRegisterView;
import com.yzg.user.R;
import com.yzg.user.databinding.UserActivityRegisterBinding;

import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * @author darryrzhoong
 */
@Route(path = RouterActivityPath.User.PAGER_REGISTER)
public class RegisterActivity extends MvvmBaseActivity<UserActivityRegisterBinding, RegisterViewModel> {

    @Autowired(name = "type")
    int type;//1为忘记

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
        timer = new MyTimer(60 * 1000, 1000);
        showLoading();
        binding.ivBack.setOnClickListener(view -> finish());


    }

    private void initView() {
//        binding.etPhone.setText("1861234567");
//        binding.etPwd.setText("123456");
//        binding.etPwd2.setText("123456");
//        binding.etName.setText("大富翁");
        if (type == 1) {
            binding.tvTitle.setText("忘记密码");
            binding.clNickname.setVisibility(View.GONE);
            binding.tvTipPwd.setText("新密码");
            binding.clPwd2.setVisibility(View.GONE);
            binding.clRecommand.setVisibility(View.GONE);
            binding.tvRegis.setText("提交");
        }
        binding.tvRegis.setOnClickListener(view -> {
            RxKeyboardTool.hideSoftInput(this);
            if (type != 1 && TextUtils.isEmpty(binding.etName.getText().toString())) {
                RxToast.normal("请输入昵称");
                return;
            }
            if (TextUtils.isEmpty(binding.etPhone.getText().toString())) {
                RxToast.normal("请输入手机号");
                return;
            }
            String pwd = binding.etPwd.getText().toString();
            String pwd1 = binding.etPwd2.getText().toString();
            String code = binding.etCode.getText().toString();
            if (TextUtils.isEmpty(code)) {
                RxToast.normal("请输入验证码");
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                RxToast.normal("请输入密码");
                return;
            }
            if (type != 1 && TextUtils.isEmpty(pwd1)) {
                RxToast.normal("请再次输入密码");
                return;
            }
            if (type != 1 && !pwd.equals(pwd1)) {
                RxToast.normal("请输入相同密码");
                return;
            }
            if (!StringUtils.checkPwd(pwd)) {
                RxToast.normal("密码数字、字母、符号6-10位,必须包含其中至少两种");
                return;
            }
//            Log.e("register:", code + "");
//            //接口有错 暂时不用code
//            viewModel.sendCode(binding.etPhone.getText().toString(), "");
            viewModel.sendCode(type,binding.etPhone.getText().toString(), code);

//            regis();

        });

        viewModel.successData.observe(this, aBoolean -> {
            if (aBoolean) {
                RxToast.showToast("注册成功");
                finish();
            }
        });
        viewModel.editSuccessData.observe(this, aBoolean -> {
            if (aBoolean) {
                RxToast.showToast("修改成功");
                finish();
            }
        });

        viewModel.successCheckCodeData.observe(this, aBoolean -> {
            if (aBoolean) {
                regis();
            }
        });

        viewModel.successSendCodeData.observe(this, aBoolean -> {
            if (aBoolean) {
                timer.start();
                RxToast.showToast("发送成功");
            }
        });

        binding.tvSend.setOnClickListener(view -> {
            String phone = binding.etPhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                RxToast.normal("请输入手机号");
                return;
            }
            viewModel.sendCode(type,phone, "");
        });

        viewModel.errorLiveData.observe(this, s -> RxToast.showToast(s));

    }

    private void regis(){
        if (type != 1) {
            String recommenderName = binding.etRecommenderName.getText().toString();
            TreeMap map = new TreeMap();
            map.put("loginName", binding.etPhone.getText().toString());
            map.put("phonenumber", binding.etPhone.getText().toString());
            map.put("userName", binding.etName.getText().toString());
            map.put("password", binding.etPwd.getText().toString());
            if (!TextUtils.isEmpty(recommenderName)) {
                map.put("userId", recommenderName);
            }
            viewModel.loadData(map, HttpService.REGISTER);
        } else {
            TreeMap map = new TreeMap();
            map.put("phone", binding.etPhone.getText().toString());
            map.put("password", binding.etPwd.getText().toString());
            viewModel.loadData(map, HttpService.FORGOTPASSWORD);
        }
    }

    public class MyTimer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * 即使过程
         *
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            binding.tvSend.setClickable(false);
            binding.tvSend.setSelected(true);
            binding.tvSend.setText(millisUntilFinished / 1000 + "s");

        }

        @Override
        public void onFinish() {
            binding.tvSend.setClickable(true);
            binding.tvSend.setText("发送");
            binding.tvSend.setSelected(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private MyTimer timer;
}
