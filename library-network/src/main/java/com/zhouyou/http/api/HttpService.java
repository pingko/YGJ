package com.zhouyou.http.api;

import com.zhouyou.http.EasyHttp;

public interface HttpService {
    public static final String TOKEN = "token";

    public static final String REGISTER = EasyHttp.getBaseUrl() + "custAdd";
    public static final String LOGIN = EasyHttp.getBaseUrl() + "login";
}
