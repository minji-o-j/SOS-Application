package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Menu1 extends AppCompatActivity {

    File memoFile = new File(Environment.getExternalStorageDirectory(),"memo.txt");
    File numFile = new File(Environment.getExternalStorageDirectory(),"phoneNum.txt");

    private EditText memoWrite;
    EditText e2;
    private Button save;
    private Button addNum;
    TextView t1;
    TextView disnum1;
    InputMethodManager imm;




    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0,0);

    }

    private void saveMemo(File f) {

        String memo=null;
        if(f.equals(memoFile)) {
            memo = memoWrite.getText().toString();
        }

        if(memo.length() > 0) {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(f);
                fileOutputStream.write(memo.getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

        memoWrite = (EditText)findViewById(R.id.edit_message);
        save = (Button)findViewById(R.id.button_message);
        addNum = (Button)findViewById(R.id.goNumber);
        t1 = (TextView) findViewById(R.id.Text1);
        disnum1 = (TextView) findViewById(R.id.num1);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memoWrite.getText().toString().equals("")) {
                    Toast.makeText(Menu1.this,
                            "값이 없습니다.",
                            Toast.LENGTH_LONG).show();
                } else { // 공백이 아닐때
                    String s1 = memoWrite.getText().toString();//1번 값 가져오기(xml->java)
                    t1.setText(s1);
                    Toast.makeText(Menu1.this,
                            "저장 완료!",
                            Toast.LENGTH_LONG).show();
                    hideKeyboard();
                }
                saveMemo(memoFile);
            }
        });


        loadMemo(memoFile);
        loadMemo(numFile);
    }



    private void loadMemo(File f) {
        File dataFile = f ;

        if (dataFile.exists()) {
            FileInputStream inputStream;

            try{
                inputStream = new FileInputStream(dataFile);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

            byte[] memoData = null;

            try{
                memoData = new byte[inputStream.available()];
                inputStream.read(memoData);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if(memoData != null) {
                String memoString = new String(memoData);
                if(f.equals(memoFile))
                    t1.setText(memoString);
                else if(f.equals(numFile))
                    disnum1.setText(memoString);
            }

        }
    }



    public void backToMain(View v) {
        Intent home001 = new Intent(Menu1.this, MainActivity.class);
        startActivity(home001);
    }

    public void goNumber(View v) {
        Intent num001 = new Intent(Menu1.this, Number.class);
        startActivity(num001);
    }

    public void checkGPS(View v) {
        Intent gps001 = new Intent(Menu1.this, Gps.class);
        startActivity(gps001);
    }

    @Override
    public void onBackPressed() {
        Intent home002 = new Intent(Menu1.this, MainActivity.class);
        startActivity(home002);
    }
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(memoWrite.getWindowToken(), 0);

    }
}