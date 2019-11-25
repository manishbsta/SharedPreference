package com.manishbsta.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText etFileContent;
    private Button btnSave, btnLoad;
    HashMap<String, String> words = new HashMap<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        etFileContent = findViewById(R.id.etFileContent);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etFileContent.getText().toString();

                try {
                    FileOutputStream fos = openFileOutput("myfile.txt", MODE_PRIVATE);
                    fos.write(text.getBytes());
                    etFileContent.getText().clear();
                    Toast.makeText(MainActivity.this, "Text Saved to "+getFilesDir(), Toast.LENGTH_SHORT).show();
                } catch (java.io.IOException e) {
                    Log.d("Exc", e.toString());
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = words.get(parent.getItemAtPosition(position).toString());
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }
        });


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = openFileInput("myfile.txt");
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    String data;
                    String allData = "";
                    while ((data = bufferedReader.readLine())!= null){
//                        allData += data +"\n";

                        String[] wm = data.split("=");
                        words.put(wm[0], wm[1]);

                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, R.layout.textview, new ArrayList<>(words.keySet()));
                    listView.setAdapter(arrayAdapter);
                } catch (java.io.IOException e) {
                    Log.d("Exc", e.toString());
                } finally {

                }
            }
        });

    }
}