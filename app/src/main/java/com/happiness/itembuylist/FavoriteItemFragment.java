package com.happiness.itembuylist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;

/**
 * Created by kanghyun on 2017-10-02.
 */

public class FavoriteItemFragment extends Fragment implements View.OnClickListener {
    TableLayout tbl;
    Button addBt;
    int namelength;
    int dellength;

    Button savebt;
    Button cancelbt;

    List addlist;
    DBHelper dbHelper ;
    RadioGroup rg;



    //View mainview

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("즐겨찾기");

        View mainview = inflater.inflate(R.layout.viewfavoriteitem, container, false);
        TableRow row = new TableRow(getActivity()); // new row
        //View table_row_view = view.inflate(getActivity(),R.layout.viewbuylist,row); // inflate from parent view;
        addBt = (Button)mainview.findViewById(R.id.buylistaddbt);
        tbl = (TableLayout) mainview.findViewById(R.id.buylisttable); // init table
        addBt.setOnClickListener(this);

        savebt = (Button)mainview.findViewById(R.id.buylistsavebt);
        savebt.setOnClickListener(this);
        cancelbt = (Button)mainview.findViewById(R.id.buylistcancelbt);
        cancelbt.setOnClickListener(this);
        /*테이블 2개 크기 맞추기 위해서 길이 구함. - 헤더와 바디 부분*/
        TextView tmp = (TextView)mainview.findViewById(R.id.buyitemname);
        tmp.measure(0,0);
        namelength = tmp.getMeasuredWidth();
        tbl.removeAllViews();
        //dbHelper = new DBHelper(getActivity().getApplicationContext(), "Itemlist.db", null, 1);
          MainActivity mainActivity = (MainActivity)getActivity();
        dbHelper = mainActivity.dbHelper;
      //  dbHelper = new DBHelper(getActivity().getApplicationContext(), "Itemlist.db", null, 1);
        //dbHelper = new DBHelper(getActivity().getApplicationContext(), "Itemlist.db", null, 1);
        //
        addlist = new ArrayList();

        rg = (RadioGroup) mainview.findViewById(R.id.radiorg) ;

        RadioButton bg2 = (RadioButton)mainview.findViewById(R.id.option2);
        bg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFavoriteList();

            }
        });

        RadioButton bg1 = (RadioButton)mainview.findViewById(R.id.option1);
        bg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFavoritePlaceList();

            }
        });



        TextView tmp3 = (TextView)mainview.findViewById(R.id.buyitemdelbt);
        tmp3.measure(0,0);
        dellength = tmp3.getMeasuredWidth();
        getFavoriteList();

        return mainview;
    }

  private List getfavoriteitemlist(){

        return dbHelper.getFavoriteItem();


    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buylistaddbt){

            setterTablelayout("");
        }
        else if(view.getId() == R.id.buylistsavebt){
            Date d = new Date();

            String s = d.toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
            System.out.println("현재날짜 : "+ sdf.format(d) + sdf2.format(d));
            String datetmp = sdf.format(d) + sdf2.format(d) + sdf3.format(d);
            if(rg.getCheckedRadioButtonId() == R.id.option2){

                List<FavoriteItem> li = new ArrayList<FavoriteItem>();
                for (int i = 0; i < tbl.getChildCount(); i++) {
                    View child = tbl.getChildAt(i);
                    if (child instanceof TableRow) {
                        TableRow row = (TableRow) child;

                        for (int x = 0; x < row.getChildCount(); x++) {
                            //View view = row.getChildAt(x);
                            TextView text = null;
                            String title = null;
                            if(x == 0 ){
                                text = (TextView)row.getChildAt(x); // get child index on particular row
                                title = text.getText().toString();
                                if(title.equals("")) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage("구매물품을 입력하세요.");
                                    alert.show();
                                    return;
                                }
                            }
                            FavoriteItem fi = new FavoriteItem();
                            if(x == 0 ){
                                fi.setItem_nm(title);
                                fi.setReq_dt(datetmp);
                                li.add(fi);
                            }
                        }

                    }
                }/*저장 버튼 눌렀을 때*/
                dbHelper.deleteFavoriteItem();
                boolean resulte = dbHelper.addFavoriteItem(li);
                if(resulte){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("정상처리 되었습니다.");
                    alert.show();

                }
            }else if(rg.getCheckedRadioButtonId() == R.id.option1){


                List<String> li = new ArrayList<String>();
                for (int i = 0; i < tbl.getChildCount(); i++) {
                    View child = tbl.getChildAt(i);
                    if (child instanceof TableRow) {
                        TableRow row = (TableRow) child;

                        for (int x = 0; x < row.getChildCount(); x++) {
                            //View view = row.getChildAt(x);
                            TextView text = null;
                            String title = null;
                            if(x == 0 ){
                                text = (TextView)row.getChildAt(x); // get child index on particular row
                                title = text.getText().toString();
                                if(title.equals("")) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage("장소를 입력하세요.");
                                    alert.show();
                                    return;
                                }
                                li.add(title);
                            }

                        }

                    }
                }/*저장 버튼 눌렀을 때*/
                dbHelper.deleteFavoritePlace();
                boolean resulte = dbHelper.addFavoritePlace(li);
                if(resulte){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("정상처리 되었습니다.");
                    alert.show();

                }

            }


        } else if (view.getId() == R.id.buylistcancelbt) {
            /*취소 버튼 눌렀을 때*/
            //tbl.removeAllViews();
            if(rg.getCheckedRadioButtonId() == R.id.option2){
                getFavoriteList();
            }else if(rg.getCheckedRadioButtonId() == R.id.option1){
                getFavoritePlaceList();
            }


        }
    }

    private void getFavoriteList(){
        tbl.removeAllViews();
        List tmplist = new ArrayList();
        tmplist = getfavoriteitemlist();
        Log.e("ERROR","======count=======" + tmplist.size());
        if(tmplist.size() > 0){
            for(int i = 0; i < tmplist.size(); i ++)
            {
                Log.e("ERROR","======tmplist.get(i).toString()=======" + tmplist.get(i).toString());
                setterTablelayout(tmplist.get(i).toString());
            }
        }
    }

    private void getFavoritePlaceList(){
        tbl.removeAllViews();
        List tmplist = new ArrayList();
        tmplist = dbHelper.getFavoritePlace();
        Log.e("ERROR","======count=======" + tmplist.size());
        if(tmplist.size() > 0){
            for(int i = 0; i < tmplist.size(); i ++)
            {
                Log.e("ERROR","======tmplist.get(i).toString()=======" + tmplist.get(i).toString());
                setterTablelayout(tmplist.get(i).toString());
            }
        }
    }


    private void setterTablelayout(String itemname){

        System.out.println("=======buylistaddbt=======");
        final TableRow row = new TableRow(getActivity());

        EditText tv1 = new EditText(getActivity());
        row.setPadding(0,5,0,0);
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT));

        tv1.setText(itemname);


        tv1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.textback));
        row.addView(tv1);
        TableRow.LayoutParams params = (TableRow.LayoutParams) tv1.getLayoutParams();
        params.weight = 1;
        params.leftMargin = 30;
        params.rightMargin = 20;
        tv1.setLayoutParams(params);


        ImageView deletebt = new ImageView(getActivity());
        deletebt.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_indeterminate_check_box_black_24dp));

        row.addView(deletebt);

        TableRow.LayoutParams params3 = (TableRow.LayoutParams) deletebt.getLayoutParams();
        params3.width = 48;
        params3.height = 48;
        params3.rightMargin = 30;
        deletebt.setLayoutParams(params3);

        deletebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbl.removeView(row);
            }
        });

        tbl.addView(row, new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    }

}
