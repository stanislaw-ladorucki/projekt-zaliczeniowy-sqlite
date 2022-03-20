package com.example.sklep;

public class GlobalUserSessionManager {
    private static ShopOrder cartOrder;
    private static String userName = "John Doe";

    public static ShopOrder getCartOrder() {
        if (cartOrder == null)
            cartOrder = new ShopOrder();
        return cartOrder;
    }

    public static void resetCartOrder() {
        cartOrder = null;
    }

    public static String getUserCustomerName() {
        return userName;
    }
    public static void setUserName(String value) {
        userName = value;
    }
}
