package com.happiness.itembuylist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.util.RangeValueIterator;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by kanghyun on 2017-10-25.
 */

public class GmailSend extends Fragment {

    TextView emailaddr ;
  //  TextView recentversion ;
    TextView devicevercion ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gmailsend,container,false);
        getActivity().setTitle("문의사항");
        emailaddr = (TextView)view.findViewById(R.id.emailaddr);
     //   recentversion = (TextView)view.findViewById(R.id.recentversion);
        devicevercion = (TextView)view.findViewById(R.id.deviceversion);
        emailaddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkemailaddr();
            }
        });
      //  String store_version = MarketVersionChecker.getMarketVersion(getActivity().getPackageName());
        try {
            String device_version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            devicevercion.append(device_version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        return view;
    }

    private void linkemailaddr(){
        Uri uri = Uri.parse("mailto:hardkkh1023@gmail.com");
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(it);
    }

    public static String getVersionName(Context context) {

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {

            return null;
        }
    }

}
