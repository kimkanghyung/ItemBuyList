package com.happiness.itembuylist;

import android.graphics.Bitmap;

/**
 * Created by kanghyun on 2017-10-08.
 */

public class Custom_List_Data {

    private String  Req_dt;
    private String  Palce_nm;
    private String  Total_pice;
    private int ser_num;
    private String Image_num;

    public String getImage_num() {
        return Image_num;
    }

    public void setImage_num(String image_num) {
        Image_num = image_num;
    }



    public int getSer_num() {
        return ser_num;
    }

    public void setSer_num(int ser_num) {
        this.ser_num = ser_num;
    }



    public Custom_List_Data(String Req_dt, String _Palce_nm, String _Total_pice,int _ser_num,String _image_num)
    {
        this.setReq_dt(Req_dt);
        this.setPalce_nm(_Palce_nm);
        this.setTotal_pice(_Total_pice);
        this.setSer_num(_ser_num);
        this.setImage_num(_image_num);
    }

    public String getPalce_nm() {
        return Palce_nm;
    }

    public void setPalce_nm(String palce_nm) {
        Palce_nm = palce_nm;
    }

    public String getReq_dt() {
        return Req_dt;
    }

    public void setReq_dt(String req_dt) {
        Req_dt = req_dt;
    }

    public String getTotal_pice() {
        return Total_pice;
    }

    public void setTotal_pice(String total_pice) {
        Total_pice = total_pice;
    }
}
