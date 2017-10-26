package com.happiness.itembuylist;

/**
 * Created by kanghyun on 2017-10-08.
 */

public class CustomSearchData {

    private String  Req_dt;
    private String  Palce_nm;
    private String  Total_pice;
    private String Item_nm;

    public String getItem_nm() {
        return Item_nm;
    }

    public void setItem_nm(String item_nm) {
        Item_nm = item_nm;
    }






    public CustomSearchData(String Req_dt, String _Palce_nm, String _Total_pice, String _item_nm)
    {
        this.setReq_dt(Req_dt);
        this.setPalce_nm(_Palce_nm);
        this.setTotal_pice(_Total_pice);
        this.setItem_nm(_item_nm);
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
