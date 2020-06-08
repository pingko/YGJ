package com.yzg.deal.deal;

import java.io.Serializable;

public class AliPayResultBean implements Serializable {


    /**
     * alipay_trade_app_pay_response : {"code":"10000","msg":"Success","app_id":"2021001159660791","auth_app_id":"2021001159660791","charset":"utf-8","timestamp":"2020-06-08 18:31:01","out_trade_no":"202006081830088","total_amount":"0.01","trade_no":"2020060822001424521402114836","seller_id":"2088731735809106"}
     * sign : divuLs3QTUeiO62iC/AnA9qMFteJP87hkN2ilWGb+bi3GlnbNn5a5HRYOdlmeyN8O040WkcmKwjanilPaYTipBifzT/wbx7s0aV0aXrXIeYcXEagwdu4cGYxHpXSZZCsO72f/4C9poMbFG/pywuSC4pL9KhCqTNMjFy0b1R/Y1fW4XGq9IwiMEZP8D6gLI7B49TFk1HwhnIu4D8mSN1ovcRU/6WAsg8KAZ9SMTOsfEw7fRktu5Ab0RUN4ytd5M6ssPSCIUhtBfowHhYG/gPZax8BTcR8L0MPlg29UTjrXqVh2G9+ga5EYlAtgUOOw8Dve6cP9V/0X1eceJd3yxMWPg==
     * sign_type : RSA2
     */

    private AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;

    public AlipayTradeAppPayResponseBean getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(AlipayTradeAppPayResponseBean alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public static class AlipayTradeAppPayResponseBean implements Serializable {
        /**
         * code : 10000
         * msg : Success
         * app_id : 2021001159660791
         * auth_app_id : 2021001159660791
         * charset : utf-8
         * timestamp : 2020-06-08 18:31:01
         * out_trade_no : 202006081830088
         * total_amount : 0.01
         * trade_no : 2020060822001424521402114836
         * seller_id : 2088731735809106
         */

        private String code;
        private String msg;
        private String app_id;
        private String auth_app_id;
        private String charset;
        private String timestamp;
        private String out_trade_no;
        private String total_amount;
        private String trade_no;
        private String seller_id;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getAuth_app_id() {
            return auth_app_id;
        }

        public void setAuth_app_id(String auth_app_id) {
            this.auth_app_id = auth_app_id;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }
    }
}
