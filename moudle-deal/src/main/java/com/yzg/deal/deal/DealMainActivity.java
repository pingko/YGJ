package com.yzg.deal.deal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.http.HttpLog;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.alipay.AuthResult;
import com.yzg.common.alipay.OrderInfoUtil2_0;
import com.yzg.common.alipay.PayResult;
import com.yzg.common.contract.AddressBean;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.deal.R;
import com.yzg.deal.databinding.DealActivityMainBinding;
import com.zhpan.idea.utils.LogUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route(path = RouterActivityPath.Deal.PAGER_DEAL_BUY)
public class DealMainActivity extends MvvmBaseActivity<DealActivityMainBinding, DealMainViewModel> implements View.OnClickListener {


    private int type;//0 买入 1卖出 2提货
    private String acctNo = "";
    private float sirverPrice = 0f;
    NumberFormat ddf;

    @Override
    protected DealMainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(DealMainViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.deal_activity_main;
    }

    @Override
    protected void onRetryBtnClick() {

    }


    public void getLastPrice() {
        viewModel.loadUserData();
        viewModel.loadTodayPrice();
        viewModel.userDuccessData.observe(this, userStoreBean -> {
            if (userStoreBean != null) {
                acctNo = userStoreBean.getAcctNo();
            }
            Log.e("DealMainA", "acctNo" + acctNo);
        });
        viewModel.lastPrice.observe(this
                , aDouble -> {
                    Log.e("DealMainA", "price:" + aDouble);
                    sirverPrice = aDouble;
                    binding.tvPrice.setText(aDouble + " 克/元");
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ddf = NumberFormat.getNumberInstance();
        ddf.setMaximumFractionDigits(2);
        ARouter.getInstance().inject(this);
//        sirverPrice = getIntent().getFloatExtra("sirverPrice",0f);
        binding.ivBack.setOnClickListener(this);
        binding.tvTest.setOnClickListener(this);
        binding.rlAddress.setOnClickListener(this);
        binding.tvPrice.setText(sirverPrice + " 克/元");
        type = getIntent().getIntExtra("type", 0);
//        acctNo = getIntent().getStringExtra("acctNo");
        if (type == 0) {
            binding.tvMoneyTip.setText("购买金额:");
            binding.tvFeTip.setText("购买份额(克):");
        } else if (type == 1) {
            binding.tvMoneyTip.setText("卖出金额:");
            binding.tvFeTip.setText("卖出份额(克):");
        } else if (type == 2) {
            binding.tvMoneyTip.setText("提货手续费(kg/500元):");
            binding.tvFeTip.setText("提货重量(kg):");
            binding.etWeight.setHint("请输入提货重量");
        }
        binding.rlAddress.setVisibility(type == 2 ? View.VISIBLE : View.GONE);


        binding.etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    price = 0;
                    binding.tvMoney.setText(ddf.format(price) + "元");
                    return;
                } else {
                    int a = Integer.parseInt(s.toString());
                    if (type == 2) {
                        price = (float) (a * 500);
                    } else {

                        price = (float) (a * sirverPrice);
                    }
                    binding.tvMoney.setText(ddf.format(price) + "元");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0 && type != 2) {
                    int a = Integer.parseInt(s.toString());
                    if (a < 20) {
                        RxToast.showToast("20克起，1克递增");
                        return;
                    }
                }

            }
        });

        viewModel.takeResponse.observe(this, s -> {
            RxToast.showToast(s);
            if (s.equals("提货成功")) {
                LiveEventBus
                        .get("takeSuccess")
                        .post(0);
                finish();
            }
        });
        viewModel.buyResponse.observe(this, s -> {
            if (!s.contains("error")) {
                orderId = s;
                payV2(s);
            }
        });

        viewModel.buySuccessResponse.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    LogUtils.e("购买成功");
                    LiveEventBus
                            .get("buySuccess")
                            .post(0);
                    finish();
                }
            }
        });
        getLastPrice();

        initAddress(0);
        LiveEventBus
                .get("addressIndex", Integer.class)
                .observe(this, s -> {
                    initAddress(s);
                });
    }

    private List<AddressBean> addressList;
    AddressBean addressBean;

    private void initAddress(int index) {
        addressList = new ArrayList<>();
        String addressArray = MmkvHelper.getInstance().getMmkv().decodeString("address");
        Log.e("aa", addressArray);
        if (!TextUtils.isEmpty(addressArray) && addressArray.length() > 2) {
            addressList.clear();
            List<AddressBean> addressList1 = JSONArray.parseArray(addressArray, AddressBean.class);
            addressList.addAll(addressList1);
            addressBean = addressList.get(index);
            binding.tvTel.setText(addressBean.getPhone());
            binding.tvName.setText(addressBean.getName());
            binding.tvAddress.setText(addressBean.getArea() + addressBean.getAddress());
        } else {
            addressBean = null;
        }
        if (addressBean == null) {
            binding.ivLocal.setVisibility(View.GONE);
            binding.tvTel.setVisibility(View.GONE);
            binding.tvAddress.setVisibility(View.GONE);
            binding.ivChoose.setVisibility(View.GONE);
            binding.tvName.setVisibility(View.GONE);
            binding.tvAdd.setVisibility(View.VISIBLE);
        } else {
            binding.tvAdd.setVisibility(View.GONE);
            binding.ivLocal.setVisibility(View.VISIBLE);
            binding.tvTel.setVisibility(View.VISIBLE);
            binding.tvAddress.setVisibility(View.VISIBLE);
            binding.tvName.setVisibility(View.VISIBLE);
            binding.ivChoose.setVisibility(View.VISIBLE);
        }
    }

    float price;
    String orderId;

    @Override
    public void showFailure(String message) {
        super.showFailure(message);
        RxToast.normal(message);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_test) {
//            payV2(v);
            String s = binding.etWeight.getText().toString();
            if (type == 2) {
                if (TextUtils.isEmpty(s)) {
                    RxToast.showToast("请输入提货重量");
                    return;
                }
                if (addressBean==null){
                    RxToast.showToast("请添加提货地址");
                    return;
                }
                int price = Integer.parseInt(s) * 500;
                viewModel.take(price + "");
            } else if (type == 0 || type == 1) {
                if (TextUtils.isEmpty(s) || Integer.parseInt(s) < 20) {
                    RxToast.showToast("20克起，1克递增");
                    return;
                }
                totalPrice = Integer.parseInt(s) * sirverPrice;
                if (type == 0) {
                    viewModel.buySirver(sirverPrice, s, ddf.format(totalPrice), acctNo);
                }
            }

        } else if (v.getId() == R.id.rl_address) {
            ARouter.getInstance()
                    .build(RouterActivityPath.User.PAGER_Address)
                    .withInt("type", 1)
                    .navigation();
        }

    }

    double totalPrice;

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
                    HttpLog.e(resultInfo);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        AliPayResultBean resultBean = JSONObject.parseObject(resultInfo, AliPayResultBean.class);
                        String trade_no = resultBean.getAlipay_trade_app_pay_response().getTrade_no();
                        viewModel.paySuccess(trade_no, acctNo, orderId);
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(DealMainActivity.this, getString(R.string.pay_success) + payResult);
                        RxToast.showToast("支付成功");
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
    public void payV2(String s) {
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
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, orderId, ddf.format(totalPrice), "订单金额");
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(DealMainActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            Log.e("msp", result.toString());

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };


        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
