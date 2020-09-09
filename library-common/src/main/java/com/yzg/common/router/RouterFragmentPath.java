package com.yzg.common.router;

/**
 * 应用模块: 组件化路由
 * <p>
 * 类描述: 用于组件化开发中,ARouter Fragment路径统一注册, 注册的路径请写好注释、标注业务功能
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
public class RouterFragmentPath {

    /**
     * 首页组件
     */
    public static class Home {
        private static final String HOME = "/home";

        /**
         * 首页
         */
        public static final String PAGER_HOME = HOME + "/Home";
        public static final String PAGER_HOMES = HOME + "/Homes";
        public static final String PAGER_GDDSRANKLIST= HOME + "/GDDSRANKLIST";
        public static final String PAGER_GDDS = HOME + "/GDDS";

    }

    /**
     * 行情
     */
    public static class Community {
        private static final String COMMUNITY = "/community";
        private static final String QUOTATION = "/PAGER_QUOTATION";

        /**
         * 行情
         */
        public static final String PAGER_COMMUNITY = COMMUNITY + "/Community";
        public static final String PAGER_QUOTATION = QUOTATION + "/PAGER_QUOTATION";
    }

    /**
     * 资讯
     */
    public static class More {
        private static final String MORE = "/more";

        /**
         * 更多页面
         */
        public static final String PAGER_MORE = MORE + "/More";
    }

    public static class User {
        private static final String USER = "/user";

        /**
         * 个人中心
         */
        public static final String PAGER_USER = USER + "/User";
    }

    public static class Deal {
        private static final String DEAL = "/deal";
        public static final String PAGER_DEAL = DEAL + "/Deal";

    }

}
