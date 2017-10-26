package com.happiness.itembuylist;

/**
 * Created by kanghyun on 2017-09-30.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class receiptCamera extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    protected static final String TAG = null;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private boolean inProgress;
    private Spinner spinner;
    //private EditText et ;
    private Button cameraretrybt;
    private Button saveimagebt;
    byte[] savadata;
    private TextView dateedit;/*날짜*/
    private TextView placetxt;/*장소*/
    private TextView sumpricetxt;/*총가격*/
    public static final int K_STATE_PREVIEW = 0;
    public static final int K_STATE_FROZEN = 1;
    int mPreviewState ;

    private String imagename;
    DBHelper dbHelper ;



    int year ;
    int month ;
    int day;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("카메라");
        View view = inflater.inflate(R.layout.viewreceiptcamera,container,false);
        surfaceView =  (SurfaceView) view.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceListener);
        //et = (EditText)view.findViewById(R.id.text2);
        dateedit = (TextView) view.findViewById(R.id.dateedit);
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        placetxt = (TextView)view.findViewById(R.id.placetxt);
        sumpricetxt = (TextView)view.findViewById(R.id.sumpricetxt);
        inProgress = false;
        savadata = null;

        MainActivity mainActivity = (MainActivity)getActivity();
        dbHelper = mainActivity.dbHelper;

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PackageManager pm = getActivity().getPackageManager();
                if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) && pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS) && (mPreviewState==K_STATE_PREVIEW)){
                    camera.autoFocus (new Camera.AutoFocusCallback() {
                        public void onAutoFocus(boolean success, Camera camera) {
                            if(success){
                                Toast.makeText(getActivity().getApplicationContext(),"Auto Focus Success",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity().getApplicationContext(),"Auto Focus Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                return false;
        }
        });

        //surfaceView.OnTouchListener(new O)

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String getTime = sdf.format(date);
        //
        getTime =getTime.substring(0,4) + "." +getTime.substring(4,6) + "." + getTime.substring(6,8) ;
        dateedit.setText(getTime);
        imagename = null;

/*
        datebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), dateSetListener, year, month, day).show();

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

        cameraretrybt = (Button) view.findViewById(R.id.cameraretrybt);
        cameraretrybt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                savadata = null;
                inProgress = false;
                camera.startPreview();
                mPreviewState= K_STATE_PREVIEW;

            }
        });

        saveimagebt = (Button) view.findViewById(R.id.savepickturebt);

        saveimagebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sumpricetxt.getText().length() > 9){
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
                if(spinner.getSelectedItem().toString().equals("") ||spinner.getSelectedItem().toString().equals("구매장소")  ){
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
                }
                saveimage();
            }
        });

        //String [] items = {"선택","E.mart","H.plus","L.mart","ETC"};
        spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getFavoritPlaceeList());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(this);

        view.findViewById(R.id.camerabt).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(camera !=null && inProgress == false)
                        {
                            camera.takePicture(null, null, takePicture);
                            inProgress = true;
                        }
                    }
                }
        );

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



    private Camera.PictureCallback takePicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            savadata = null;

            if(data!=null)
                Log.i(TAG, "JPEG 사진 찍었음!");
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            savadata = data;
            bitmap.recycle();
            camera.stopPreview();
            mPreviewState = K_STATE_FROZEN;
            //inProgress = false;

        }
    };

        private File getOutputMediaFile(){

             File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Receipt");
            //mediaStorageDir.getPath().toString();

            if(!mediaStorageDir.exists()){

                if(! mediaStorageDir.mkdirs()){

                    Log.d("MyCamera", "failed to create directory");

                    return null;

                }

            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            imagename = "IMG_" + timestamp + ".jpg";

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timestamp + ".jpg");

            Log.i("MyCamera", "Saved at"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));

            System.out.println(mediaFile.getPath());

            return mediaFile;
        }

    private void saveimage(){

             File pictureFile = getOutputMediaFile();

            if(pictureFile == null) {

                Toast.makeText(getActivity().getApplicationContext(),"사진이 저장 오류",Toast.LENGTH_LONG).show();

                return;
            }
            //camera.startPreview();
            try{

                if(savadata == null){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("사진을 찍어주세요.");
                    alert.show();
                    return;
                }else {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(savadata);
                    getActivity().sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(pictureFile)) );
                    fos.close();

                    Toast.makeText(getActivity().getApplicationContext(),"사진저장되었습니다.",Toast.LENGTH_LONG).show();

                    int totalprice = 0 ;
                    if(String.valueOf(sumpricetxt.getText()).equals("")){
                        totalprice = 0;
                    }else {
                        totalprice = Integer.parseInt(String.valueOf(sumpricetxt.getText()));
                    }

                    String datetmp = String.valueOf(dateedit.getText()).replace(".","");
                    //String.valueOf(dateedit.getText()).replace(".","");
                    Log.e("datetmp",datetmp);

                    dbHelper.addReceiptList(datetmp,spinner.getSelectedItem().toString(),totalprice,imagename);
                }

                //imagename



            } catch (FileNotFoundException e) {

                Log.d(TAG, "File not found: " + e.getMessage());

            } catch (IOException e) {

                Log.d(TAG, "Error accessing file: " + e.getMessage());

            }

    }

    // 이미지 회전 함수
    public Bitmap rotateBitmap(Bitmap bitmap, int rotate){

        Log.d("TEST", "ROTATE : " + rotate);
        Matrix rotateMatrix = new Matrix();

        if(rotate == 0 )
            rotateMatrix.postRotate(0);
        else if(rotate == 1)
            rotateMatrix.postRotate(45);
        else if(rotate == 2)
            rotateMatrix.postRotate(90);
        else if(rotate == 3)
            rotateMatrix.postRotate(135);
        else if(rotate == 4)
            rotateMatrix.postRotate(180);
        else if(rotate == 5)
            rotateMatrix.postRotate(225);
        else if(rotate == 6)
            rotateMatrix.postRotate(270);
        else
            rotateMatrix.postRotate(315);

        Bitmap sideInversionImg = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, false);

        return sideInversionImg;
    }



    private SurfaceHolder.Callback surfaceListener = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            camera.release();
            camera = null;
            Log.i(TAG, "카메라 기능 해제");
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            Log.i(TAG, "카메라 미리보기 활성");
            camera.startPreview();
            try {
                inProgress = false;
                camera.setPreviewDisplay(holder);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
            // TODO Auto-generated method stub
            Camera.Parameters parameters = camera.getParameters();

            /*if(inProgress == false)
            {
                parameters.setPreviewSize(width, height);
                camera.startPreview();
            }else {
                holder.setKeepScreenOn(false);
            }*/
            // 우선 멈춘다
            try {
             //   camera.stopPreview();
             //   mPreviewState = K_STATE_FROZEN;
            } catch (Exception e){
                // 프리뷰가 존재조차 하지 않는 경우다
            }

            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }

            parameters.setPreviewSize(width, height);

            // 새로 변경된 설정으로 프리뷰를 재생성한다
            try {
             //   camera.setParameters(parameters);
                //camera.setPreviewDisplay(holder);
                //camera.startPreview();
               // mPreviewState = K_STATE_PREVIEW;

            } catch (Exception e){
                Log.d(TAG, "Error starting camera preview: " + e.getMessage());
            }

            Log.i(TAG,"카메라 미리보기 활성");

        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(spinner.getSelectedItem().toString() == "구매장소") {
            //et.setHint("구매물품입력");
            //placetxt.setText("구매장소");
        }else{
           // placetxt.setText(spinner.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




}