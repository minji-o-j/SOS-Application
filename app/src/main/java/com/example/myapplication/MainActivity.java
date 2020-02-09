package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_USED_PERMISSION = 200;

    private static final String[] needPermissons = { Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SEND_SMS ,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    private final long FINISH_INTERVAL_TIME = 1000;
    private long backPressedTime = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean permissionToFileAccepted = true;

        switch(requestCode)

        {
            case REQUEST_USED_PERMISSION:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionToFileAccepted = false;
                        break;
                    }
                }
                break;
        }
        if(permissionToFileAccepted == false) {
            finish();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0,0);
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(String permission : needPermissons){
            if(ActivityCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,needPermissons, REQUEST_USED_PERMISSION);
                break;
            }
        }
    }

    public void button1(View view) {
        Intent intent001 = new Intent(MainActivity.this, Main2Activity.class);
        Toast.makeText(this, "한 번 더 버튼을 누르면 SOS 메시지가 전송됩니다!", Toast.LENGTH_LONG).show();
        startActivity(intent001);
    }

    public void menuButton1(View view) {
        Intent menu001 = new Intent(MainActivity.this, Menu1.class);
        startActivity(menu001);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            ActivityCompat.finishAffinity(MainActivity.this);
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번더 뒤로가기를 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();

        }
    }
}