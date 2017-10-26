package com.happiness.itembuylist;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_VARIATION_NORMAL;

/**
 * Created by kanghyun on 2017-09-27.
 */

public class BuylistFrament extends Fragment implements View.OnClickListener {

    TableLayout tbl;
    Button addBt;
    int placelength;
    int namelength;
    int pricelength;
    int dellength;

    Button savebt;
    Button cancelbt;
    Spinner spinner;
    Spinner spinnerplcae;
    DBHelper dbHelper ;
    List favoritelist;

    String datetmp;
    boolean gubun;

    int sumprice;

    private TextView dateedit;/*날짜*/
    int year ;
    int month ;
    int day;
    TextView edittxt;

    ScrollView scrollviewbody;

    public BuylistFrament(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        getActivity().setTitle("장바구니");
        View view = inflater.inflate(R.layout.viewbuylist,container,false);
        TableRow row = new TableRow(getActivity()); // new row

        gubun = false;

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
        System.out.println("현재날짜 : "+ sdf.format(d) + sdf2.format(d));
        datetmp = sdf.format(d) + sdf2.format(d) + sdf3.format(d);



        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        edittxt = (TextView) view.findViewById(R.id.sumtext);

        //View table_row_view = view.inflate(getActivity(),R.layout.viewbuylist,row); // inflate from parent view;
        addBt = (Button)view.findViewById(R.id.buylistaddbt);
        tbl = (TableLayout) view.findViewById(R.id.buylisttable); // init table
        addBt.setOnClickListener(this);

        savebt = (Button)view.findViewById(R.id.buylistsavebt);
        savebt.setOnClickListener(this);
        cancelbt = (Button)view.findViewById(R.id.buylistcancelbt);
        cancelbt.setOnClickListener(this);

        dateedit = (TextView) view.findViewById(R.id.dateedit);
        datetmp =datetmp.substring(0,4) + "." +datetmp.substring(4,6) + "." + datetmp.substring(6,8) ;
        dateedit.setText(datetmp);
      /*  dateedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("onTouch","motionEvent.getAction() = " + motionEvent.getAction());
                Log.e("MotionEvent.ACTION_UP","MotionEvent.ACTION_UP = " + MotionEvent.ACTION_UP);
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    new DatePickerDialog(getContext(), dateSetListener, year, month, day).show();
                return false;
            }
        });
*/
        dateedit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    new DatePickerDialog(getContext(), dateSetListener, year, month, day).show();
                return false;
            }
        });

        MainActivity mainActivity = (MainActivity)getActivity();
        dbHelper = mainActivity.dbHelper;
        //String [] items = {"구매장소","E.mart","H.plus","L.mart","ETC"};

        // 구매장소
        spinnerplcae = (Spinner)view.findViewById(R.id.spinnerplace);
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,dbHelper.getFavoritePlace());
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerplcae.setAdapter(aa2);

        //getBuyList();
        spinnerplcae.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //각 항목 클릭시 포지션값을 토스트에 띄운다.
                // spinnerplcae.get
                getBuyList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // 구매물품
        spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getFavoriteList());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        /*테이블 2개 크기 맞추기 위해서 길이 구함. - 헤더와 바디 부분*/

        TextView tmp0 = (TextView)view.findViewById(R.id.buyitemplace);
        tmp0.measure(0,0);
        placelength = tmp0.getMeasuredWidth();
        Log.e("namelength","namelength = " + placelength);

        TextView tmp = (TextView)view.findViewById(R.id.buyitemname);
        tmp.measure(0,0);
        namelength = tmp.getMeasuredWidth();

        Log.e("namelength","namelength = " + namelength);

        TextView tmp2 = (TextView)view.findViewById(R.id.buyitemprice);
        tmp2.measure(0,0);
        pricelength = tmp2.getMeasuredWidth();
        Log.e("pricelength","pricelength = " + pricelength);

        TextView tmp3 = (TextView)view.findViewById(R.id.buyitemdelbt);
        tmp3.measure(0,0);
        dellength = tmp3.getMeasuredWidth();
        Log.e("dellength","dellength = " + dellength);
        scrollviewbody = (ScrollView)view.findViewById(R.id.scrollviewbody);

        init();
        getBuyList();

        gubun = true;

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

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            String Tmonth = "";
            String Tday = "";

            if(monthOfYear+1 < 10 ){
                Tmonth ="0"+Integer.toString(monthOfYear+1);
                //(monthOfYear+1)
            }else {
                Tmonth = Integer.toString(monthOfYear+1);
            }
            if(dayOfMonth < 10 ){
                Tday ="0"+Integer.toString(dayOfMonth);
                //(monthOfYear+1)
            }else{
                Tday = Integer.toString(dayOfMonth);
            }

            String msg = String.format("%d%s%s", year,Tmonth, Tday);
            msg =msg.substring(0,4) + "." +msg.substring(4,6) + "." + msg.substring(6,8) ;

            dateedit.setText(msg);

        }


    };


    private List getFavoriteList(){

        List tmplist = new ArrayList();
        tmplist.add("구매물품");
        if(dbHelper.getFavoriteItem().size() > 0){
            for(int i = 0; i < dbHelper.getFavoriteItem().size(); i ++)
            {
                Log.e("ERROR","======dbHelper.getFavoriteItem()=======" + dbHelper.getFavoriteItem().get(i).toString());
                tmplist.add(dbHelper.getFavoriteItem().get(i).toString());
            }
        }

        return tmplist;
    }

    private List getFavoritPlaceeList(){

        List tmplist = new ArrayList();
        tmplist.add("구매장소");
        if(dbHelper.getFavoritePlace().size() > 0){
            for(int i = 0; i < dbHelper.getFavoritePlace().size(); i ++)
            {
                Log.e("ERROR","======dbHelper.getFavoriteItem()=======" + dbHelper.getFavoritePlace().get(i).toString());
                tmplist.add(dbHelper.getFavoritePlace().get(i).toString());
            }
        }

        return tmplist;
    }

    private void init(){
      /*  String tmp = dbHelper.selectPlace(datetmp);
        if(tmp != null){
            if(tmp.equals("E.mart")) {
                spinnerplcae.setSelection(1);
            }else if(tmp.equals("H.plus")){
                spinnerplcae.setSelection(2);
            }else if(tmp.equals("L.mart")){
                spinnerplcae.setSelection(3);

            }else if(tmp.equals("ETC")){
                spinnerplcae.setSelection(4);
            }
        }*/

    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buylistaddbt){

            System.out.println("=======buylistaddbt=======");
            List<BuyItemList> tmplist = new ArrayList<BuyItemList>();
            String Tmp_place = null;
            if(spinnerplcae.getSelectedItem().toString().equals("구매장소")){
                //setterTablelayout("","",0);
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
                setterTablelayout(Tmp_place,"",0);
            }
            scrollToEnd();


        }
        else if(view.getId() == R.id.buylistsavebt){
            /*저장 버튼 눌렀을 때*/
            List<PreBuyItemList> li = new ArrayList<PreBuyItemList>();
            List comparetmplist = new ArrayList<>(); /*동일 품목이 들어잇는지 체크*/




            for (int i = 0; i < tbl.getChildCount(); i++) {
                View child = tbl.getChildAt(i);
                if (child instanceof TableRow) {
                    TableRow row = (TableRow) child;

                    PreBuyItemList bil = new PreBuyItemList();

                    for (int x = 0; x < row.getChildCount(); x++) {
                        //View view = row.getChildAt(x);
                        TextView text = null;
                        String title = null;
                        /*2017.10.13 추가*/
                        if(x == 0){
                            CheckBox cb = (CheckBox)row.getChildAt(x);
                            if(cb.isChecked()) bil.setBuy_yn("Y");
                            else  bil.setBuy_yn("N");
                        }

                        if(x == 2 || x == 3){ // 물품명, 예상가격
                            text = (TextView)row.getChildAt(x); // get child index on particular row
                            title = text.getText().toString();
                        }

                        // 물품명
                        if(x == 2){
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

                        }
                        // 예상가격
                        else if(x == 3){
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
                        }
                        // 장소
                        else if(x == 1){
                            bil.setBuypalce(spinnerplcae.getSelectedItem().toString());
                            li.add(bil);
                        }

                    }
                 /* boolean chkbool =   dbHelper.checkPreBuyListItem(bil.getBuypalce(),bil.getItem_nm(),bil.getBuy_yn());
                    if(chkbool){
                        dbHelper.updatePreBuyListItem(bil.getBuypalce(),bil.getItem_nm(),bil.getBuy_yn());
                    }else {

                    }
                   */
                    /*2017.10.13 insert부분 추가*/
                    if(bil.getBuy_yn().equals("Y")){

                        dbHelper.addBuyListItem2(dateedit.getText().toString().replace(".",""), bil.getItem_nm(), bil.getBuypalce(),  bil.getPre_price()  ,"Y");

                    }


                }
            }
            /*저장 버튼 눌렀을 때*/
            dbHelper.deletePreBuyListItem(spinnerplcae.getSelectedItem().toString());
            comparetmplist.clear();
            boolean resulte = dbHelper.addPreBuyListItem(li);
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
                getBuyList(); /*재조회*/
            }

        } else if (view.getId() == R.id.buylistcancelbt) {
            /*취소 버튼 눌렀을 때*/
            tbl.removeAllViews();
            getBuyList();

        }
    }

    private void getBuyList(){
        tbl.removeAllViews();
        /*2017.10.13 수정*/
        List<PreBuyItemList> tmplist = new ArrayList<PreBuyItemList>();
        Date d = new Date();

        String s = d.toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
        System.out.println("현재날짜 : "+ sdf.format(d) + sdf2.format(d));
        String datetmp = sdf.format(d) + sdf2.format(d) + sdf3.format(d);

        /*2017.10.13 수정*/
        tmplist = dbHelper.getPreBuyListItem(spinnerplcae.getSelectedItem().toString(),"N");
        sumBuyPrice();

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


        final TableRow row = new TableRow(getActivity());

        CheckBox box = new CheckBox(getActivity());
        EditText tv0 = new EditText(getActivity());
        EditText tv1 = new EditText(getActivity());
        final EditText tv2 = new EditText(getActivity());

        row.setPadding(0,0,0,0);
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT));


        box.setChecked(false);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                //what happens when the it's unchecked or checked

            }
        });
        row.addView(box);

        //장소
        tv0.setText("");
        tv0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.textback));
        tv0.setGravity(Gravity.CENTER);

        if(!place.equals("")){
            tv0.setText(place);
        }else {
            tv0.setText("");
        }

        row.addView(tv0);
        TableRow.LayoutParams params0 = (TableRow.LayoutParams) tv0.getLayoutParams();
        //params0.weight = 3;
        params0.width = placelength
        ;
        //params0.span = 3;
        params0.leftMargin = 15;
        params0.rightMargin = 5;
        tv0.setLayoutParams(params0);


        //tv2.getMeasuredWidth();
        Log.e(" tv0.getMeasuredWidth()"," tv0.getMeasuredWidth() = " +  tv0.getMeasuredWidth());

        //물품명
        tv1.setText("");
        tv1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.textback));
        tv1.setGravity(Gravity.CENTER);
        if(spinner.getSelectedItem().toString() == "구매물품") {
            tv1.setHint("물품입력");
        }else{
            tv1.setText(spinner.getSelectedItem().toString());
        }
        if(!item_nm.equals("")){
            tv1.setText(item_nm);
        }
        tv1.setMaxWidth(8);

        row.addView(tv1);
        TableRow.LayoutParams params = (TableRow.LayoutParams) tv1.getLayoutParams();
        //params.weight = 5;
        params.width = namelength;
        //params.span = 5;
        params.rightMargin = 5;
        tv1.setLayoutParams(params);

        tv1.getMeasuredWidth();
        Log.e(" tv1.getMeasuredWidth()"," tv1.getMeasuredWidth() = " +  tv1.getMeasuredWidth());

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

        row.addView(tv2);

        TableRow.LayoutParams params2 = (TableRow.LayoutParams) tv2.getLayoutParams();
        //params2.weight = 4;
        params2.width = pricelength;
        //params2.span = 4;
        params2.rightMargin = 10;
        tv2.setLayoutParams(params2);

       /* tv2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){ // 뷰의 id를 식별, 키보드의 완료 키 입력 검출

                    sumBuyPrice();

                }
                return false;
            }
        });
        */
        //tv2.getMeasuredWidth();
        //Log.e(" tv2.getMeasuredWidth()"," tv2.getMeasuredWidth() = " +  tv2.getMeasuredWidth());

        //삭제버튼
        ImageView deletebt = new ImageView(getActivity());
        deletebt.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_indeterminate_check_box_black_24dp));

        row.addView(deletebt);

        TableRow.LayoutParams params3 = (TableRow.LayoutParams) deletebt.getLayoutParams();
        //float scale = getContext().getResources().getDisplayMetrics().density;
        //int dp = (int) ()
        //int a = (int) getResources().getDimension(R.dimen.imagebt_width);
        params3.width = (int) getResources().getDimension(R.dimen.imagebt_width);
        params3.height = (int) getResources().getDimension(R.dimen.imagebt_height);
        //params3.span = 1;
        //params3.rightMargin = 15;
        deletebt.setLayoutParams(params3);

        deletebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbl.removeView(row);
                sumBuyPrice();
            }
        });


        //tbl.setColumnStretchable(3,false);
        tbl.addView(row, new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        // tbl.setColumnStretchable(0,true);
        //tbl.setColumnStretchable(1,true);



        spinner.setSelection(0);
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
                    if(x == 2 || x == 3){ // 물품명, 예상가격
                        text = (TextView)row.getChildAt(x); // get child index on particular row
                        title = text.getText().toString();
                    }
                    // 예상가격
                    if(x == 3){
                        if(title.equals("")){
                            title = "0";
                        }
                        sumprice = sumprice + Integer.parseInt(title.replaceAll(",",""));
                    }
                }


            }
        }
        Log.e("합계 = ", String.valueOf(sumprice) );

        edittxt.setText(String.valueOf(Comma_won(String.valueOf(sumprice))));
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


