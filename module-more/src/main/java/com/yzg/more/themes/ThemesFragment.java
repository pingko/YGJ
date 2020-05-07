package com.yzg.more.themes;

import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.more.R;
import com.yzg.more.databinding.MoreFragmentThemesBinding;
import com.yzg.more.themes.adapter.ThemesFragmentPageAdapter;
import com.yzg.more.themes.bean.Tabs;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-22
 */
public class ThemesFragment extends MvvmLazyFragment<MoreFragmentThemesBinding, ThemeFragmentViewModel> implements IThemeView {

    private ThemesFragmentPageAdapter adapter;

    public static ThemesFragment newInstance() {
        return new ThemesFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.more_fragment_themes;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        binding.vpContent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.vpContent.setOffscreenPageLimit(1);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vpContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter = new ThemesFragmentPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        binding.vpContent.setAdapter(adapter);
        viewModel.initModel();

        initTabs();

    }

    private void initTabs() {
        ArrayList<Tabs> tabs = new ArrayList<>();
        tabs.add(new Tabs("自选"));
        tabs.add(new Tabs("上海黄金"));
        tabs.add(new Tabs("国际黄金"));
        tabs.add(new Tabs("外汇"));
        tabs.add(new Tabs("特色数据"));
        adapter.setData(tabs);
        binding.tabLayout.removeAllTabs();
        for (Tabs tabs1 : tabs) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabs1.getName()));
        }
        binding.tabLayout.scrollTo(0, 0);
    }


    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected ThemeFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ThemeFragmentViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataLoaded(ArrayList<Tabs> tabs) {
//        adapter.setData(tabs);
//        binding.tabLayout.removeAllTabs();
//        for (Tabs tabs1 : tabs){
//            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabs1.getName()));
//        }
//        binding.tabLayout.scrollTo(0,0);
    }
}
