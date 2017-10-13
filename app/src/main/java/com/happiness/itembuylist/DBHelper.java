package com.happiness.itembuylist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by kanghyun on 2017-10-03.
 */

public class DBHelper extends SQLiteOpenHelper {
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.w("Table" ,  "Table onCreate!!!!!");

        StringBuffer sb0 = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        StringBuffer sb4 = new StringBuffer();


        sb4.append(" CREATE TABLE IF NOT EXISTS ");
        sb4.append( "FAVORITE_PLACE" + "( ");
        sb4.append(" PLACE TEXT ) ");

    //    sb4.append("CREATE TABLE IF NOT EXISTS FAVORITE_PLACE (PLACE TEXT) AS ");
    //    sb4.append("SELECT 'E.mart' FROM DUAL                                 ");

        sqLiteDatabase.execSQL(sb4.toString());
        sqLiteDatabase.execSQL("INSERT INTO FAVORITE_PLACE VALUES('E.mart');");
        sqLiteDatabase.execSQL("INSERT INTO FAVORITE_PLACE VALUES('L.mart');");
        sqLiteDatabase.execSQL("INSERT INTO FAVORITE_PLACE VALUES('H.plus');");

        System.out.println("FAVORITE_PLACE 데이터베이스 생성 성공");




        // if(tablename.equals("FAVORITE_BUY_LIST")){
        /*2017.10.13 변경*/
        sb0.append(" CREATE TABLE IF NOT EXISTS ");
        sb0.append( " PRE_BUY_LIST" + "( ");
        sb0.append(" ITEM_NM TEXT ,");
        sb0.append(" BUY_PLACE TEXT ,");
        sb0.append(" ITEM_PRE_PRICE INTEGER ,");
        sb0.append(" BUY_YN TEXT ,");
        sb0.append(" PRIMARY KEY(ITEM_NM,BUY_PLACE));");

        sqLiteDatabase.execSQL(sb0.toString());
        /*2017.10.13 변경*/

        sb.append(" CREATE TABLE IF NOT EXISTS ");
        sb.append( "FAVORITE_BUY_LIST" + "( ");
        sb.append(" ITEM_CD INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" ITEM_NM TEXT, ");
        sb.append(" REQ_DT INTEGER )");

        sqLiteDatabase.execSQL(sb.toString());
        System.out.println("데이터베이스 생성 성공");

        sb2.append(" CREATE TABLE IF NOT EXISTS ");
        sb2.append( " BUY_LIST" + "( ");
        sb2.append(" REQ_DT TEXT, ");
        sb2.append(" ITEM_NM TEXT ,");
        sb2.append(" BUY_PLACE TEXT ,");
        sb2.append(" ITEM_PRE_PRICE INTEGER ,");
        sb2.append(" BUY_YN TEXT ,");
        sb2.append(" PRIMARY KEY(REQ_DT, ITEM_NM,BUY_PLACE));");


        sqLiteDatabase.execSQL(sb2.toString());

        sb3.append(" CREATE TABLE IF NOT EXISTS ");
        sb3.append( " RECEIPT_LIST" + "( ");
        sb3.append(" SER_NUM INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb3.append(" REQ_DT TEXT, ");
        sb3.append(" BUY_PLACE TEXT ,");
        sb3.append(" TOTAL_PRICE INTEGER ,");
        sb3.append(" IMAGE_NUM TEXT )");


        sqLiteDatabase.execSQL(sb3.toString());

       /* Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name = 'FAVORITE_BUY_LIST'" , null);
        cursor.moveToFirst();
        Log.w("Table" ,  "Table create Start");
        if(cursor.getCount()>0) {
            Log.w("Table", "Table already create");
        }
        else {
            if(cursor.getCount()>0) {
                Log.w("Table", "Table not yet");
            }
        }*/




    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean insertFirstFavoritePlace(){

        SQLiteDatabase db = this.getWritableDatabase();
        List<String> li = new ArrayList<String>();


        li.add("E.mart");
        li.add("H.plus");
        li.add("L.mart");

        for(String str : li){
            StringBuffer insertSQL = new StringBuffer();
            insertSQL.append(" INSERT INTO FAVORITE_PLACE ( ");
            insertSQL.append(" PLACE ) ");
            insertSQL.append(" VALUES ( ? ) ");
            db.execSQL(insertSQL.toString());
            //Log.w("getPre_price" ,  String.valueOf(biltmp.getPre_price()));
//            Log.w("getBuy_yn" , biltmp.getBuy_yn());

            db.execSQL(insertSQL.toString(), new Object[]{
                 str
            });
        }

        db.close();

        return true;

    }

    public boolean addPreBuyListItem(List<PreBuyItemList> bil){

        SQLiteDatabase db = this.getWritableDatabase();

        for(PreBuyItemList biltmp : bil){
            StringBuffer sb = new StringBuffer();
            //FavoriteItem fit = new FavoriteItem();
            // fit = fi[i];
            sb.append(" INSERT INTO PRE_BUY_LIST ( ");
            sb.append(" ITEM_NM , BUY_PLACE, ITEM_PRE_PRICE ,BUY_YN ) ");
            sb.append(" VALUES ( ? ,? ,? ,? ) ");

            Log.w("getItem_nm" , biltmp.getItem_nm());
            Log.w("getBuypalce" , biltmp.getBuypalce());
            if(String.valueOf(biltmp.getPre_price()).equals("")){
                biltmp.setPre_price(0);
            }
            //Log.w("getPre_price" ,  String.valueOf(biltmp.getPre_price()));
//            Log.w("getBuy_yn" , biltmp.getBuy_yn());

            db.execSQL(sb.toString(), new Object[]{
                    biltmp.getItem_nm()
                    ,biltmp.getBuypalce()
                    ,biltmp.getPre_price()
                    ,biltmp.getBuy_yn()
            });
        }

        db.close();

        return true;

    }


    public boolean checkPreBuyListItem(String place,String Item_nm,String buyyn)
    {
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();
        sb.append(" SELECT ITEM_NM , BUY_PLACE, ITEM_PRE_PRICE,BUY_YN FROM PRE_BUY_LIST ");
        sb.append("WHERE " + "BUY_PLACE =" + "'" + place + "'" + " and " + "BUY_YN =" + "'" + buyyn + "'");
        sb.append(" and " + "ITEM_NM =" + "'" + Item_nm + "' "  + "LIMIT 1");

        Log.e("SQL", sb.toString());

        Cursor cursor = db.rawQuery(sb.toString(), null);
        if(cursor.getCount() == 0){
            return false;
        }else{
            return true;
        }

    }

    public List<PreBuyItemList> getPreBuyListItem(String place,String buyyn)
    {
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();
        sb.append(" SELECT ITEM_NM , BUY_PLACE, ITEM_PRE_PRICE,BUY_YN FROM PRE_BUY_LIST ");
        sb.append("WHERE " + "BUY_PLACE =" + "'" + place + "'" + " and " + "BUY_YN =" + "'" + buyyn + "'");

        Log.e("SQL", sb.toString());

        Cursor cursor = db.rawQuery(sb.toString(), null);
        List<PreBuyItemList> li = new ArrayList<PreBuyItemList>();
        FavoriteItem favoriteitem = null;
        while( cursor.moveToNext() )
        {
            PreBuyItemList prebuyitemlist = new PreBuyItemList();
            prebuyitemlist.setItem_nm(cursor.getString(0));
            prebuyitemlist.setBuypalce(cursor.getString(1));
            prebuyitemlist.setPre_price(Integer.parseInt(cursor.getString(2)));
            prebuyitemlist.setBuy_yn(cursor.getString(3));

            li.add(prebuyitemlist);
        }
        return li;
    }

    public void deletePreBuyListItem(String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM PRE_BUY_LIST ");
        //sb.append("WHERE " + "BUY_PLACE =" + "'" + place + "'" + " and " + "BUY_YN =" + "'" + buyyn + "'" );
        sb.append("WHERE " + "BUY_PLACE =" + "'" + place + "'");

        Log.e("DELETE SQL", sb.toString());
        db.execSQL(sb.toString());
        db.close();
    }

    public void updatePreBuyListItem(String place,String Itemnm,String buyyn,int price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE PRE_BUY_LIST ");
        sb.append(" SET BUY_YN = '" + buyyn + "'" );
        sb.append("WHERE " + "BUY_PLACE =" + "'" + place + "'" + " and " +"ITEM_NM = " + "'" + Itemnm + "'");

        Log.e("DELETE SQL", sb.toString());
        db.execSQL(sb.toString());
        db.close();
    }

    public boolean addBuyListItem2(String REQ_DT, String ITEM_NM , String BUY_PLACE, int ITEM_PRE_PRICE ,String BUY_YN){

        SQLiteDatabase db = this.getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        //FavoriteItem fit = new FavoriteItem();
        // fit = fi[i];

        StringBuffer sb2 = new StringBuffer();

        sb2.append(" DELETE FROM BUY_LIST ");
        sb2.append(" WHERE REQ_DT = " + "'" + REQ_DT + "'" );
        sb2.append(" AND BUY_PLACE = " + "'" + BUY_PLACE  + "'" );
        sb2.append(" AND ITEM_NM = " + "'" +  ITEM_NM + "'" );
        db.execSQL(sb2.toString());

       // db.close();

        db = this.getWritableDatabase();
        sb.append(" INSERT INTO BUY_LIST ( ");
        sb.append(" REQ_DT, ITEM_NM , BUY_PLACE, ITEM_PRE_PRICE ,BUY_YN ) ");
        sb.append(" VALUES ( ? ,? ,? ,? ,? ) ");

        db.execSQL(sb.toString(), new Object[]{
                REQ_DT
                ,ITEM_NM
                ,BUY_PLACE
                ,ITEM_PRE_PRICE
                ,BUY_YN
        });


        db.close();

        return true;

    }



    public boolean addFavoriteItem(List<FavoriteItem> fit){

        //deleteFavoriteItem();
        SQLiteDatabase db = this.getWritableDatabase();

        for(FavoriteItem fi : fit){
            StringBuffer sb = new StringBuffer();
            //FavoriteItem fit = new FavoriteItem();
            // fit = fi[i];
            sb.append(" INSERT INTO FAVORITE_BUY_LIST ( ");
            sb.append(" ITEM_NM, REQ_DT ) ");
            sb.append(" VALUES ( ?, ? ) ");

            Log.w("getItem_nm" , fi.getItem_nm());
            Log.w("getReq_dt" , fi.getReq_dt());
            db.execSQL(sb.toString(), new Object[]{
                    fi.getItem_nm()
                    ,fi.getReq_dt()
            });
        }


        db.close();

        return true;

        //
        // Toast.makeText(context, "addFavoriteItem 완료", Toast.LENGTH_SHORT).show();

    }

    public List getFavoriteItem()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ITEM_NM FROM FAVORITE_BUY_LIST "); // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        List favoriteitemlist = new ArrayList();
        FavoriteItem favoriteitem = null; // moveToNext 다음에 데이터가 있으면 true 없으면 false
        while( cursor.moveToNext() )
        {
            favoriteitem = new FavoriteItem();
            favoriteitem.setItem_nm(cursor.getString(0));
            favoriteitemlist.add(cursor.getString(0));
        }
        return favoriteitemlist;
    }

    public void deleteFavoriteItem()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM FAVORITE_BUY_LIST "); // 읽기 전용 DB 객체를 만든다.
        db.execSQL(sb.toString());
        db.close();
    }


    public boolean addBuyListItem(List<BuyItemList> bil){

        SQLiteDatabase db = this.getWritableDatabase();

        for(BuyItemList biltmp : bil){
            StringBuffer sb = new StringBuffer();

            //FavoriteItem fit = new FavoriteItem();
            // fit = fi[i];
            sb.append(" INSERT INTO BUY_LIST ( ");
            sb.append(" REQ_DT, ITEM_NM , BUY_PLACE, ITEM_PRE_PRICE ,BUY_YN ) ");
            sb.append(" VALUES ( ? ,? ,? ,? ,? ) ");

            Log.w("getReq_dt" , biltmp.getReq_dt());
            Log.w("getItem_nm" , biltmp.getItem_nm());
            Log.w("getBuypalce" , biltmp.getBuypalce());
            if(String.valueOf(biltmp.getPre_price()).equals("")){
                biltmp.setPre_price(0);
            }
            //Log.w("getPre_price" ,  String.valueOf(biltmp.getPre_price()));
//            Log.w("getBuy_yn" , biltmp.getBuy_yn());

            db.execSQL(sb.toString(), new Object[]{
                    biltmp.getReq_dt()
                    ,biltmp.getItem_nm()
                    ,biltmp.getBuypalce()
                    ,biltmp.getPre_price()
                    ,biltmp.getBuy_yn()
            });
        }

        db.close();

        return true;

    }

    public List<BuyItemList> getBuyListItem(String req_dt,String place)
    {
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();
        sb.append(" SELECT ITEM_NM , BUY_PLACE, ITEM_PRE_PRICE FROM BUY_LIST ");
        sb.append("WHERE " + " REQ_DT ="+ "'" + req_dt + "'" + " and " + "BUY_PLACE =" + "'" + place + "'");

        Log.e("SQL", sb.toString());

        Cursor cursor = db.rawQuery(sb.toString(), null);
        List<BuyItemList> li = new ArrayList<BuyItemList>();
        FavoriteItem favoriteitem = null;
        while( cursor.moveToNext() )
        {
            BuyItemList buyitemlist = new BuyItemList();
            buyitemlist.setItem_nm(cursor.getString(0));
            buyitemlist.setBuypalce(cursor.getString(1));
            buyitemlist.setPre_price(Integer.parseInt(cursor.getString(2)));

            li.add(buyitemlist);
        }
        return li;
    }

    public void deleteBuyListItem(String req_dt,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM BUY_LIST ");
        sb.append("WHERE " + " REQ_DT =" +"'" + req_dt + "'" + " and " + "BUY_PLACE =" + "'" + place + "'");

        Log.e("DELETE SQL", sb.toString());
        db.execSQL(sb.toString());
        db.close();
    }

    public String selectPlace(String req_dt)
    {
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();
        sb.append(" SELECT BUY_PLACE FROM BUY_LIST ");
        sb.append("WHERE " + " REQ_DT ="+ "'" + req_dt + "'" + "LIMIT 1");
        Log.e("selectPlace SQL", sb.toString());
        Cursor cursor = db.rawQuery(sb.toString(), null);
        List li = new ArrayList();
        String tmp = null;
        while( cursor.moveToNext() )
        {
            tmp = cursor.getString(0);
        }
        return tmp;
    }

    public List<MonthBuyList> selectMonthPlace(String monthdt)
    {
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();
        sb.append(" SELECT distinct BUY_PLACE, substr(REQ_DT, 7, 2) FROM BUY_LIST ");
        sb.append("WHERE " + " REQ_DT like "+ "'" + "%" + monthdt + "%'");
        Log.e("selectPlace SQL", sb.toString());
        Cursor cursor = db.rawQuery(sb.toString(), null);

        List<MonthBuyList> li = new ArrayList<MonthBuyList>();
        String tmp = null;
        while( cursor.moveToNext() )
        {
            MonthBuyList mbl = new MonthBuyList();
            mbl.setBuyPlace(cursor.getString(0));
            mbl.setMonthdt(cursor.getString(1));
            Log.e("cursor", cursor.getString(0));
            li.add(mbl);
        }
        return li;
    }

    public boolean addReceiptList(String REQ_DT,String BUY_PLACE, int TOTAL_PRICE, String IMAGE_NUM){

        SQLiteDatabase db = this.getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        String sreq_dt = REQ_DT;
        String sbuy_place = BUY_PLACE;
        int    stotal_price = TOTAL_PRICE;
        String simage_num = IMAGE_NUM;



        sb.append(" INSERT INTO RECEIPT_LIST ( ");
        sb.append(" REQ_DT,  BUY_PLACE, TOTAL_PRICE ,IMAGE_NUM ) ");
        sb.append(" VALUES ( ? ,? ,? ,? ) ");

        Log.w("sreq_dt" , sreq_dt);
        Log.w("sbuy_place" , sbuy_place);
        Log.w("simage_num" , simage_num);

        db.execSQL(sb.toString(), new Object[]{
                sreq_dt
                ,sbuy_place
                ,stotal_price
                ,simage_num
        });

        db.close();

        return true;

    }

    public List<receiptList> selectReceiptList(String req_dt, String buy_place)
    {
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();

        sb.append(" SELECT SER_NUM , REQ_DT,  BUY_PLACE, TOTAL_PRICE ,IMAGE_NUM FROM RECEIPT_LIST ");
        sb.append(" WHERE '1'='1' ");
        if(!req_dt.equals("")){
            sb.append(" AND req_dt = " + "'" + req_dt + "'");
        }
        if(!buy_place.equals("전체")){
            sb.append(" AND BUY_PLACE = " + "'" + buy_place + "'");
        }
        Log.e("selectPlace SQL", sb.toString());
        Cursor cursor = db.rawQuery(sb.toString(), null);

        List<receiptList> li = new ArrayList<receiptList>();
        String tmp = null;
        while( cursor.moveToNext() )
        {
            receiptList mbl = new receiptList();
            mbl.setSer_num(Integer.parseInt(cursor.getString(0)));
            mbl.setReq_dt(cursor.getString(1));
            mbl.setBuy_place(cursor.getString(2));
            mbl.setTotal_price(Integer.parseInt(cursor.getString(3)));
            mbl.setImage_num(cursor.getString(4));

            li.add(mbl);
        }
        return li;
    }

    public void deletereceiptList(int ser_num)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM RECEIPT_LIST ");
        sb.append("WHERE " + " SER_NUM =" + ser_num);

        Log.e("DELETE SQL", sb.toString());
        db.execSQL(sb.toString());
        db.close();
    }


    public boolean addFavoritePlace(List<String> li){

        //deleteFavoriteItem();
        SQLiteDatabase db = this.getWritableDatabase();

        for(String fi : li){
            StringBuffer sb = new StringBuffer();
            //FavoriteItem fit = new FavoriteItem();
            // fit = fi[i];
            sb.append(" INSERT INTO FAVORITE_PLACE ( ");
            sb.append(" PLACE ) ");
            sb.append(" VALUES ( ? ) ");

            db.execSQL(sb.toString(), new Object[]{
                    fi
            });
        }

        db.close();

        return true;

    }

    public List getFavoritePlace()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT PLACE FROM FAVORITE_PLACE "); // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        List favoritePlacelist = new ArrayList();
        FavoriteItem favoriteitem = null; // moveToNext 다음에 데이터가 있으면 true 없으면 false
        while( cursor.moveToNext() )
        {
            favoritePlacelist.add(cursor.getString(0));
        }
        return favoritePlacelist;
    }

    public void deleteFavoritePlace()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM FAVORITE_PLACE "); // 읽기 전용 DB 객체를 만든다.
        db.execSQL(sb.toString());
        db.close();
    }

}