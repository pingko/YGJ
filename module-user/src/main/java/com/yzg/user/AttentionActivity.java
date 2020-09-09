package com.yzg.user;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yzg.common.router.RouterActivityPath;

/**
 * 应用模块:
 * <p>
 * 类描述: 关注页面
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-29
 */
@Route(path = RouterActivityPath.User.PAGER_ATTENTION)
public class AttentionActivity extends AppCompatActivity {

    private FrameLayout web_view_container;
    private WebView web_view;
    private TextView rl_title;
    private ImageView ivBack;
    private int type;

    private final String LANGUAGE_CN = "zh-CN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_attention);
        initView();
    }

    private void initView() {
        type = getIntent().getIntExtra("type", 1);
        web_view_container = findViewById(R.id.web_view_container);
        rl_title = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        web_view = new WebView(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        web_view.setLayoutParams(params);
        web_view.setWebViewClient(new WebViewClient());
        //动态添加WebView，解决在xml引用WebView持有Activity的Context对象，导致内存泄露
        web_view_container.addView(web_view);

        String language = AppUtil.getLanguage(AttentionActivity.this);
        Log.i("aaaa", "当前语言：" + language);

//        if (LANGUAGE_CN.equals(language)) {
//            web_view.loadUrl("file:///android_asset/user_agreement.html");
//        } else {
//            web_view.loadUrl("file:///android_asset/user_agreement.html");
//        }

        if (1 == type) {
            web_view.loadUrl("file:///android_asset/user_service.html");
            rl_title.setText("用户协议");

        } else {
            web_view.loadUrl("file:///android_asset/privacy.html");
            rl_title.setText("隐私政策");
        }

        ivBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        web_view_container.removeAllViews();
        web_view.destroy();
    }
}
