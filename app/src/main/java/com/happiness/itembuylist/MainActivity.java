package com.happiness.itembuylist;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Fragment buylistframent ;
    public Fragment boughtlistframent ;
    public Fragment receiptCamera;
    public Fragment favoriteitemfragment;
    public Fragment receiptListfragment;
    //DBHelper dbHelper ;
     SQLiteDatabase db;
    DBHelper dbHelper;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    //private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_CAMERA = 2;


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
        if(permissionCamera == PackageManager.PERMISSION_DENIED || permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.buylist) {
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
        }

        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
