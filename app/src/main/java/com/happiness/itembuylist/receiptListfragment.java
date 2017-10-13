package com.happiness.itembuylist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by kanghyun on 2017-10-08.
 */

public class receiptListfragment  extends Fragment {

    DBHelper dbHelper ;
    private ArrayList<Custom_List_Data> Array_Data;
    private Custom_List_Data data;
    private Custom_List_Adapter adapter;
    ListView custom_list;
    public Fragment receiptImageView;


    private TextView dateedit;/*날짜*/
    int year ;
    int month ;
    int day;
    Spinner spinnerplcae;
    Button searchallbt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        getActivity().setTitle("영수증");
        View view = inflater.inflate(R.layout.receipt_list,container,false);
        MainActivity mainActivity = (MainActivity)getActivity();
        dbHelper = mainActivity.dbHelper;
        dateedit = (TextView) view.findViewById(R.id.r_dateedit);
        //datetmp =datetmp.substring(0,4) + "." +datetmp.substring(4,6) + "." + datetmp.substring(6,8) ;
        dateedit.setText("");
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);

        searchallbt = (Button) view.findViewById(R.id.searchallbt);
        searchallbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingListView(2);
            }
        });
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
                //if(motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                Log.w("motionEvent", "motionEvent = " +motionEvent.getAction());
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    new DatePickerDialog(getContext(), dateSetListener, year, month, day).show();
                return false;
            }
        });
        spinnerplcae = (Spinner)view.findViewById(R.id.r_spinner);
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getFavoritPlaceeList());
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerplcae.setAdapter(aa2);

        spinnerplcae.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //각 항목 클릭시 포지션값을 토스트에 띄운다.
                // spinnerplcae.get
               // getBuyList();
                settingListView(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        custom_list = (ListView) view.findViewById(R.id.receiplistview);
        settingListView(1);
        return view;
    }

    private List getFavoritPlaceeList(){

        List tmplist = new ArrayList();
        tmplist.add("전체");
        if(dbHelper.getFavoritePlace().size() > 0){
            for(int i = 0; i < dbHelper.getFavoritePlace().size(); i ++)
            {
                Log.e("ERROR","======dbHelper.getFavoriteItem()=======" + dbHelper.getFavoritePlace().get(i).toString());
                tmplist.add(dbHelper.getFavoritePlace().get(i).toString());
            }
        }

        return tmplist;
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
            settingListView(1);

        }


    };

    private void settingListView(int gubun){
        List<receiptList> li = new ArrayList<receiptList>();
        if(gubun == 1){
            li = dbHelper.selectReceiptList(dateedit.getText().toString().replace(".",""),spinnerplcae.getSelectedItem().toString());
        }else if(gubun == 2){
            li = dbHelper.selectReceiptList("","전체");
            dateedit.setText("");
            spinnerplcae.setSelection(0);
        }

        Array_Data = new ArrayList<Custom_List_Data>();

        for(receiptList tmp : li){

            tmp.getBuy_place();
            tmp.getTotal_price();
            tmp.getImage_num();
            tmp.getSer_num();
            tmp.getImage_num();
            data = new Custom_List_Data("[" + tmp.getReq_dt() + "]", tmp.getBuy_place(),"지출액 : " +String.valueOf(tmp.getTotal_price()) + " 원"
                    ,tmp.getSer_num(),tmp.getImage_num());
            Array_Data.add(data);
        }





        adapter = new Custom_List_Adapter(getActivity(),
                android.R.layout.simple_list_item_1, Array_Data);
        custom_list.setAdapter(adapter);
    }

    private String getimage(String imagename){

        String imagepath = imagename;
        //Bitmap bm = null;
        String imgpath = null;
        try{
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Receipt");
            imgpath = mediaStorageDir.getPath() + File.separator + imagepath ;
            Log.e("path",imgpath);
            //bm = BitmapFactory.decodeFile(imgpath);
            //imgview.setImageBitmap(bm);

        }catch(Exception e)
        {

        }

        return imgpath;

    }

    public class Custom_List_Adapter extends ArrayAdapter<Custom_List_Data>
    {
        private Context m_Context   = null;

        public Custom_List_Adapter(Context context, int textViewResourceId, ArrayList<Custom_List_Data> items)
        {
            super(context, textViewResourceId, items);
            this.m_Context = context;
        }

        @Override
        public View getView(int nPosition, View convertView, ViewGroup parent)
        {
            // 뷰를 재사용 하기 위해 필요한 클래스
            PointerView pView = null;

            View view = convertView;

            if(view == null)
            {
                view = LayoutInflater.from(m_Context).inflate(R.layout.receipt_row, null);
                pView = new PointerView(view);
                view.setTag(pView);
            }

            pView = (PointerView)view.getTag();

            // 데이터 클래스에서 해당 리스트목록의 데이터를 가져온다.
            final Custom_List_Data custom_list_data = getItem(nPosition);

            if(custom_list_data != null)
            {
                // 현재 item의 position에 맞는 이미지와 글을 넣어준다.
                //pView.GetIconView().setImageBitmap(custom_list_data.getImage_ID());
                //Bitmap bm = BitmapFactory.decodeFile(custom_list_data.get());
                //imgview.setImageBitmap(bm);
                pView.GetReqDtiew().setText(custom_list_data.getReq_dt());
                pView.GetPlaceiew().setText(custom_list_data.getPalce_nm());
                pView.GetPriceView().setText(custom_list_data.getTotal_pice());

                pView.GetReceiptBtiew().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        String image_url = getimage(custom_list_data.getImage_num());
                        bundle.putString("key", image_url);
                        receiptImageView = new receiptImageView();
                        receiptImageView.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container,receiptImageView);
                       /* FragmentManager fmanager = getFragmentManager();
                        FragmentTransaction ftrans = fmanager.beginTransaction();
                        ftrans.replace(receiptImageView);*/
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                pView.GetDeleteBtiew().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
                        alert_confirm.setMessage("삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 'YES'
                                        dbHelper.deletereceiptList(custom_list_data.getSer_num());
                                        settingListView(1);
                                    }
                                }).setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 'No'
                                        return;
                                    }
                                });
                        AlertDialog alert = alert_confirm.create();
                        alert.show();



                    }
                });
            }

            return view;
        }

        /**
         * 뷰를 재사용 하기위해 필요한 클래스
         * 클래스 자체를 view tag로 저장/불러오므로 재사용가능
         */
        private class PointerView
        {
            private View        m_BaseView      = null;
           // private ImageView m_ivIcon        = null;
            private TextView    m_place_nm       = null;
            private TextView    m_price    = null;
            private TextView    m_req_dt    = null;
            private Button     m_receipt_bt = null;
            private Button     m_delete_bt = null;

            public PointerView(View BaseView)
            {
                this.m_BaseView = BaseView;
            }

            public Button GetReceiptBtiew()
            {
                if(m_receipt_bt == null)
                {
                    m_receipt_bt = (Button)m_BaseView.findViewById(R.id.receipt_bt);
                }

                return m_receipt_bt;
            }

            public Button GetDeleteBtiew()
            {
                if(m_delete_bt == null)
                {
                    m_delete_bt = (Button)m_BaseView.findViewById(R.id.delete_bt);
                }

                return m_delete_bt;
            }


            public TextView GetReqDtiew()
            {
                if(m_req_dt == null)
                {
                    m_req_dt = (TextView)m_BaseView.findViewById(R.id.custom_list_req_dt);
                }

                return m_req_dt;
            }

            public TextView GetPlaceiew()
            {
                if(m_place_nm == null)
                {
                    m_place_nm = (TextView)m_BaseView.findViewById(R.id.custom_list_place);
                }

                return m_place_nm;
            }

            public TextView GetPriceView()
            {
                if(m_price == null)
                {
                    m_price = (TextView)m_BaseView.findViewById(R.id.custom_list_total_sum);
                }

                return m_price;
            }

        }
    }

}
