package com.yzg.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.base.http.HttpLog;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.utils.GsonUtils;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.user.bean.UserStoreBean;
import com.yzg.user.databinding.UserFragmentLayoutBinding;
import com.yzg.user.setting.UserSettingActivity;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-28
 */
@Route(path = RouterFragmentPath.User.PAGER_USER)
public class UserFragment
        extends MvvmLazyFragment<UserFragmentLayoutBinding, UserViewModel> implements IUserMainView, View.OnClickListener {

    String token;
    private UserStoreBean userStoreBean = new UserStoreBean();
    @Override
    public int getLayoutId() {
        return R.layout.user_fragment_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        token = MmkvHelper.getInstance().getMmkv().decodeString("token");
        Log.e("UserFragment", token + "");
        initView();
        viewModel.isLoginLivedata.setValue(TextUtils.isEmpty(token) ? false : true);
        viewModel.isLoginLivedata.observe(this, aBoolean -> {
            if (aBoolean) {
                viewModel.loadData();
            }
            binding.rlNo.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
            binding.rlLogin.setVisibility(!aBoolean ? View.GONE : View.VISIBLE);
            binding.rlSubscrible.setVisibility(!aBoolean ? View.GONE : View.VISIBLE);
            binding.viewSubscrible.setVisibility(!aBoolean ? View.GONE : View.VISIBLE);

//            if (!GsonUtils.isShowTrue()){
////                binding.rlNo.setVisibility(View.VISIBLE);
////                binding.rlLogin.setVisibility(View.GONE);
//                binding.llOption.setVisibility(View.INVISIBLE);
//                binding.tvCs.setVisibility(View.INVISIBLE);
//            }
        });

    }


    private void start(Context context) {
        startActivityForResult(new Intent(context, LoginActivity.class), 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("UserFragment", requestCode + "," + resultCode);
        switch (requestCode) {
            case 1001:
                viewModel.isLoginLivedata.setValue(true);
//                break;
            case 1002:
//                viewModel.isLoginLivedata.setValue(false);
                break;
        }
    }


    private void initView() {
        Glide.with(getContext()).load(getContext().getDrawable(R.drawable.avatar))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(binding.ivAvatar);
        binding.rvTables.setHasFixedSize(true);
        binding.rvTables
                .setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rlLogin.setOnClickListener(v -> {
//            start(getContext());
        });
        binding.rlNo.setOnClickListener(v -> {
            start(getContext());
        });
        binding.ivShow.setOnClickListener(v -> {
            if (binding.ivShow.getTag().equals("cancle")) {
                binding.ivShow.setTag("sure");
                binding.ivShow.setImageResource(R.drawable.user_money_show);
                binding.tvMoney.setText(userStoreBean.getCurrCanUse()+"");
            } else {
                binding.ivShow.setTag("cancle");
                binding.ivShow.setImageResource(R.drawable.user_money_hide);
                binding.tvMoney.setText("******");
            }
        });

        binding.rlHelp.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });
        binding.rlSetting.setOnClickListener(view -> {

            startActivityForResult(new Intent(getContext(), UserSettingActivity.class), 1002);
        });
        binding.rlSubscrible.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });
        binding.rlFeedback.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });
        binding.rlAbout.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });


        binding.tvBuy.setOnClickListener(this);
        binding.tvSale.setOnClickListener(this);
        binding.tvProduct.setOnClickListener(this);

        binding.rlDeal.setOnClickListener(this::onClick);


    }


    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(com.yzg.common.R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    @Override
    public int getBindingVariable() {
        return BR.userVm;
    }

    @Override
    protected UserViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_buy || view.getId() == R.id.tv_sale || view.getId() == R.id.tv_product) {
            LiveEventBus
                    .get("index")
                    .post(2);
        }else if (view.getId()==R.id.rl_deal){
            startActivity(new Intent(getActivity(),UserDealAccountActivity.class));
        }
    }

    @Override
    public void onDataLoadFinish(UserStoreBean bean) {
        userStoreBean = bean;
        binding.tvName.setText(bean.getAcctNo());
        binding.tvMoney.setText(bean.getCurrCanUse()+"");
    }


}
