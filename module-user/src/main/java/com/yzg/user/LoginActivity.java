package com.yzg.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
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

    //已阅读《用户协议》和《隐私政策》

    private void initData() {
        binding.ivBack.setOnClickListener(view -> finish());
//        setLoadSir(binding.tvLogin);
        showLoading();
        showTip();
    }

    private void initView() {
//        binding.etPhone.setText("1861234567");
//        binding.etPwd.setText("123456");
        binding.tvLogin.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etPhone.getText().toString())) {
                RxToast.normal("请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(binding.etPwd.getText().toString())) {
                RxToast.normal("请输入验证码");
                return;
            }
            if (binding.ivChoose.getTag().equals("0")){
                RxToast.normal("请勾选协议");
                return;
            }

            viewModel.login(binding.etPhone.getText().toString(), binding.etPwd.getText().toString(),binding.etRecommenderName.getText().toString(),"1");

        });

        binding.tvRegister.setOnClickListener(view -> {
            ARouter.getInstance()
                    .build(RouterActivityPath.User.PAGER_REGISTER)
                    .navigation();
        });


        viewModel.successData.observe(this, tokenBean -> {
            MmkvHelper.getInstance().getMmkv().encode("token", tokenBean.getToken());
            MmkvHelper.getInstance().getMmkv().encode("acctNo",  binding.etPhone.getText().toString());
            RxToast.normal("登录成功");
//            if (splashLogin == 1) {
//                Log.e("ss", "sss");
//                ARouter.getInstance()
//                        .build(RouterActivityPath.Main.PAGER_MAIN)
//                        .navigation();
//            } else {
//                setResult(RESULT_OK);
//            }
//            finish();

            ARouter.getInstance()
                    .build(RouterActivityPath.Main.PAGER_MAIN)
                    .navigation();
        });

        viewModel.errorLiveData.observe(this, s -> RxToast.normal(s));

    }

    private void showTip() {
        String string = getResources().getString(R.string.privacy_tips);
        String key1 = getResources().getString(R.string.privacy_tips_key1);
        String key2 = getResources().getString(R.string.privacy_tips_key2);
        int index1 = string.indexOf(key1);
        int index2 = string.indexOf(key2);

        //需要显示的字串
        SpannableString spannedString = new SpannableString(string);
        //设置点击字体颜色
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(getResources().getColor(R.color.user_color_main));
        spannedString.setSpan(colorSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.user_color_main));
        spannedString.setSpan(colorSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击字体大小
        AbsoluteSizeSpan sizeSpan1 = new AbsoluteSizeSpan(12, true);
        spannedString.setSpan(sizeSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan sizeSpan2 = new AbsoluteSizeSpan(12, true);
        spannedString.setSpan(sizeSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, AttentionActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //点击事件去掉下划线
                ds.setUnderlineText(false);
            }
        };
        spannedString.setSpan(clickableSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, AttentionActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //点击事件去掉下划线
                ds.setUnderlineText(false);
            }
        };
        spannedString.setSpan(clickableSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //设置点击后的颜色为透明，否则会一直出现高亮
        binding.tvPri.setHighlightColor(Color.TRANSPARENT);
        //开始响应点击事件
        binding.tvPri.setMovementMethod(LinkMovementMethod.getInstance());

        binding.tvPri.setText(spannedString);

        binding.ivChoose.setTag("0");
        binding.ivChoose.setOnClickListener(view -> {
            binding.ivChoose.setImageResource("1".equals(binding.ivChoose.getTag()) ? R.drawable.user_ic_match_normal : R.drawable.user_ic_match_success);
            binding.ivChoose.setTag("0".equals(binding.ivChoose.getTag()) ? "1" : "0");
        });
    }


    @Override
    public void onDataLoadFinish(BaseCustomViewModel viewModel) {

    }

    @Override
    public void showFailure(String message) {
        super.showFailure(message);
        RxToast.normal(message);
    }
}
