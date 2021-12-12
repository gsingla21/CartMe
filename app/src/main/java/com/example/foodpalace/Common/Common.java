package com.example.foodpalace.Common;

public class Common {
    private static String marketname;
    private static String marketimg,payamount;
    private static int cartval;

    public static int getCartval() {
        return cartval;
    }

    public static String getPayamount() {
        return payamount;
    }

    public static void setPayamount(String payamount) {
        Common.payamount = payamount;
    }

    public static void setCartval(int cartval) {
        Common.cartval = cartval;
    }

    public static String getMarketimg() {
        return marketimg;
    }

    public static void setMarketimg(String marketimg) {
        Common.marketimg = marketimg;
    }

    public static String getMarketname() {
        return marketname;
    }

    public static void setMarketname(String marketname) {
        Common.marketname = marketname;
    }
}
