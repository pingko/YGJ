package com.yzg.common.router;

/**
 * 应用模块: 组件化路由
 * <p>
 * 类描述: 用于在组件化开发中,利用ARouter 进行Activity跳转的统一路径注册, 注册时请写好注释、标注界面功能业务
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
public class RouterActivityPath
{
    /**
     * main组件
     */
    public static class Main
    {
        private static final String MAIN = "/main";
        
        /** 主页面 */
        public static final String PAGER_MAIN = MAIN + "/Main";
    }
    
    /**
     * 视频播放(video)组件
     */
    public static class Video
    {
        private static final String VIDEO = "/video";
        
        /* 视频播放界面 */
        public static final String PAGER_VIDEO = VIDEO + "/Video";
        
    }

    public static class Quotation
    {
        private static final String Quotation = "/quotation";

        public static final String Quotation_main = Quotation + "/main";
        public static final String Quotation_main_chart = Quotation + "/mainchart";

    }

    public static class Deal
    {
        private static final String DEAL = "/deal";

        /** 登录界面 */
        public static final String PAGER_DEAL = DEAL + "/deal";
        public static final String PAGER_DEAL_BUY = DEAL + "/Deal_BUY";

    }

    public static class Home
    {
        private static final String Home = "/home";

        /** 登录界面 */
        public static final String PAGER_HOME_GDDSLIST = Home + "/GDDSLIST";
        public static final String PAGER_HOME_GDDSRANKLIST = Home + "/GDDSRANKLIST";
        public static final String PAGER_HOME_GDDS_DETAIL = Home + "/GDDSLISTDETAIL";

    }
    
    public static class User
    {
        private static final String USER = "/user";
        
        /** 登录界面 */
        public static final String PAGER_LOGIN = USER + "/Login";
        public static final String PAGER_Address = USER + "/Address";
        public static final String PAGER_Address_add = USER + "/Address_add";
        public static final String PAGER_REGISTER = USER + "/REGISTER";
        public static final String PAGER_BINDALIPAY = USER + "/BINDALIPAY";
        public static final String PAGER_EDITPASSWORD = USER + "/EDITPASSWORD";

        /** 关注页面 */
        public static final String PAGER_ATTENTION = USER + "/attention";
    }
}
