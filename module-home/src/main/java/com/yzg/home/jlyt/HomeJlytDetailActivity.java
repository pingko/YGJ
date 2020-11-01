package com.yzg.home.jlyt;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alipay.sdk.app.PayTask;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.common.alipay.AuthResult;
import com.yzg.common.alipay.OrderInfoUtil2_0;
import com.yzg.common.alipay.PayResult;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeActivityJlytDetailBinding;

import java.util.Map;


public class HomeJlytDetailActivity extends MvvmBaseActivity<HomeActivityJlytDetailBinding, HomeJlytDetailViewModel> {


    private JlytBean bean;
    private String productId;

    @Override
    protected HomeJlytDetailViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeJlytDetailViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = (JlytBean) getIntent().getSerializableExtra("JlytBean");
        productId = getIntent().getStringExtra("productId");

        initData();
    }

    private void initData() {
        binding.ivBack.setOnClickListener(view -> finish());
        if (bean != null)
            binding.tvTitle.setText(bean.getProductName());

        binding.progressBar.setProgress(33);

        binding.tvBuy.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etNum.getText().toString())) {
                RxToast.normal("请输入数量");
                return;
            }
            viewModel.buyjLYT(productId, binding.etNum.getText().toString());
        });

        viewModel.loadData(productId);
        viewModel.successData.observe(this, bean -> {
            if (bean != null) {
                binding.tvDate.setText(bean.getLength() + "个月");
                binding.tvTitle.setText(bean.getProductName());
                binding.tvRate.setText(bean.getRate() + "%");
//                binding.buyRule1.setText("1克起购，10克递增，用户持有上限20000克");
                binding.tvStart.setText(bean.getPoint() + "克起购");
                binding.buyRule1.setText(bean.getPoint() + "克起购," + bean.getLength() + "克递增，用户持有上限" + bean.getProductLimit() + "克");
            }
        });

        viewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                RxToast.showToast(s);
            }
        });
        viewModel.buyResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                RxToast.showToast(s);
                if (s.equals("购买成功"))
                    finish();
            }
        });


    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity_jlyt_detail;
    }

    @Override
    protected void onRetryBtnClick() {

    }
//
//    @Override
//    public void onDataLoadFinish(JlytDetailBean viewModel) {
//        HttpLog.e(viewModel.getProductName());
//    }


    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2021001159660791";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    public static final String RSA_PRIVATE = "";

    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCUEE2U67/f26fkQSBQTH9/o9weey/XwFlCTbkTZRjg4+V9Kwrcur+HtK+UGRZAFjTBHF7AfFtLdaH1TWdUCgh/0/+/h2A+z7ZWv7799ONGTgVCey/WIA2mndrdnJIawAMs+iakwrB9FZN9AHceLmPDsXVn2IsohAVXPQQWDTfGzzHmafMlXdNATjDCYRyvEY8B632YVmGHHbnuAxEg5mFSDalKjn9MpuXNewAOPNhBT2xLIrwYBh7f+qG//BppNQmEwFTPVGOu1s+6qYfZzTIN5mLq2RBVt1G0m84+NxzvZDb7hryyPO/u68FQ9emUDGfcj5hAGJSpRzWva8S6ITUrAgMBAAECggEAMPyv3mydYAA5rRBLE6YrrNxW8JLLQnO2VHSInj0dnRJplB8QifUTd1+1k6c1MGDodVfglYjPX8j8m79PR4PGShynCgRNOJradhscosNKCrG3lcZSDmMugQjLJ/UsdcM/ibr93Bc58ziXQo1L2+V3RoDJGmGPsQj1B2b9mhoncRQMptYCscilrkZY8OoZu5et2ip3UuKBJWUCFlaBLbZ2fEHnhXSbjt1HP6tPSHjayMzApcLXB2GtZe2EVHmxuDd805TOWyliAK822QG7DAAi1JeezkAAiC7WgpRS3ZbD+pSxe4Q7W+BYe8/GJkiRQZ5+a8wdYtpTgi+yU7M58BBVeQKBgQDX5M2wRLFhkyjyTIdGUqBLvrtq8tFHHzOnqImWZPpGxerL3PqvfnyNUopCLrWSwlEikrg5i5BMFNQOUCm+1Z+7Rt1tw9+WbkHfdOWJ99MBs+PHVK8Bu1XbIh7uxny3zPQqHyAY8ztagv1tXqtbqcr99S+vuVx3bXYNXCD6kE2wpwKBgQCvkbltxeR43g815cah4okeT1tEWQQU3q+NgN2v0D7XWSOupYDQoE+bBa8A9c48ulmYEu48xQ1cu76Ft78cYJ+99zeRS21HctblJfC2zI7kvv9eOtTjPFNty4nm6yoZ71fVmqdsXFG70H7dBODDCsMATZzKzEdF4JkC+g1dZgxD3QKBgQCBsZfHDl0o6sisVkDlg/l7x0a7hscl3J6hV2PgDyUyou7cLRIDnw6frzgTbg/x6/3lsRIur6Ktsc16E7ogRCDC1l1q9UI6El5MKONDsLb3zONG+Z5wgeOf0q2Wb3K4z8zWCQC1PFkplIs9yqNSW3vwd/x/qfDcZcuHVxMQsuOY/QKBgEym/eNMZAzVPqTUdh2VrzH5iapUhvCprNHF8oPhEp6ov2hvv8bWRwsytw5fQzTGU3mOwk7r5YKYQX1WFO1JzlR9C9i2qrZoECSppOfadYSvUwUMAIhZfT9Rfxq/j3kVQy7yP6iSweiTBeBvUcY1+581Q4BTIccDYSqoi488j5xNAoGBAL07y7Q81QySQiX6HdzpjudAMZsmIwfWQkRKRsYPfhzevi3OtSMhLEzpw/upiWDMfultkdWzHxJWtqVjEvzzsnf0HFkZp3r7rZNruZGJy1RpnUGdS2bV7+0oq9JsJhZCrfSYeCrev082KpnhTLoXXUzaOAmiGz9zFaorpeIIEXUd";
    ;


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(DealMainActivity.this, getString(R.string.pay_success) + payResult);
                        RxToast.showToast("支付成功");
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(DealMainActivity.this, getString(R.string.pay_failed) + payResult);
                        RxToast.showToast("支付失败");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        RxToast.normal(getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        RxToast.normal(getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            RxToast.normal(getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, "");
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(HomeJlytDetailActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };


        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
