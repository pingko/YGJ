//package com.yzg.common.interceptor;
//
//import android.content.Context;
//
//import com.alibaba.android.arouter.facade.Postcard;
//import com.alibaba.android.arouter.facade.annotation.Interceptor;
//import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
//import com.alibaba.android.arouter.facade.template.IInterceptor;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.orhanobut.logger.Logger;
//import com.yzg.common.router.RouterActivityPath;
//import com.yzg.common.services.ILoginService;
//
//import java.io.IOException;
//
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * 应用模块:
// * <p>
// * 类描述: 登录状态拦截器
// * <p>
// *
// * @author darryrzhoong
// * @since 2020-02-29
// */
//@Interceptor(priority = 8)
//public class SessionInterceptor implements Interceptor {
//    @Override
//    public Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
//
//
//        Request request = chain.request();
//        //添加公共参数
//        if (request.method().equals("GET")) {
//            request = addGetParams(request);
//        } else if (request.method().equals("POST")) {
//            request = addPostParams(request);
//        }
//        //判断接口是否需要使用session
//        if (SPUtils.getInstance().getBoolean(MultipleFields.UPDATE_COOKIES.toString(), false)) {
//            //获取Cookie
//            getCookie(chain, request);
//            Request request_cookie = setCookie(request);
//            return chain.proceed(request_cookie);
//        }
//        return chain.proceed(request);
//    }
//
//    private void getCookie(Chain chain, Request request) throws IOException {
//        // 获取 Cookie
//        Response resp = chain.proceed(request);
//        List<String> cookies = resp.headers("Set-Cookie");
//        String cookieStr = "";
//        if (cookies != null && cookies.size() > 0) {
//            String s = cookies.get(0);
//            //sessionid值格式：JSESSIONID=AD5F5C9EEB16C71EC3725DBF209F6178，是键值对，不是单指值
//            cookieStr = s.substring(0, s.indexOf(";"));
//            //持久化到本地
//            SPUtils.getInstance().put(MultipleFields.COOKIES.toString(), cookieStr);
//        }
//
//    }
//
//    private Request setCookie(Request request) {
//        //设置cookie
//        String cookieStr = SPUtils.getInstance().getString(MultipleFields.COOKIES.toString(), "");
//        //判断cookie不为空
//        if (!cookieStr.isEmpty()) {
//            Request cookieRequest = request.newBuilder().addHeader("Cookie", cookieStr).build();
//            return cookieRequest ;
//        }
//        return request;
//    }
//}
