package com.yzg.more;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tamsiree.rxkit.RxWebViewTool;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.base.viewmodel.IMvvmBaseViewModel;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.more.adapter.MoreFragmentPageAdapter;
import com.yzg.more.databinding.MoreFragmentMoreBinding;
import com.yzg.more.message.MessageFragment;
import com.yzg.more.themes.ThemesFragment;
import com.yzg.more.topic.TopicFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 应用模块:
 * <p>
 * 类描述: 资讯主页
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-22
 */
@Route(path = RouterFragmentPath.More.PAGER_MORE)
public class MoreFragment
        extends MvvmLazyFragment<MoreFragmentMoreBinding, IMvvmBaseViewModel> {

    public WebSettings webSettings;
    private MoreFragmentPageAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.more_fragment_more;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
//        initData();

    }

    private void initView() {
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
        adapter = new MoreFragmentPageAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        binding.vpHomeContent.setAdapter(adapter);
        webShow();
    }

    private void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(ThemesFragment.newInstance());
        fragments.add(TopicFragment.newInstance());
        fragments.add(TopicFragment.newInstance());
//        fragments.add(MessageFragment.newInstance());
        adapter.setData(fragments);
        binding.vpHomeContent.setCurrentItem(1);
    }

    private void webShow() {
        webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        binding.webview.setWebViewClient(new MyWebViewClient());
        binding.webview.loadUrl("http://www.jin10.com");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
// return super.shouldOverrideUrlLoading(view, url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
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
