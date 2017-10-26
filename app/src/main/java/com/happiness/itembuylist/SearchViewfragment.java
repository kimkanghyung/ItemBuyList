package com.happiness.itembuylist;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by kanghyun on 2017-10-19.
 */

public class SearchViewfragment extends Fragment implements SearchView.OnQueryTextListener,BackPressedFragment {
    DBHelper dbHelper ;
    SearchView sv;
    private ArrayList<CustomSearchData> Array_Data;
    private CustomSearchData data;
    private SearchDataListAdapter adapter;
    ListView custom_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_list,container,false);
        MainActivity mainActivity = (MainActivity)getActivity();
        dbHelper = mainActivity.dbHelper;
        //setHasOptionsMenu(true);
        //getActivity().supportInvalidateOptionsMenu();
        //setHasOptionsMenu(true);
//        sv.setQuery("", true) ;
        getActivity().invalidateOptionsMenu();
        custom_list = (ListView) view.findViewById(R.id.searchlistview);

        return view;
    }

    private void settingListView( List<searchItemList> li){
       // List<searchItemList> li = new ArrayList<searchItemList>();
        Array_Data = new ArrayList<CustomSearchData>();
        if(li.size() == 0){
            Array_Data.clear();
        }



        for(searchItemList tmp : li){

            tmp.getBuypalce();
            tmp.getItem_nm();
            tmp.getReq_dt();
            tmp.getPre_price();

            String str =  tmp.getReq_dt().substring(0,4) + "." +tmp.getReq_dt().substring(4,6) + "." + tmp.getReq_dt().substring(6,8) ;
            data = new CustomSearchData("[" + str + "]",  tmp.getBuypalce(),"지출액 : " +String.valueOf(tmp.getPre_price()) + " 원"
                   ,"구매물품 : "+tmp.getItem_nm());
            Array_Data.add(data);
        }

        adapter = new SearchDataListAdapter(getActivity(),
                android.R.layout.simple_list_item_1, Array_Data);
        custom_list.setAdapter(adapter);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.main, menu);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("onCreate!!!","onCreate!!");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //menu
    }

    @Override
    public void onStart() {
        Log.e("onStart!!!","onStart!!");
        try {
            sv.setQuery("", true) ;

        }catch (Exception e){

        }

        super.onStart();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        System.out.println("newText =" + newText);
        return false;
    }

 @Override
    public void onPrepareOptionsMenu(Menu menu) {
     Log.e("onPrepareOptionsMenu" , "onPrepareOptionsMenu");
    /* sv.post(new Runnable() {

         @Override
         public void run() {
             sv.setQuery("", false);
         }
     });
     */
     //   super.onPrepareOptionsMenu(menu);
    }




   @Override
   public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
      // menu.clear();
 /*      MainActivity mainActivity = (MainActivity)getActivity();
       System.out.print("onCreateOptionsMenu!!!");
       Log.e("onCreateOptionsMenu" , "onCreateOptionsMenu");
       sv = mainActivity.searchView;
       MenuItem searchItem = menu.findItem(R.id.action_search);
       sv = (SearchView) MenuItemCompat.getActionView(searchItem);
       sv.setQuery("", true) ;
*/
       Log.e("onCreateOptionsMenu" , "onCreateOptionsMenu");
       MainActivity mainActivity = (MainActivity)getActivity();

       sv = mainActivity.searchView;
       MenuItem searchItem = menu.findItem(R.id.action_search);
       sv = (SearchView) MenuItemCompat.getActionView(searchItem);
       sv.setQuery("", false) ;
       sv.clearFocus();
       searchItem.expandActionView();

    //    super.onCreateOptionsMenu(menu, inflater);


   /*     SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
                */
        //inflater.inflate(R.menu.main, menu); // removed to not double the menu items
        //getActivity().getMenuInflater().inflate(R.menu.main, menu);
       // MenuItem item = menu.findItem(R.id.action_search);
       // SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
      //  MainActivity mainActivity = (MainActivity)getActivity();
      //  SearchView sv = mainActivity.searchView;
     //   MenuItem searchItem = menu.findItem(R.id.action_search);
      //  sv = (SearchView) MenuItemCompat.getActionView(searchItem);
                //  sv.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getActivity(), MainActivity.class)));
      //  sv= new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
       // MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
       // MenuItemCompat.setActionView(item, sv);
       // sv.setOnQueryTextListener(this);
       // sv.setIconifiedByDefault(false);
       // dbHelper.selectSearchItemtList();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //System.out.println("111111");
                List<searchItemList> li = dbHelper.selectSearchItemtList(s);
                if(li.size() > 0){
                    settingListView(li);
                }else {
                    settingListView(li);
                }
                return false;
            }
        });

        sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("SeacrchCick" , "SeacrchCick");
            }
        });
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
               // super.onBackPressed();
                onPopBackStack();
                return true;  // Return true to expand action view
            }
        });

   //    super.onCreateOptionsMenu(menu,inflater);
    }



    @Override
    public void onPopBackStack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public class SearchDataListAdapter extends ArrayAdapter<CustomSearchData>
    {
        private Context m_Context   = null;

        public SearchDataListAdapter(Context context, int textViewResourceId, ArrayList<CustomSearchData> items)
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
                view = LayoutInflater.from(m_Context).inflate(R.layout.search_row, null);
                pView = new PointerView(view);
                view.setTag(pView);
            }

            pView = (PointerView)view.getTag();

            // 데이터 클래스에서 해당 리스트목록의 데이터를 가져온다.
            final CustomSearchData CustomSearchData = getItem(nPosition);

            if(CustomSearchData != null)
            {

                pView.GetReqDtiew().setText(CustomSearchData.getReq_dt());
                pView.GetPlaceiew().setText(CustomSearchData.getPalce_nm());
                pView.GetPriceView().setText(CustomSearchData.getTotal_pice());
                pView.GetItemnmView().setText(CustomSearchData.getItem_nm());


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
            private TextView    m_item_nm    = null;

            public PointerView(View BaseView)
            {
                this.m_BaseView = BaseView;
            }



            public TextView GetReqDtiew()
            {
                if(m_req_dt == null)
                {
                    m_req_dt = (TextView)m_BaseView.findViewById(R.id.item_req_dt);
                }

                return m_req_dt;
            }

            public TextView GetPlaceiew()
            {
                if(m_place_nm == null)
                {
                    m_place_nm = (TextView)m_BaseView.findViewById(R.id.item_place);
                }

                return m_place_nm;
            }

            public TextView GetPriceView()
            {
                if(m_price == null)
                {
                    m_price = (TextView)m_BaseView.findViewById(R.id.item_price);
                }

                return m_price;
            }

           public TextView GetItemnmView()
            {
                if(m_item_nm == null)
                {
                    m_item_nm = (TextView)m_BaseView.findViewById(R.id.item_nm);
                }

                return m_item_nm;
            }


        }
    }

}
