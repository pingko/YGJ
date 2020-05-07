package com.yzg.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.tamsiree.rxkit.view.RxToast;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yzg.base.fragment.MvvmBaseFragment;
import com.yzg.base.viewmodel.IMvvmBaseViewModel;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.user.adapter.RecyclerAdapter;
import com.yzg.user.databinding.UserFragmentLayoutBinding;
import com.yzg.user.setting.UserSettingActivity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

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
        extends MvvmBaseFragment<UserFragmentLayoutBinding, IMvvmBaseViewModel> {


    @Override
    public int getLayoutId() {
        return R.layout.user_fragment_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLogin(false);
        initView();
    }

    private void start(Context context) {
        startActivityForResult(new Intent(context, LoginActivity.class), 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            setLogin(true);
        }
    }


    private void setLogin(boolean isLogin) {
        viewDataBinding.rlLogin.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        viewDataBinding.rlNo.setVisibility(!isLogin ? View.VISIBLE : View.GONE);
        viewDataBinding.rlSubscrible.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        viewDataBinding.viewSubscrible.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }

    private void initView() {
        Glide.with(getContext()).load(getContext().getDrawable(R.drawable.avatar))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(viewDataBinding.ivAvatar);
        viewDataBinding.rvTables.setHasFixedSize(true);
        viewDataBinding.rvTables
                .setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.rlLogin.setOnClickListener(v -> {
//            start(getContext());
        });
        viewDataBinding.rlNo.setOnClickListener(v -> {
            start(getContext());
        });
        viewDataBinding.ivShow.setOnClickListener(v -> {
            if (viewDataBinding.ivShow.getTag().equals("cancle")) {
                viewDataBinding.ivShow.setTag("sure");
                viewDataBinding.ivShow.setImageResource(R.drawable.user_money_show);
                viewDataBinding.tvMoney.setText("100000.00");
            } else {
                viewDataBinding.ivShow.setTag("cancle");
                viewDataBinding.ivShow.setImageResource(R.drawable.user_money_hide);
                viewDataBinding.tvMoney.setText("******");
            }
        });

        viewDataBinding.rlHelp.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });
        viewDataBinding.rlSetting.setOnClickListener(view -> {

            getActivity().startActivity(new Intent(getContext(),UserSettingActivity.class));
        });
        viewDataBinding.rlSubscrible.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });
        viewDataBinding.rlFeedback.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });
        viewDataBinding.rlAbout.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });

    }

    private View getFooterView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.user_item_footer_view, viewDataBinding.rvTables, false);
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected IMvvmBaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected void onRetryBtnClick() {

    }
}
