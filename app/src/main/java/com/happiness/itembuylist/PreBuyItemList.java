package com.happiness.itembuylist;

/**
 * Created by kanghyun on 2017-10-13.
 */

public class PreBuyItemList {

    private String item_nm;
    private int pre_price;
    private String buypalce;
    private String buy_yn;

    public String getBuy_yn() {
        return buy_yn;
    }

    public void setBuy_yn(String buy_yn) {
        this.buy_yn = buy_yn;
    }

    public String getBuypalce() {
        return buypalce;
    }

    public void setBuypalce(String buypalce) {
        this.buypalce = buypalce;
    }

    public String getItem_nm() {
        return item_nm;
    }

    public void setItem_nm(String item_nm) {
        this.item_nm = item_nm;
    }

    public int getPre_price() {
        return pre_price;
    }

    public void setPre_price(int pre_price) {
        this.pre_price = pre_price;
    }
}
