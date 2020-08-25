package com.yzg.base.http;


public class HttpService {
    public static final String TOKEN = "token";
//    public static final String BASE_URL="http://120.78.206.203:8088/";
    public static final String BASE_URL="http://admin.zitic.net:8088/";
    public static final String REGISTER = BASE_URL+ "custAdd";//注册
    public static final String LOGIN = BASE_URL+ "login";//登录
    public static final String Gold_info_list = BASE_URL+ "system/aipGoldInfo/list";//产品列表
    public static final String Gold_info_detail = BASE_URL+ "system/aipGoldInfo/getInfo";//产品详情
    public static final String Gold_custom_stock = BASE_URL+ "system/storage/getStorage";//查询客户库存
    public static final String EB_Quotation_price = BASE_URL+ "system/price/getPrice";//获取行情价格数据
    public static final String EB_Pay_url = BASE_URL+ "notify_alipay.asp";//支付宝支付地址
    public static final String EB_Pay = BASE_URL+ "system/goldFlow/buyStorage";//支付
    public static final String EB_Pay_Success = BASE_URL+ "system/goldFlow/upStorage";//支付成功
    public static final String EB_BuyJLYT = BASE_URL+ "system/goldFlow/buyAg";//购买 积利银条
    public static final String EB_Take = BASE_URL+ "system/goldFlow/takeAg";//提货
}
