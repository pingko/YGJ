package com.yzg.home.gdds.gddslist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeActivityGddslistBinding;
import com.yzg.home.main.HomeGDDSListFragment;

/**
 * 应用模块:
 * <p>
 * 类描述: 关注页面
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-29
 */
@Route(path = RouterActivityPath.Home.PAGER_HOME_GDDSRANKLIST)
public class HomeGddsRankListctivity extends AppCompatActivity {

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
                .add(R.id.fragment_container, GddsRankListFragment.newInstance())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }

    private void initView() {
        binding.tvTitle.setText("大师排行榜列表");
        binding.ivBack.setOnClickListener(view -> {
            finish();
        });
    }
}
