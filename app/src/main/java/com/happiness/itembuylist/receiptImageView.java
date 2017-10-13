package com.happiness.itembuylist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by kanghyun on 2017-10-09.
 */

public class receiptImageView extends Fragment {

    ImageView m_imageView;
    PhotoViewAttacher mAttacher;
    private String ImageUrl;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.receipt_image_view,container,false);
        m_imageView = (ImageView) view.findViewById(R.id.imageView);
        Bundle bundle = getArguments();
        String Image_Url = bundle.getString("key");
        Log.e("Image_Url",Image_Url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap src = BitmapFactory.decodeFile(Image_Url, options);
       // Bitmap resized = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true);
       // Bitmap bm = BitmapFactory.decodeFile(Image_Url);
        m_imageView.setImageBitmap(src);
        mAttacher = new PhotoViewAttacher(m_imageView);
        // 3.화면에 꽉차는 옵션 (선택사항)
        mAttacher.setScaleType(ImageView.ScaleType.FIT_XY);

        return view;

    }




}
