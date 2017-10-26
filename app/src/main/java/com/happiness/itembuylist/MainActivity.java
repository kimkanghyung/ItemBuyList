package com.happiness.itembuylist;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Fragment buylistframent ;
    public Fragment boughtlistframent ;
    public Fragment receiptCamera;
    public Fragment favoriteitemfragment;
    public Fragment receiptListfragment;
    public Fragment searchviewfragment;
    public Fragment gmailsender;
    SearchView searchView;
    //DBHelper dbHelper ;
     SQLiteDatabase db;
    DBHelper dbHelper;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    //private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_CAMERA = 4;
    /* 버젼체크 */
    String Apppackage = "";
    String SaveMarketVersion;
    String SaveAppVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //dbHelper = new DBHelper(getApplicationContext(), "Itemlist.db", null, 1);
       // db = dbHelper.getWritableDatabase();
        authCheck();



        buylistframent = new BuylistFrament();
        boughtlistframent = new BoughtListFragment();
        receiptCamera = new receiptCamera();
        favoriteitemfragment = new FavoriteItemFragment();
        receiptListfragment = new receiptListfragment();
        searchviewfragment = new SearchViewfragment();
        gmailsender = new GmailSend();

        dbHelper = new DBHelper(getApplicationContext(), "itembuylist.db", null, 1);
        //db = dbHelper.getWritableDatabase();
        //dbHelper.insertFirstFavoritePlace();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment selectedFragment = null;
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.nav_camera:
                                if(authCheck()){
                                    transaction.replace(R.id.container,receiptCamera);
                                    navigationView.setCheckedItem(R.id.nav_camera);
                                }

                                break;
                            case R.id.buylist:
                                transaction.replace(R.id.container,buylistframent);
                                navigationView.setCheckedItem(R.id.buylist);
                                break;
                            case R.id.boughtlist:
                                transaction.replace(R.id.container,boughtlistframent);
                                navigationView.setCheckedItem(R.id.boughtlist);
                                break;
                            case R.id.receipt_list:
                                transaction.replace(R.id.container,receiptListfragment);
                                navigationView.setCheckedItem(R.id.receipt_list);
                                break;
                        }

                       transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                });
    }
    public boolean authCheck(){

        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);

        int permissionReadStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionInternet = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);
        int permissionInternetstate = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE);

        if(permissionCamera == PackageManager.PERMISSION_DENIED || permissionReadStorage == PackageManager.PERMISSION_DENIED
                || permissionWriteStorage == PackageManager.PERMISSION_DENIED
                || permissionInternet == PackageManager.PERMISSION_DENIED
                || permissionInternetstate == PackageManager.PERMISSION_DENIED
                ) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                                  Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_CAMERA);
            return false;
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        } else {
            return true;
            //resultText.setText("camera permission authorized");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            //resultText.setText("camera permission authorized");
                        } else {
                            //resultText.setText("camera permission denied");
                            onBackPressed();
                        }
                    }

                    if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {

                        } else {
                            onBackPressed();
                        }
                    }
                    if (permission.equals(Manifest.permission.INTERNET)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {

                        } else {
                            onBackPressed();
                        }
                    }
                    if (permission.equals(Manifest.permission.ACCESS_NETWORK_STATE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {

                        } else {
                            onBackPressed();
                        }
                    }
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       // MenuItem searchItem = menu.findItem(R.id.action_search);
      //  searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
     /*   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(s);
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                //DO SOMETHING WHEN THE SEARCHVIEW IS CLOSING
                onBackPressed();
                return true;
            }
        });
        */



        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_search:
                Fragment selectedFragment = null;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,searchviewfragment);
               // navigationView.setCheckedItem(R.id.receipt_list);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }

        //noinspection SimplifiableIfStatement
     /*   if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if (id == R.id.nav_camera) {
            // Handle the camera action
            transaction.replace(R.id.container,receiptCamera);
           // bottomNavigationView.setId(R.id.nav_camera);
            bottomNavigationView.getMenu().getItem(2).setChecked(true);

        }  else if (id == R.id.buylist) {
            transaction.replace(R.id.container,buylistframent);
           // bottomNavigationView.setId(R.id.buylist);
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
        else if (id==R.id.boughtlist){
            transaction.replace(R.id.container,boughtlistframent);
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
         //   bottomNavigationView.setId(R.id.boughtlist);
        }
        else if(id==R.id.favoritebuylist){

            transaction.replace(R.id.container,favoriteitemfragment);

        }
        else if(id==R.id.receipt_list){
            transaction.replace(R.id.container,receiptListfragment);
            bottomNavigationView.getMenu().getItem(3).setChecked(true);
        }else if(id==R.id.nav_send){
            transaction.replace(R.id.container,gmailsender);
           // bottomNavigationView.getMenu().getItem(3).setChecked(true);
        }

       transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* 버젼 체크 로직 */
    private class getMarketVersion extends AsyncTask<String, String, String> {

        String MarketVersion;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String AppFromPlayStore = "https://play.google.com/store/apps/details?id=" + Apppackage;
                Document doc = Jsoup.connect(AppFromPlayStore).get();
                MarketVersion = doc.getElementsByAttributeValue("itemprop", "softwareVersion").first().text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MarketVersion;
        }
    }

    private String getAppVersion() {
        PackageManager pm = getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        String currentVersion = pInfo.versionName;

        return currentVersion;
    }

    public void CompareVersion() {
        if (isNetWork() == true) {
            try {
                SaveMarketVersion = new getMarketVersion().execute().get();
                SaveAppVersion = getAppVersion();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (!SaveMarketVersion.toString().equals(SaveAppVersion)) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("업데이트가 필요합니다.\n업데이트를 해 주세요.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                Uri uri = Uri.parse("market://details?id=" + Apppackage);
                                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    }

    private Boolean isNetWork() {//버전 확인을 위한 네트워크 체크 함수
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if ((isWifiAvailable && isWifiConnect) || (isMobileAvailable && isMobileConnect)) {
            return true;
        } else {
            return false;
        }
    }


}
