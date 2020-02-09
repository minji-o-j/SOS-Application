package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity  {
    private final long FINISH_INTERVAL_TIME = 1000;
    private long backPressedTime = 0;
    private GpsTracker gpsTracker;
    ArrayList<String> numBook= new ArrayList<String>();

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0,0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }



    final LocationListener gpsLocationListner=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {


            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    public void sendMessage() {

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            final LocationManager lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String provider = location.getProvider();
            gpsTracker = new GpsTracker(Main2Activity.this);

            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            //double longitude = location.getLongitude();
            //double latitude = location.getLatitude();

            // double altitude = location.getAltitude();
            String where1="http://maps.google.com/maps?f=q&q=("+latitude+","+longitude +")";
            String message = loadData("memo.txt");
            loadData("phoneNum.txt");
            try {
                for(String s:numBook) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(s, null, message, null, null);
                    smsManager.sendTextMessage(s, null, where1, null, null);
                }
                Toast.makeText(this, "메시지가 전송되었습니다!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "메시지 전송 실패! 다시 시도하세요!", Toast.LENGTH_LONG).show();
            }

        }
    }
    public void button2(View view) {
        Intent intent002 = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent002);
        sendMessage();
    }

    public void menuButton2(View view) {
        Intent menu002 = new Intent(Main2Activity.this, Menu1.class);
        startActivity(menu002);

    }
    private File getMemoFile(String s) {
        File memoFile = new File(Environment.getExternalStorageDirectory(),s)  ;
        return memoFile;
    }

    private String loadData(String s) {
        File memoFile = getMemoFile(s);
        String output=null;
        if (memoFile.exists()) {
            FileInputStream inputStream;
            try {
                inputStream = new FileInputStream(memoFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "error";
            }

            byte[] memoData = null;
            try {
                memoData = new byte[inputStream.available()];
                inputStream.read(memoData);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }

            if (memoData != null) {
                String memoString = new String(memoData);
                output = new String(memoString);
            }

//            if(s.equals("phoneNum.txt")) {
//                StringBuffer tmp = new StringBuffer();
//                char[] charData = memoData.toString().toCharArray();
//                for (int i = 0; i < charData.length; i++) {
//                    if (charData[i] == '\n') {
//                        numBook.add(tmp.toString());
//                        tmp.delete(0, tmp.length());
//                    } else
//                        tmp.append(charData[i]);
//                }
//            }
            if(s.equals("phoneNum.txt")) {
                StringBuffer tmp = new StringBuffer();
                for (int i = 0; i<memoData.length; i++) {
                    if (memoData[i] == '\n') {
                        numBook.add(tmp.toString());
                        tmp.delete(0, tmp.length());
                    } else if (memoData[i]>=48&&memoData[i]<=57) {
                        tmp.append(memoData[i]-48);

                    }
                }
            }
        }

        return output;
    }


//뒤로가기 두번시 앱 종료
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            ActivityCompat.finishAffinity(Main2Activity.this);
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번더 뒤로가기를 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();

        }
    }
}