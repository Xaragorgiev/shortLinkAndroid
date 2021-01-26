package com.itproger.a3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText long_link;
    private EditText short_link;
    private Button button;
    private ListView listView;
    private ArrayList<String> shortList;
    private ArrayList<String> longList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long_link = findViewById(R.id.long_link);
        short_link = findViewById(R.id.short_link);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);
        shortList = new ArrayList<>();
        longList = new ArrayList<>();

        long_link.addTextChangedListener(textWatcher);
        short_link.addTextChangedListener(textWatcher);
        addLink();
        check();
    }

    private void addLink() {
        button.setOnClickListener(v -> {
            if (long_link.getText().length() != 0) {
                if (short_link.getText().length() != 0) {
                    for (String s : shortList) {
                        if (String.valueOf(short_link.getText()).equalsIgnoreCase(s)) {
                            button.setText("Выберите другое сокращение");
                            return;
                        }
                    }
                    shortList.add(String.valueOf(short_link.getText()));
                    longList.add(String.valueOf(long_link.getText()));
                    add();
                    button.setText("Успешно");
                    short_link.setText("");
                    long_link.setText("");
                } else
                    button.setText("Заполните все поля");
            } else
                button.setText("Заполните все поля");
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (long_link.getText().length() != 0) {
                button.setText("Добавить");
            }
            if (short_link.getText().length() != 0) {
                button.setText("Добавить");
                for (String c : shortList) {
                    if (String.valueOf(short_link.getText()).equalsIgnoreCase(c)) {
                        button.setText("Выберите другое сокращение");
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void add() {
            arrayAdapter = new ArrayAdapter<>(this, R.layout.links_layout, R.id.link, shortList);
            listView.setAdapter(arrayAdapter);
    }

    void check() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(longList.get(position)));
            startActivity(browserIntent);
        });
    }
}