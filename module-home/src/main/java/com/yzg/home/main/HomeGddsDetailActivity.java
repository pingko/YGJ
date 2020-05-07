package com.yzg.home.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeActivityGddslistBinding;
import com.yzg.home.databinding.HomeActivityGddslistDetailBinding;
import com.yzg.home.discover.adapter.HomeGddsDetailFragmentPageAdapter;

import java.util.ArrayList;

/**
 * 应用模块:
 * <p>
 * 类描述: 关注页面
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-29
 */
@Route(path = RouterActivityPath.Home.PAGER_HOME_GDDS_DETAIL)
public class HomeGddsDetailActivity extends AppCompatActivity {

    HomeActivityGddslistDetailBinding binding;
    HomeGddsDetailFragmentPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity_gddslist_detail);
        initView();
        initData();
    }


    private void initView() {
        binding.ivBack.setOnClickListener(view -> {
            finish();
        });
        binding.tabLayout.setupWithViewPager(binding.vpHomeContent);
        binding.vpHomeContent.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(
                        binding.tabLayout));
        binding.tabLayout
                .addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        binding.vpHomeContent
                                .setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
        adapter = new HomeGddsDetailFragmentPageAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        binding.vpHomeContent.setAdapter(adapter);
    }

    private void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeGddsDetailDealFragment.newInstance());
        fragments.add(HomeGddsDetailIDealFragment.newInstance());
        adapter.setData(fragments);
        binding.vpHomeContent.setCurrentItem(0);
    }
}
