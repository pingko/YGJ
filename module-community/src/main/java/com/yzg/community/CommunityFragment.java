package com.yzg.community;

import java.util.ArrayList;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.community.adapter.QuotationFragmentPageAdapter;
import com.yzg.community.bean.Tabs;
import com.yzg.community.databinding.CommunityFragmentCommunityBinding;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

/**
 * 应用模块:
 * <p>
 * 类描述: 行情首页
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-16
 */

@Route(path = RouterFragmentPath.Community.PAGER_COMMUNITY)
public class CommunityFragment extends MvvmLazyFragment<CommunityFragmentCommunityBinding, QuotationFragmentViewModel> implements IQuotationView  {

    private QuotationFragmentPageAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.community_fragment_community;
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
        adapter = new QuotationFragmentPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
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
    protected QuotationFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this).get(QuotationFragmentViewModel.class);
    }


    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataLoaded(ArrayList<Tabs> tabs) {

    }
}
