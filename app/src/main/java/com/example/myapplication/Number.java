package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Number extends AppCompatActivity {
    private final String fileName = "phoneNum.txt" ;
    private String number;
    private ListView listview ;
    private ArrayAdapter adapter ;
    private ArrayList<String> items = new ArrayList<String>() ;
    TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        num = (TextView) findViewById(R.id.editText);
        listview = (ListView) findViewById(R.id.listView) ;
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;

        listview.setAdapter(adapter) ;

        // 파일에서 데이터를 읽어들여 리스트뷰에 표시.
        loadItemsFromFile() ;
        adapter.notifyDataSetChanged();


        Button buttonAdd = (Button) findViewById(R.id.btnAdd) ;
        buttonAdd.setEnabled(false) ; // 초기 버튼 상태 비활성 상태로 지정.
        buttonAdd.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextNew = (EditText) findViewById(R.id.editText) ;
                String strNew = (String) editTextNew.getText().toString() ;

                if (strNew.length() > 0) {
                    // 리스트에 문자열 추가.
                    items.add(strNew);

                    // 에디트텍스트 내용 초기화.
                    editTextNew.setText("") ;

                    // 리스트뷰 갱신
                    adapter.notifyDataSetChanged();

                    // 리스트뷰 아이템들을 파일에 저장.
                    saveItemsToFile() ;
                    Toast.makeText(Number.this,
                            "저장되었습니다.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button buttonDel = (Button) findViewById(R.id.btnDel) ;
        buttonDel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count ;
                int checkedIndex ;

                count = adapter.getCount() ;

                if (count > 0) {
                    // 리스트뷰에서 선택된 아이템 인덱스 얻어오기.
                    checkedIndex = listview.getCheckedItemPosition();
                    if (checkedIndex > -1 && checkedIndex < count) {
                        // 아이템 삭제
                        items.remove(checkedIndex) ;

                        // 리스트뷰 선택 초기화.
                        listview.clearChoices();

                        // 리스트뷰 갱신
                        adapter.notifyDataSetChanged();

                        // 리스트뷰 아이템들을 파일에 저장.
                        saveItemsToFile() ;

                        Toast.makeText(Number.this,
                                "삭제되었습니다.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Button buttonDirectory = (Button) findViewById(R.id.btnBring) ;
        buttonDirectory.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 0);


            }


        });

        EditText editTextNew = (EditText) findViewById(R.id.editText) ;
        editTextNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                Button buttonAdd = (Button) findViewById(R.id.btnAdd) ;
                if (edit.toString().length() > 0) {
                    // 버튼 상태 활성화.
                    buttonAdd.setEnabled(true) ;
                } else {
                    // 버튼 상태 비활성화.
                    buttonAdd.setEnabled(false) ;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }) ;





    }
    private void loadItemsFromFile() {
        File file = new File(Environment.getExternalStorageDirectory(), fileName) ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;

                while ((str = bufrd.readLine()) != null) {
                    items.add(str) ;
                }

                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    private void saveItemsToFile() {
        File file = new File(Environment.getExternalStorageDirectory(), fileName) ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;

            for (String str : items) {
                bufwr.write(str) ;
                bufwr.newLine() ;
            }

            // write data to the file.
            bufwr.flush() ;

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        try {
            // close file.
            if (bufwr != null) {
                bufwr.close();
            }

            if (fw != null) {
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }
    public void goMenu(View v) {
        Intent num001 = new Intent(Number.this, Menu1.class);
        // saveItemsToFile();
        startActivity(num001);
    }
    @Override
    public void onBackPressed() {
        Intent home003 = new Intent(Number.this, Menu1.class);
        startActivity(home003);
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            Cursor cursor = getContentResolver().query(data.getData(),

                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,

                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);

            cursor.moveToFirst();

            //name = cursor.getString(0);        //0은 이름을 얻어옵니다.

            number = cursor.getString(1);   //1은 번호를 받아옵니다.

            cursor.close();


        }

        Number.super.onActivityResult(requestCode, resultCode, data);
        num.setText(number);
    }
}