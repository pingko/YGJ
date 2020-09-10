package com.yzg.main.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.utils.GsonUtils;
import com.yzg.common.adapter.ScreenAutoAdapter;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.main.R;

/**
 * 应用模块: 主业务模块
 * <p>
 * 类描述: 欢迎页面
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-26
 */
public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenAutoAdapter.match(this, 375.0f);
        setContentView(R.layout.main_activity_splash);
        ImmersionBar.with(this)
                .titleBar(findViewById(R.id.top_view))
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init();
        mHandler.postDelayed(this::startToMain, 1000);
        if (!GsonUtils.isShowTrue()){
            findViewById(R.id.rl_top).setBackgroundResource(R.drawable.nono);
        }else {
            findViewById(R.id.rl_top).setBackgroundResource(R.drawable.splash_bg);
        }
    }

    private void startToMain() {
        String token = MmkvHelper.getInstance().getMmkv().decodeString("token", "");
        Log.e("SplashActivity", "token:" + token);
        if (TextUtils.isEmpty(token)) {
            ARouter.getInstance()
                    .build(RouterActivityPath.User.PAGER_LOGIN)
                    .withInt("splashLogin",1)
                    .navigation();
        } else {
            MainActivity.start(this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity销毁时移除所有消息,防止内存泄漏
        mHandler.removeCallbacksAndMessages(null);
    }
}
