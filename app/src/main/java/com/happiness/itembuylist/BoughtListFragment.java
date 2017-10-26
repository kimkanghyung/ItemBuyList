package com.happiness.itembuylist;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.text.InputType.TYPE_CLASS_NUMBER;

/**
 * Created by kanghyun on 2017-09-27.
 */

public class BoughtListFragment extends Fragment{

    private TextView tvDate;
    private GridAdapter gridAdapter;
    private ArrayList<String> dayList;
    private GridView gridView;
    private Calendar mCal;

    private PopupWindow mpopupWindow;
    private int mWidthPixels, mHeightPixels;

    DBHelper dbHelper ;
    View poupView;
    TableLayout tbl;

    int placelength;
    int namelength;
    int pricelength;
    int dellength;

    Spinner spinnerplcae;
    Spinner spinner;
    int sumprice;
    Button addBt;
    ScrollView scrollviewbody;

    // 요일별도
    private GridView dayListView;
    private GridAdapter dayAdapter;
    private ArrayList<String> dayTopList;


    public BoughtListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        getActivity().setTitle("구매내역");
        View view = inflater.inflate(R.layout.viewboughtlist,container,false);
        tvDate = (TextView)view.findViewById(R.id.tv_date);
        gridView = (GridView)view.findViewById(R.id.gridview);

        dayListView = (GridView)view.findViewById(R.id.daylistview);




/*        String [] items = {"선택","emart","홈플","롯데","기타"};

        spinnerplcae = (Spinner)view.findViewById(R.id.spinnerplace);
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,items);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerplcae.setAdapter(aa2);
        //getBuyList();
        spinnerplcae.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //각 항목 클릭시 포지션값을 토스트에 띄운다.
                //getBuyList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/



        MainActivity mainActivity = (MainActivity)getActivity();
        dbHelper = mainActivity.dbHelper;


        dayList = new ArrayList<String>();
        dayTopList = new ArrayList<String>();



        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        long now = System.currentTimeMillis();
        final Date Nowdate = new Date(now);
        String scurYearFormat = curYearFormat.format(Nowdate);
        String scurMonthFormat = curMonthFormat.format(Nowdate);
        //현재 날짜 텍스트뷰에 뿌려줌
        Log.e("LOG","scurYearFormat = "+ scurYearFormat );
        Log.e("LOG","scurMonthFormat = "+ scurMonthFormat );

        if(scurYearFormat.length() == 1){
            scurYearFormat = "0" + scurYearFormat;
        }
        tvDate.setText(scurYearFormat + "년" + scurMonthFormat + "월");
        setSeachDate();







        view.findViewById(R.id.nextBt).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
               // tvDate.setText(scurYearFormat + "년" + scurMonthFormat + "월");
                addMonth(1);

            }
        });

        view.findViewById(R.id.preBt).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tvDate.setText(scurYearFormat + "년" + scurMonthFormat + "월");
                addMonth(-1);

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
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




        return view;

    }

    private List getFavoriteList(){

        List tmplist = new ArrayList();
        tmplist.add("구매물품");
        Log.e("ERROR","======count=======" + tmplist.size());
        if(dbHelper.getFavoriteItem().size() > 0){
            for(int i = 0; i < dbHelper.getFavoriteItem().size(); i ++)
            {
                Log.e("ERROR","======dbHelper.getFavoriteItem()=======" + dbHelper.getFavoriteItem().get(i).toString());
                tmplist.add(dbHelper.getFavoriteItem().get(i).toString());
            }
        }

        return tmplist;
    }

    private void init(){
        dayTopList.clear();
        dayList.clear();
        dayTopList.add("일");
        dayTopList.add("월");
        dayTopList.add("화");
        dayTopList.add("수");
        dayTopList.add("목");
        dayTopList.add("금");
        dayTopList.add("토");
        dayAdapter = new GridAdapter(getActivity().getApplicationContext(), dayTopList,null,false);
        dayListView.setAdapter(dayAdapter);
        dayListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return motionEvent.getAction() == MotionEvent.ACTION_MOVE;
            }
        });

    }

    private void addMonth(int tmp){

        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        DateFormat dfyear = new SimpleDateFormat("yyyy");
        DateFormat dfmonth = new SimpleDateFormat("MM");
        String Tyear = tvDate.getText().toString().substring(0,4);
        String Tmonth = tvDate.getText().toString().substring(5,7);
        String s = Tyear + "-" + Tmonth;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mCal.setTime(date);
        mCal.add(Calendar.MONTH, tmp);
        String tmpyear = dfyear.format(mCal.getTime());
        String tmpmonth = dfmonth.format(mCal.getTime());
        if(tmpmonth.length() == 1){
            tmpmonth = "0" + tmpmonth;
        }
        tvDate.setText(tmpyear + "년" + tmpmonth + "월");
        init();
        setSeachDate();
    }

    private void setSeachDate(){
        Log.e("==setSeachDate==","setSeachDate!!!!!!!!!");
        String Tyear = tvDate.getText().toString().substring(0,4);
        String Tmonth = tvDate.getText().toString().substring(5,7);

        mCal = Calendar.getInstance();
        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        List<MonthBuyList> tmplist = new ArrayList<MonthBuyList>();
        tmplist = dbHelper.selectMonthPlace(Tyear+Tmonth);
//        Log.e("==tmplist1==",tmplist.get(0).getBuyPlace());
//        Log.e("==tmplist2==",tmplist.get(1).getBuyPlace());
        Log.e("LOG","Tyear = "+ Tyear );
        Log.e("LOG","Tmonth = "+ Tmonth );

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        String s = Tyear + "-" + Tmonth + "-" + "01";
        try {
             date = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        mCal.setTime(date);

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        //1일 - 요일 매칭 시키기 위해 공백 add
        init();
        for (int i = 1; i < dayNum; i++) {

            dayList.add("");

        }
        setCalendarDate(mCal.get(Calendar.MONTH) + 1);
        //if(gridView.getChildCount() > 0 ) gridView.clear

        gridAdapter = new GridAdapter(getActivity().getApplicationContext(), dayList,tmplist,true);

        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                //Log.e("날짜 = ",selectedItem);
                if(!selectedItem.equals("")){
                    String Tyear = tvDate.getText().toString().substring(0,4);
                    String Tmonth = tvDate.getText().toString().substring(5,7);
                    if(selectedItem.length() ==1){
                        selectedItem = "0" + selectedItem;
                    }

                    settingPopup(Tyear + Tmonth + selectedItem);
                }




            }
        });

    }



    private void settingPopup(String str){
        String [] items = {"E.mart","H.plus","L.mart","ETC"};
        WindowManager w = getActivity().getWindowManager();
        Display d = w.getDefaultDisplay();
        Point realSize = new Point();
        final String senderDate = str;
        //senderDate        = str;

        try {
            Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        mWidthPixels = realSize.x;
        mHeightPixels = realSize.y;

       poupView = getActivity().getLayoutInflater().inflate(R.layout.popup_boughtlist,null);


        mpopupWindow = new PopupWindow(poupView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);

        //mpopupWindow.setFocusable(true);
        mpopupWindow.setTouchInterceptor(new View.OnTouchListener()
        {

            public boolean onTouch(View v, MotionEvent event)
            {
                Log.e("onTouch!!","event.getAction() = " + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                {
                    Log.e("onTouch!!","ACTION_OUTSIDE!");
                    setSeachDate();
                }

                return false;
            }
        });
        mpopupWindow.setOutsideTouchable(false);
        mpopupWindow.setTouchable(true);
        mpopupWindow.setFocusable(true);
      //  mpopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    //   mpopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
     //   mpopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
      //  mpopupWindow.setClippingEnabled(false);



        new Handler().postDelayed(new Runnable(){

            public void run() {
               // mpopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                mpopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                mpopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                mpopupWindow.showAtLocation(poupView, Gravity.CENTER, 0, 0);
            }

        }, 100L);


        spinner = (Spinner)poupView.findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getFavoriteList());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);





        // mpopupWindow.setAnimationStyle(0);
        //View poupView = getActivity().getLayoutInflater().inflate(R.layout.popup_boughtlist,null);
        //mpopupWindow = new PopupWindow(poupView,mWidthPixels - 100 , mHeightPixels - 500,true);
        //mpopupWindow.showAtLocation(poupView, Gravity.CENTER, 0, 0);
        /*테이블 2개 크기 맞추기 위해서 길이 구함. - 헤더와 바디 부분*/
        TextView tmp0 = (TextView)poupView.findViewById(R.id.buyitemplace);
        tmp0.measure(0,0);
        placelength = tmp0.getMeasuredWidth();
        Log.e("namelength","namelength = " + placelength);

        TextView tmp = (TextView)poupView.findViewById(R.id.buyitemname);
        tmp.measure(0,0);
        namelength = tmp.getMeasuredWidth();

        Log.e("namelength","namelength = " + namelength);

        TextView tmp2 = (TextView)poupView.findViewById(R.id.buyitemprice);
        tmp2.measure(0,0);
        pricelength = tmp2.getMeasuredWidth();
        Log.e("pricelength","pricelength = " + pricelength);

        TextView tmp3 = (TextView)poupView.findViewById(R.id.buyitemdelbt);
        tmp3.measure(0,0);
        dellength = tmp3.getMeasuredWidth();
        Log.e("dellength","dellength = " + dellength);

        TextView topdatetxt = (TextView)poupView.findViewById(R.id.topdatetxt);

        String tmpdate = senderDate.substring(0,4) + "." +senderDate.substring(4,6) + "." + senderDate.substring(6,8) ;
        topdatetxt.setText(tmpdate);

        ImageView closebt = (ImageView) poupView.findViewById(R.id.closebtn);
        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpopupWindow.dismiss();
                setSeachDate();
                //getBuyList(senderDate,parent.getSelectedItem().toString());
            }
        });

        /*
        Button closebt = (Button) poupView.findViewById(R.id.closebtn);
        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpopupWindow.dismiss();
                setSeachDate();
                //getBuyList(senderDate,parent.getSelectedItem().toString());
            }
        });
        */


        spinnerplcae = (Spinner)poupView.findViewById(R.id.spinnerplace);
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,dbHelper.getFavoritePlace());
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerplcae.setAdapter(aa2);

        spinnerplcae.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //각 항목 클릭시 포지션값을 토스트에 띄운다.
                Log.e("장소 = ",parent.getSelectedItem().toString());
                getBuyList(senderDate,parent.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addBt = (Button)poupView.findViewById(R.id.buylistaddbt);
        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tmp_place = null;
                if(spinnerplcae.getSelectedItem().toString().equals("구매장소")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("구매장소를 입력하세요.");
                    alert.show();
                    return;
                }else {
                    Tmp_place =spinnerplcae.getSelectedItem().toString();
                    String itm = spinner.getSelectedItem().toString();
                    Log.e("Tmp_place",Tmp_place);
                    if(!itm.equals("")){
                        if(!itm.equals("구매물품")){
                            setterTablelayout(Tmp_place,itm,0);
                        }else {
                            setterTablelayout(Tmp_place,"",0);
                        }
                    }

                }
                scrollToEnd();
            }
        });

        scrollviewbody = (ScrollView)poupView.findViewById(R.id.scrollviewbody);;

        Button savebt = (Button)poupView.findViewById(R.id.buylistsavebt);
        savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveBTevent(senderDate,spinnerplcae.getSelectedItem().toString());
                setSeachDate();
            }
        });
        Button cancelbt = (Button)poupView.findViewById(R.id.buylistcancelbt);
        cancelbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*취소 버튼 눌렀을 때*/
                tbl.removeAllViews();
                setSeachDate();
                mpopupWindow.dismiss();
            }
        });

    }

    private void getBuyList(String strdate ,String strplace){

        //View poupView = getActivity().getLayoutInflater().inflate(R.layout.popup_boughtlist,null);

        //Window

        Log.e("getBuyList = ","----------getBuyList---------");
        //TableLayout tbl = (TableLayout) poupView.findViewById(R.id.buylisttable);
        tbl = (TableLayout) poupView.findViewById(R.id.buylisttable);
        //tbl.cou
        tbl.removeAllViews();
        List<BuyItemList> tmplist = new ArrayList<BuyItemList>();
        String senderDate = strdate;
        String senderPlace = strplace;

        tmplist = dbHelper.getBuyListItem(senderDate , senderPlace);

        Log.e("ERROR","tmplist count" + tmplist.size());
        if(tmplist.size() > 0){
            for(int i = 0; i < tmplist.size(); i ++)
            {
                //Log.e("ERROR","======tmplist.get(i).toString()=======" + tmplist.get(i).toString());
                Log.e("getItem_nm","getItem_nm =" + tmplist.get(i).getItem_nm());
                Log.e("getPre_price","getPre_price=" + tmplist.get(i).getPre_price());

                setterTablelayout(tmplist.get(i).getBuypalce(),tmplist.get(i).getItem_nm(),tmplist.get(i).getPre_price());
            }
        }
        sumBuyPrice();

    }

    private void setterTablelayout(String place ,String item_nm,int pirce){

        Log.e("setterTablelayout = ","----------setterTablelayout---------");
        Log.e("item_nm = ",item_nm);
        //View poupView = getActivity().getLayoutInflater().inflate(R.layout.popup_boughtlist,null);

        final TableRow row = new TableRow(getActivity());

        EditText tv0 = new EditText(getActivity());
        EditText tv1 = new EditText(getActivity());
        final EditText tv2 = new EditText(getActivity());

        row.setPadding(0,5,0,0);
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT));

        //장소
        tv0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.textback));
        tv0.setGravity(Gravity.CENTER);
        if(!place.equals("")){
            tv0.setText(place);
        }else {
            tv0.setText("");
        }

        row.addView(tv0);
        TableRow.LayoutParams params0 = (TableRow.LayoutParams) tv0.getLayoutParams();
        params0.width = placelength;
       // params0.leftMargin = 10;
       // params0.rightMargin = 5;
        tv0.setLayoutParams(params0);

        //물품명
        tv1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.textback));
        tv1.setGravity(Gravity.CENTER);

        tv1.setMaxWidth(8);
        tv1.setText(item_nm);

        row.addView(tv1);
        TableRow.LayoutParams params = (TableRow.LayoutParams) tv1.getLayoutParams();
        //params.weight = 1;
        params.width = namelength;
       // params.rightMargin = 5;
        tv1.setLayoutParams(params);

        //예상가격
        tv2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.textback));
        tv2.setInputType(TYPE_CLASS_NUMBER);
        tv2.setHint("가격입력");
        tv2.setGravity(Gravity.CENTER);
        if(pirce != 0) {
            tv2.setText(Comma_won(String.valueOf(pirce)));
        }else {
            tv2.setText("");
        }

        tv2.addTextChangedListener(new TextWatcher(){
            String strAmount = ""; // 임시저장값 (콤마)
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals("")){
                    sumBuyPrice();
                }else {
                    if (!s.toString().equals(strAmount)) { // StackOverflow를 막기위해,

                        if(strAmount.length() <= 9){
                            strAmount = Comma_won(s.toString().replace(",", ""));
                            tv2.setText(strAmount);
                            Editable e = tv2.getText();
                            Selection.setSelection(e, strAmount.length());
                            sumBuyPrice();
                        }
                        else if(strAmount.length() > 9){

                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("숫자가 너무 큽니다.");
                            alert.show();
                            tv2.setText(strAmount);
                            return;

                        }


                    }
                }

            }
        });
        //tv2.setWidth();
        row.addView(tv2);

        TableRow.LayoutParams params2 = (TableRow.LayoutParams) tv2.getLayoutParams();
        //params2.weight = 1;
        params2.width = pricelength;
       // params2.rightMargin = 10;
        tv2.setLayoutParams(params2);
/*
        tv2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){ // 뷰의 id를 식별, 키보드의 완료 키 입력 검출

                    sumBuyPrice();

                }
                return false;
            }
        });
*/
        //삭제버튼
        ImageView deletebt = new ImageView(getActivity());
        deletebt.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_indeterminate_check_box_black_24dp));

        row.addView(deletebt);

        TableRow.LayoutParams params3 = (TableRow.LayoutParams) deletebt.getLayoutParams();
        params3.width = (int) getResources().getDimension(R.dimen.imagebt_height);
        params3.height = (int) getResources().getDimension(R.dimen.imagebt_height);
        //params3.rightMargin = 15;
        deletebt.setLayoutParams(params3);

        deletebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbl.removeView(row);
                sumBuyPrice();
            }
        });

        tbl.addView(row, new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
       // spinner.setSelection(0);
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

    public String Comma_won(String price) {
        int inValues = Integer.parseInt(price);
        DecimalFormat Commas = new DecimalFormat("###,###,###.####");
        String result_int = (String)Commas.format(inValues);
        return result_int;
    }

    private void sumBuyPrice(){

        sumprice = 0;

        for (int i = 0; i < tbl.getChildCount(); i++) {
            View child = tbl.getChildAt(i);
            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;

                for (int x = 0; x < row.getChildCount(); x++) {
                    TextView text = null;
                    String title = null;
                    if(x == 1 || x == 2){
                        text = (TextView)row.getChildAt(x); // get child index on particular row
                        title = text.getText().toString();
                    }
                    if(x == 2){
                        if(title.equals("")){
                            title = "0";
                        }
                        sumprice = sumprice + Integer.parseInt(title.replaceAll(",",""));
                    }
                }
                /*
                for (int x = 0; x < row.getChildCount(); x++) {
                    TextView text = null;
                    String title = null;
                    if(x == 0 || x == 1){
                        text = (TextView)row.getChildAt(x); // get child index on particular row
                        title = text.getText().toString();
                    }
                    if(x % 3 == 1){
                        if(title.equals("")){
                            title = "0";
                        }
                        sumprice = sumprice + (Integer.parseInt(title));
                    }
                }
                */

            }
        }
        Log.e("합계 = ", String.valueOf(sumprice) );
        TextView edittxt = (TextView) poupView.findViewById(R.id.sumtext);
        edittxt.setText(String.valueOf(Comma_won(String.valueOf(sumprice))));
    }


    private void SaveBTevent(String strdate ,String strplace){
        List<BuyItemList> li = new ArrayList<BuyItemList>();
        List comparetmplist = new ArrayList<>(); /*동일 품목이 들어잇는지 체크*/


        dbHelper.deleteBuyListItem(strdate, strplace);
        for (int i = 0; i < tbl.getChildCount(); i++) {
            View child = tbl.getChildAt(i);
            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;

                BuyItemList bil = new BuyItemList();

                for (int x = 0; x < row.getChildCount(); x++) {
                    //View view = row.getChildAt(x);
                    TextView text = null;
                    String title = null;
                    if(x == 1 || x == 2){
                        text = (TextView)row.getChildAt(x); // get child index on particular row
                        title = text.getText().toString();
                    }

                    if(x == 1){
                        bil.setItem_nm(title);

                        if(title.equals("")){
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
                            alert.setMessage("동일 구매물품 입력은 안됩니다.");
                            alert.show();
                            return;
                        }

                    }else if(x == 2){
                        if(title.equals("")){
                            title = "0";
                        }

                        if(title.length() > 9){
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("이것은 서민의 장바구니입니다. 10억 이상은 입력할 수 없습니다.");
                            alert.show();
                            return;
                        }
                        bil.setPre_price(Integer.parseInt(title.replaceAll(",","")));
                    }else if(x == 3){
                        bil.setBuypalce(strplace);
                        bil.setReq_dt(strdate);
                        li.add(bil);
                    }

                }


            }
        }/*저장 버튼 눌렀을 때*/
        comparetmplist.clear();
        boolean resulte = dbHelper.addBuyListItem(li);
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


    private void setCalendarDate(int month) {

        mCal.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            dayList.add("" + (i + 1));

        }

    }

    private class GridAdapter extends BaseAdapter {

        private final List<String> list;
        private final LayoutInflater inflater;
        private List<MonthBuyList> lmbl;
        private boolean flag;


        public GridAdapter(Context context, List<String> list,List<MonthBuyList> lmbl,boolean flag) {

            this.list = list;
            this.lmbl = lmbl;
            this.flag = flag;

            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }



        @Override
        public int getCount() {

            return list.size();

        }

        @Override
        public String getItem(int position) {

            return list.get(position);

        }

        @Override
        public long getItemId(int position) {

            return position;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            ViewHolder holder = null;



            if (convertView == null ) {
                if(flag == true){
                    convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
                    holder = new ViewHolder();
                    holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);
                }else {
                    convertView = inflater.inflate(R.layout.itm_calendar_head, parent, false);
                    holder = new ViewHolder();
                    holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);

                }

                //holder.tvemart = (TextView)convertView.findViewById(R.id.emarticon);
                //holder.tvlotte = (TextView)convertView.findViewById(R.id.lottemarticon);
                //holder.tvhome = (TextView)convertView.findViewById(R.id.homeicon);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder)convertView.getTag();
                return convertView;

            }

            //convertView.re




            holder.tvItemGridView.setText("" + getItem(position));



            //해당 날짜 텍스트 컬러,배경 변경

            mCal = Calendar.getInstance();

            //오늘 day 가져옴

            Integer today = mCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);

            Integer todaymonth = mCal.get(Calendar.MONTH);
            todaymonth = todaymonth + 1;
            String sTodayMonth = String.valueOf(todaymonth);

            if(sTodayMonth.length() == 1){
                sTodayMonth = "0" + sTodayMonth;
            }

            if (sToday.equals(getItem(position)) && tvDate.getText().toString().substring(5,7).equals(sTodayMonth)) { //오늘 day 텍스트 컬러 변경

                holder.tvItemGridView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_flatgrey));
                LinearLayout mRootLinear = (LinearLayout) convertView.findViewById(R.id.linear);
                mRootLinear.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_flatgrey));

            }

            List<String> tmpStirng = new ArrayList<String>();
            String Tmpdate = null;



            if(flag == true){

                    if(lmbl.size() > 0){

                        for(MonthBuyList tmp : lmbl){

                            if(getItem(position).length() > 0  ){
                              //  Log.e("==Monthdt==",tmp.getMonthdt());
                              //  Log.e("==getItem==",getItem(position));
                                if(Integer.parseInt(getItem(position)) == Integer.parseInt(tmp.getMonthdt())) {
                                 //   Log.e("==getViewgetBuyPlace==",tmp.getBuyPlace());
                                    Log.e("==getViewgetMonthdt==",tmp.getMonthdt());
                                    Log.e("==getItem(position)==",getItem(position));
                                 //   Log.e("==position==",String.valueOf(position));
                                    Tmpdate = tmp.getMonthdt();
                                    if(!tmpStirng.contains(tmp.getBuyPlace())){
                                        tmpStirng.add(tmp.getBuyPlace());
                                    }

                                }
                            }
                        }

                }

                if(position % 7 == 0){
                    holder.tvItemGridView.setTextColor(Color.rgb(211,77,12));
                }else if(position % 7 == 6){
                    holder.tvItemGridView.setTextColor(Color.rgb(254,100,1));
                }

                if(tmpStirng.size()>0 && Tmpdate != null){
                    Log.e("==Tmpdate==",Tmpdate);
                    Log.e("==tmpStirng.size()==","" + tmpStirng.size());

                    // holder.tvemart.setVisibility(View.VISIBLE);
                    if(Integer.parseInt(getItem(position)) == Integer.parseInt(Tmpdate)){
                        for(String Tmpplace : tmpStirng){
                            setCalendarbuylist(convertView,Tmpplace);
                        }
                    }

                }
            }else {
                // 텍스트색상
                holder.tvItemGridView.setTextColor(Color.rgb(31,31,31));
                // 텍스트위치(중간)
                holder.tvItemGridView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                // Height고정
                //LinearLayout mRootLinear3 = (LinearLayout) convertView.findViewById(R.id.linear);
                //ViewGroup.LayoutParams params3 = mRootLinear3.getLayoutParams();
                //params3.height = 35;
                //mRootLinear3.setLayoutParams(params3);
            }

            if(tmpStirng.size()>0){
                tmpStirng.clear();
            }





            return convertView;

        }

        private void setCalendarbuylist(View v,String s){
            String wherebuy = null;
            wherebuy = s;
            LayoutInflater mInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout mRootLinear = (LinearLayout) v.findViewById(R.id.linear);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            //params.height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());

            TextView tmp  = new TextView(getActivity());
            //tmp.setText(wherebuy);
            if(s.equals("E.mart")){
                tmp.setText("E.mart");
                tmp.setGravity(Gravity.CENTER);
                tmp.setBackgroundResource(R.drawable.tv_rounded_corner);
                GradientDrawable drawable1 = (GradientDrawable) tmp.getBackground();
                drawable1.setColor(Color.rgb(255,216,0));
                tmp.setTextColor(Color.WHITE);
            }else if(s.equals("H.plus")){
                tmp.setText("H.plus");
                tmp.setGravity(Gravity.CENTER);
                tmp.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tv_rounded_corner));
                GradientDrawable drawable2 = (GradientDrawable) tmp.getBackground();
                drawable2.setColor(Color.rgb(4,176,167));
                tmp.setTextColor(Color.WHITE);
            }else if(s.equals("L.mart")){
                tmp.setText("L.mart");
                tmp.setGravity(Gravity.CENTER);
                tmp.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tv_rounded_corner));
                GradientDrawable drawable3 = (GradientDrawable) tmp.getBackground();
                drawable3.setColor(Color.rgb(248,98,75));
                tmp.setTextColor(Color.WHITE);
            }else {
                if(s.length() < 5){
                    tmp.setText(s.substring(0,s.length()));
                }else {
                    tmp.setText(s.substring(0,4));
                }

                tmp.setGravity(Gravity.CENTER);
                tmp.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tv_rounded_corner));
                GradientDrawable drawable4 = (GradientDrawable) tmp.getBackground();
                drawable4.setColor(Color.rgb(89,143,220));
                tmp.setTextColor(Color.WHITE);
            }

            tmp.setTextSize(TypedValue.COMPLEX_UNIT_PT,7);

            //tmp.setHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics()));
            LinearLayout.LayoutParams textlayoutparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            textlayoutparam.height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());
            tmp.setLayoutParams(textlayoutparam);
            tmp.setGravity(Gravity.CENTER);

            List<String> compare = new ArrayList<String>();

            for (int i = 0; i < mRootLinear.getChildCount(); i++) {
                View child = mRootLinear.getChildAt(i);
                if (child instanceof TextView) {
                    TextView tvtmp = (TextView) child;
                    compare.add(tvtmp.getText().toString());
                    //if(tvtmp.getText().toString().equals(tmp.getTe)

                }
            }

            if(!compare.contains(tmp.getText().toString())) mRootLinear.addView(tmp);

            compare.clear();


        }





    }



    private class ViewHolder {

        TextView tvItemGridView;
        TextView tvemart;
        TextView tvlotte;
        TextView tvhome;
        TextView tvetc;

    }

}
