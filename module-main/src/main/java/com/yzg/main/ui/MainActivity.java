package com.yzg.main.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.utils.GsonUtils;
import com.yzg.base.viewmodel.IMvvmBaseViewModel;
import com.yzg.common.adapter.ScreenAutoAdapter;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.main.R;
import com.yzg.main.adapter.MainPageAdapter;
import com.yzg.main.databinding.MainActivityMainBinding;
import com.yzg.main.utils.ColorUtils;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.NavigationController;

/**
 * app 主页面
 *
 * @author darryrzhoong
 */

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity
        extends MvvmBaseActivity<MainActivityMainBinding, IMvvmBaseViewModel> {

    private List<Fragment> fragments;

    private MainPageAdapter adapter;

    public NavigationController mNavigationController;


    @Override
    protected IMvvmBaseViewModel getViewModel() {
        return null;
    }


    public static void start(Context context) {
        MmkvHelper.getInstance().getMmkv().encode("first", false);
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScreenAutoAdapter.match(this, 375.0f);
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color_bar)
                .navigationBarColor(R.color.main_color_bar)
                .fitsSystemWindows(true)
                .autoDarkModeEnable(true)
                .init();

        initView();
        initFragment();
        LiveEventBus
                .get("index", Integer.class)
                .observe(this, s -> {
                    mNavigationController.setSelect(s);
                });
        LiveEventBus
                .get("reLogin", Integer.class)
                .observe(this, s -> {
                    MmkvHelper.getInstance().getMmkv().clearAll();
                    ARouter.getInstance()
                            .build(RouterActivityPath.User.PAGER_LOGIN)
                            .navigation();
                  finish();
                });
    }

    private void initView() {


//        if (!GsonUtils.isShowTrue()){
//            mNavigationController = binding.bottomView.material()
//                    .addItem(R.drawable.main_home,
//                            "首页",
//                            ColorUtils.getColor(this, R.color.main_choose))
//                    .addItem(R.drawable.main_info,
//                            "资讯",
//                            ColorUtils.getColor(this, R.color.main_choose))
//                    .addItem(R.drawable.main_mine,
//                            "我的",
//                            ColorUtils.getColor(this, R.color.main_choose))
//                    .setDefaultColor(
//                            ColorUtils.getColor(this, R.color.main_bottom_default_color))
//                    .enableAnimateLayoutChanges()
//                    .build();
//        }else {
            mNavigationController = binding.bottomView.material()
                    .addItem(R.drawable.main_home,
                            "首页",
                            ColorUtils.getColor(this, R.color.main_choose))
                    .addItem(R.drawable.main_quotation,
                            "行情",
                            ColorUtils.getColor(this, R.color.main_choose))
                    .addItem(R.drawable.main_deal,
                            "交易",
                            ColorUtils.getColor(this, R.color.main_choose))
                    .addItem(R.drawable.main_info,
                            "资讯",
                            ColorUtils.getColor(this, R.color.main_choose))
                    .addItem(R.drawable.main_mine,
                            "我的",
                            ColorUtils.getColor(this, R.color.main_choose))
                    .setDefaultColor(
                            ColorUtils.getColor(this, R.color.main_bottom_default_color))
                    .enableAnimateLayoutChanges()
                    .build();
//        }
//        mNavigationController.setHasMessage(2, true);
//        mNavigationController.setMessageNumber(3, 6);
        adapter = new MainPageAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        binding.cvContentView.setOffscreenPageLimit(1);
        binding.cvContentView.setAdapter(adapter);
        mNavigationController.setupWithViewPager(binding.cvContentView);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        //通过ARouter 获取其他组件提供的fragment
//        if (GsonUtils.isShowTrue()){
            Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOMES).navigation();
            Fragment communityFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Community.PAGER_QUOTATION).navigation();
//            Fragment communityFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Community.PAGER_COMMUNITY).navigation();
            Fragment dealFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Deal.PAGER_DEAL).navigation();
            Fragment moreFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.More.PAGER_MORE).navigation();
            Fragment userFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.User.PAGER_USER).navigation();
            fragments.add(homeFragment);
            fragments.add(communityFragment);
            fragments.add(dealFragment);
            fragments.add(moreFragment);
            fragments.add(userFragment);
            adapter.setData(fragments);
//        }else {
//            Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOMES).navigation();
//            Fragment moreFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.More.PAGER_MORE).navigation();
//            Fragment userFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.User.PAGER_USER).navigation();
//            fragments.add(homeFragment);
//            fragments.add(moreFragment);
//            fragments.add(userFragment);
//            adapter.setData(fragments);
//        }

    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void testData(long time) {
        long currentTime = System.currentTimeMillis();

    }


}
