package com.zhouyou.http.api;

import com.zhouyou.http.EasyHttp;

public class HttpService {
    public static final String TOKEN = "token";

    public static final String REGISTER = EasyHttp.getBaseUrl() + "custAdd";//注册
    public static final String LOGIN = EasyHttp.getBaseUrl() + "login";//登录
    public static final String Gold_info_list = EasyHttp.getBaseUrl() + "system/aipGoldInfo/list";//产品列表
    public static final String Gold_info_detail = EasyHttp.getBaseUrl() + "system/aipGoldInfo/getInfo";//产品详情
    public static final String Gold_custom_stock = EasyHttp.getBaseUrl() + "system/storage/getStorage";//查询客户库存
    public static final String EB_Quotation_price = EasyHttp.getBaseUrl() + "system/price/getPrice";//获取行情价格数据
    public static final String EB_Pay_url = EasyHttp.getBaseUrl() + "notify_alipay.asp";//支付宝支付地址
    public static final String EB_Pay = EasyHttp.getBaseUrl() + "system/goldFlow/buyStorage";//支付
    public static final String EB_Pay_Success = EasyHttp.getBaseUrl() + "system/goldFlow/upStorage";//支付成功
    public static final String EB_Buy = EasyHttp.getBaseUrl() + "system/goldFlow/buyAg";//购买
    public static final String EB_Take = EasyHttp.getBaseUrl() + "system/goldFlow/takeAg";//提货
}
