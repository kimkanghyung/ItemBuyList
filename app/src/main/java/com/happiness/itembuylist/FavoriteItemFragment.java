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
import android.view.KeyEvent;
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
import android.widget.ScrollView;
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
    ScrollView scrollviewbody;

    RadioButton bg2;
    RadioButton bg1;


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
        scrollviewbody = (ScrollView)mainview.findViewById(R.id.scrollviewbody);

        rg = (RadioGroup) mainview.findViewById(R.id.radiorg);




        bg2 = (RadioButton)mainview.findViewById(R.id.option2);
        bg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFavoriteList();

            }
        });

        bg1 = (RadioButton)mainview.findViewById(R.id.option1);
        bg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFavoritePlaceList();

            }
        });

        bg1.setChecked(true);

        TextView tmp3 = (TextView)mainview.findViewById(R.id.buyitemdelbt);
        tmp3.measure(0,0);
        dellength = tmp3.getMeasuredWidth();
        if(rg.getCheckedRadioButtonId() == R.id.option2){
            getFavoriteList();
        }else if(rg.getCheckedRadioButtonId() == R.id.option1){
            getFavoritePlaceList();
        }

        mainview.setFocusableInTouchMode(true);
        mainview.requestFocus();
        mainview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
                    alert_confirm.setMessage("종료하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 'YES'
                                   // Intent intent = new Intent(getActivity(),MainActivity.class);
                                  //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                  //  intent.putExtra("finishstatus", true);
                                  //  startActivity(intent);
                                    getActivity().finish();
                                }
                            }).setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 'No'
                                   // return;
                                }
                            });
                    AlertDialog alert = alert_confirm.create();
                    alert.show();

                    return true;
                } else {
                    return true;
                }
            }
        });

        return mainview;
    }

    @Override
    public void onResume() {
        bg1.setChecked(true);
        super.onResume();
    }

  private List getfavoriteitemlist(){

        return dbHelper.getFavoriteItem();


    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buylistaddbt){

            setterTablelayout("");
            scrollToEnd();
        }
        else if(view.getId() == R.id.buylistsavebt){
            Date d = new Date();

            String s = d.toString();
            List comparetmplist = new ArrayList<>();

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
                                if(!comparetmplist.contains(title)){
                                    comparetmplist.add(title);
                                }else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage("동일한 목록은 입력 안됩니다.");
                                    alert.show();
                                    comparetmplist.clear();
                                    return;
                                }
                            }
//                            Log.e("title" , title);

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
                                if(!comparetmplist.contains(title)){
                                    comparetmplist.add(title);
                                }else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage("동일한 목록은 입력 안됩니다.");
                                    alert.show();
                                    comparetmplist.clear();
                                    return;
                                }
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
            comparetmplist.clear();


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

    public void scrollToEnd(){
        scrollviewbody.post(new Runnable() {
            @Override
            public void run() {
                scrollviewbody.fullScroll(View.FOCUS_DOWN);
                // scrollviewbody.focus
                //  scrollviewbody.scrollTo(0, scrollviewbody.getBottom());
            }
        });
    }

}
