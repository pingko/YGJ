package com.yzg.main.application;

import com.blankj.utilcode.util.Utils;
import com.yzg.base.base.BaseApplication;
import com.yzg.base.loadsir.EmptyCallback;
import com.yzg.base.loadsir.ErrorCallback;
import com.yzg.base.loadsir.LoadingCallback;
import com.yzg.base.loadsir.TimeoutCallback;
import com.yzg.common.IModuleInit;
import com.yzg.common.adapter.ScreenAutoAdapter;
import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.logger.Logger;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.GsonDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.cookie.CookieManger;

/**
 * 应用模块: main
 * <p>
 * 类描述: main组件的业务初始化
 * <p>
 *
 * http://120.78.206.203:8088/
 * "http://baobab.kaiyanapp.com"
 * @since 2020-02-26
 */
public class MainModuleInit implements IModuleInit {

    public static final String BASE_URL="http://120.78.206.203:8088/";

    @Override
    public boolean onInitAhead(BaseApplication application) {
        ScreenAutoAdapter.setup(application);
        EasyHttp.init(application);
        if (application.issDebug()) {
            EasyHttp.getInstance().debug("easyhttp", true);
        }
        EasyHttp.getInstance()
                .setBaseUrl(BASE_URL)
                .setReadTimeOut(15 * 1000)
                .setWriteTimeOut(15 * 1000)
                .setConnectTimeout(15 * 1000)
                .setRetryCount(3)
                .setCookieStore(new CookieManger(application))
                .setCacheDiskConverter(new GsonDiskConverter())
                .setCacheMode(CacheMode.FIRSTREMOTE);
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
        Utils.init(application);
        Logger.i("main组件初始化完成 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(BaseApplication application) {
        return false;
    }
}
