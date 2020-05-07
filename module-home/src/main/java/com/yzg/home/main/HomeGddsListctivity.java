package com.yzg.home.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeActivityGddslistBinding;

/**
 * 应用模块:
 * <p>
 * 类描述: 跟单大师
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-29
 */
@Route(path = RouterActivityPath.Home.PAGER_HOME_GDDSLIST)
public class HomeGddsListctivity extends AppCompatActivity {

    HomeActivityGddslistBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity_gddslist);
        initView();
        initData();
    }

    private void initData() {
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container,new HomeGDDSListFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }

    private void initView() {
        binding.tvTitle.setText("跟单大师");
        binding.ivBack.setOnClickListener(view -> {
            finish();
        });
    }
}
