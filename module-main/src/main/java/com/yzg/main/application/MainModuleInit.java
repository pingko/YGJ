package com.yzg.main.application;

import com.blankj.utilcode.util.Utils;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.yzg.base.base.BaseApplication;
import com.yzg.base.loadsir.EmptyCallback;
import com.yzg.base.loadsir.ErrorCallback;
import com.yzg.base.loadsir.LoadingCallback;
import com.yzg.base.loadsir.TimeoutCallback;
import com.yzg.common.IModuleInit;
import com.yzg.common.adapter.ScreenAutoAdapter;
import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
//import com.zhouyou.http.EasyHttp;
//import com.zhouyou.http.cache.converter.GsonDiskConverter;
//import com.zhouyou.http.cache.model.CacheMode;
//import com.zhouyou.http.cookie.CookieManger;

/**
 * 应用模块: main
 * <p>
 * 类描述: main组件的业务初始化
 * <p>
 * <p>
 * http://120.78.206.203:8088/
 * "http://baobab.kaiyanapp.com"
 *
 * @since 2020-02-26
 */
public class MainModuleInit implements IModuleInit {

    public static final String BASE_URL = "http://120.78.206.203:8088/";

    @Override
    public boolean onInitAhead(BaseApplication application) {
        ScreenAutoAdapter.setup(application);
//        EasyHttp.init(application);
//        if (application.issDebug()) {
//            EasyHttp.getInstance().debug("easyhttp", true);
//        }
//        EasyHttp.getInstance()
//                .setBaseUrl(BASE_URL)
//                .setReadTimeOut(15 * 1000)
//                .setWriteTimeOut(15 * 1000)
//                .setConnectTimeout(15 * 1000)
//                .setRetryCount(3)
//                .setCookieStore(new CookieManger(application))
//                .setCacheDiskConverter(new GsonDiskConverter())
//                .setCacheMode(CacheMode.DEFAULT);
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
        Utils.init(application);
        initOkGo(application);
//        HeConfig.init("HE1812191428311732", "1fb80996cb4f45e0b460bbf48f93e845");
        closeAndroidPDialog();
        Logger.i("main组件初始化完成 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(BaseApplication application) {
        return false;
    }

    private void initOkGo(BaseApplication application) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(25, TimeUnit.SECONDS);
        builder.writeTimeout(25, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(application)));
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
//log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
//log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        OkGo.getInstance().init(application)
                .setRetryCount(0)
                .setOkHttpClient(builder.build());

    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
