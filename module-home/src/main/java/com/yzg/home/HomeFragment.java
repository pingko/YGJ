package com.yzg.home;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.base.viewmodel.IMvvmBaseViewModel;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.home.databinding.HomeFragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.yzg.home.adapter.HomeFragmentPageAdapter;
import com.yzg.home.daily.DailyFragment;
import com.yzg.home.discover.DisCoverFragment;
import com.yzg.home.main.HomeMainFragment;
import com.yzg.home.nominate.NominateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用模块: home
 * <p>
 * 类描述: 首页-fragment
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-27
 */
@Route(path = RouterFragmentPath.Home.PAGER_HOME)
public class HomeFragment extends MvvmLazyFragment<HomeFragmentHomeBinding, IMvvmBaseViewModel> {

    private HomeFragmentPageAdapter pageAdapter;

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeMainFragment.newInstance());
        fragments.add(DisCoverFragment.newInstance());
        fragments.add(NominateFragment.newInstance());
        fragments.add(DailyFragment.newInstance());
        pageAdapter.setData(fragments);
        binding.vpHomeContent.setCurrentItem(1);

    }


    private void initView() {
        pageAdapter = new HomeFragmentPageAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        binding.vpHomeContent.setAdapter(pageAdapter);
        binding.tabLayout
                .setupWithViewPager(binding.vpHomeContent);
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
        binding.ivNetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                VideoHeaderBean videoHeaderBean = new VideoHeaderBean("1","1","1",0,9,"1","1","1","1");
//                LiveDatabus.getInstance().withSticky("player",VideoHeaderBean.class).setValue(videoHeaderBean);
//                startActivity(new Intent(getContext(), VideoInfoAndPlayerActivity.class));
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_home;
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
