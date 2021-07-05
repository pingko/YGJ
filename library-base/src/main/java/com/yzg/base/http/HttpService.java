package com.yzg.base.http;


public class HttpService {
    public static final String TOKEN = "token";
    //https://www.zitic.net/
//    public static final String BASE_URL="http://120.78.206.203:8088/";
//    public static final String BASE_URL="http://admin.zitic.net:8088/";
    public static final String BASE_URL="https://admin.zitic.net/";
//    public static final String BASE_URL="http://371j86o781.wicp.vip/";
    public static final String REGISTER = BASE_URL+ "custAdd";//注册
    public static final String FORGOTPASSWORD = BASE_URL+ "forgotPassword";//注册
    public static final String LOGIN = BASE_URL+ "login";//登录
    public static final String Gold_info_list = BASE_URL+ "system/aipGoldInfo/list";//产品列表
    public static final String Gold_info_detail = BASE_URL+ "system/aipGoldInfo/getInfo";//产品详情
    public static final String Gold_custom_stock = BASE_URL+ "system/storage/getStorage";//查询客户库存
    public static final String EB_Quotation_price = BASE_URL+ "system/price/getPrice";//获取行情价格数据
    public static final String EB_Quotation_priceList = BASE_URL+ "system/price/list";//行情多条数据
    public static final String EB_Pay_url = BASE_URL+ "notify_alipay.asp";//支付宝支付地址
    public static final String EB_Pay = BASE_URL+ "system/goldFlow/buyStorage";// 购买
    public static final String EB_Pay_Success = BASE_URL+ "system/goldFlow/upStorage";//支付成功
    public static final String EB_BuyJLYT = BASE_URL+ "system/goldFlow/buyAg";//购买 积利银条
    public static final String EB_Take = BASE_URL+ "system/goldFlow/takeAg";//提货
    public static final String EB_Sale = BASE_URL+ "system/goldFlow/sell";//卖出
    public static final String EB_DealList = BASE_URL+ "system/goldFlow/list";//交易流水
    public static final String EB_UPDATE = BASE_URL+ "app/versionUpdate";//更新
    public static final String EB_LOGOUT = BASE_URL+ "app/token/logout";//退出
    public static final String EB_getUser = BASE_URL+ "getUser";//查询信息
    public static final String EB_editUser = BASE_URL+ "editUser";//修改信息
    public static final String EB_sendCode = BASE_URL+ "sendSMS";//发送验证码
    public static final String EB_checkCode = BASE_URL+ "smsOK";//校验验证码
    public static final String EB_editPwd = BASE_URL+ "system/user/profile/resetPwd";//修改密码
    public static final String EB_jslist = BASE_URL+ "system/military/list";//修改密码
    public static final String EB_js_detail = BASE_URL+ "system/military/get";//修改密码
    public static final String EB_Take_Success = BASE_URL+ "system/takeDelivery/upStorage";//提货成功

}
