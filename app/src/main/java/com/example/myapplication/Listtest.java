package com.example.myapplication;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Listtest extends AppCompatActivity {

    ArrayList<String> Items;
    ArrayAdapter<String> Adapter;
    ListView listView;
    Button btnAdd, btnDel;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtest);

        Items = new ArrayList<String>();
        Items.add("First");
        Items.add("Second");
        Items.add("Third");

        Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, Items);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(Adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        editText = (EditText) findViewById(R.id.editText);
        btnAdd  = (Button) findViewById(R.id.btnAdd);
        btnDel  = (Button) findViewById(R.id.btnDel);

        btnAdd.setOnClickListener(listener);
        btnDel.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnAdd:
                    String text = editText.getText().toString();
                    if (text.length() != 0) {
                        Items.add(text);
                        editText.setText("");
                        Adapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.btnDel:
                    int pos;
                    pos = listView.getCheckedItemPosition();
                    if (pos != ListView.INVALID_POSITION) {
                        Items.remove(pos);
                        listView.clearChoices();
                        Adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
}

