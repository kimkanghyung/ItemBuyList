package com.happiness.itembuylist;

/**
 * Created by kanghyun on 2017-10-08.
 */

public class receiptList {
    private String req_dt;
    private String buy_place,image_num;
    private int ser_num,total_price;

    public String getBuy_place() {
        return buy_place;
    }

    public void setBuy_place(String buy_place) {
        this.buy_place = buy_place;
    }

    public String getImage_num() {
        return image_num;
    }

    public void setImage_num(String image_num) {
        this.image_num = image_num;
    }

    public String getReq_dt() {
        return req_dt;
    }

    public void setReq_dt(String req_dt) {
        this.req_dt = req_dt;
    }

    public int getSer_num() {
        return ser_num;
    }

    public void setSer_num(int ser_num) {
        this.ser_num = ser_num;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }



}
