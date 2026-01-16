package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;
    private Button addButton;
    private Button removeButton;
    private LinearLayout inputBox;
    private EditText cityInput;
    private Button confirmButton;
    private int selectedPosition = -1; // 0 selected cities



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cityList = findViewById(R.id.city_list);

        String[] cities = {
                "Edmonton",
                "Vancouver",
        };

        //create arraylist to add or remove cities
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities)); // copy the array to the list


        cityAdapter = new ArrayAdapter<>(this, R.layout.content, R.id.content_view, dataList);
        cityList.setAdapter(cityAdapter);

        addButton = findViewById(R.id.button_add);
        removeButton = findViewById(R.id.button_remove);

        inputBox = findViewById(R.id.input_box);
        cityInput = findViewById(R.id.city_input);
        confirmButton = findViewById(R.id.button_confirm);

        //add city
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {

                        //The following line is from chatgpt, "how to make button invisible till i need it in xml then use it in java?", 2026-01-16
                        //    Example given : android:visibility="gone"
                        //    inputBox.setVisibility(View.VISIBLE);
                        //    inputBox.setVisibility(View.GONE);

                        inputBox.setVisibility(View.VISIBLE); // show input area when button clicken
                        cityInput.setText(""); // clear previous text
                        cityInput.requestFocus();
                    }
                }
        );
        // confirm button
        confirmButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {String newCity = cityInput.getText().toString().trim();
                        if (newCity.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Enter a city", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dataList.add(newCity); // add to the data list
                        cityAdapter.notifyDataSetChanged();

                        inputBox.setVisibility(View.GONE);
                        cityInput.setText("");
                    }
                }
        );

        // select city
        cityList.setOnItemClickListener(
                new android.widget.AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                        selectedPosition = position; // store the position of the selected city
                        Toast.makeText(MainActivity.this, "Selected  " + dataList.get(position), Toast.LENGTH_SHORT).show();}
                }
        );

        // delete city
        removeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedPosition == -1) {
                            Toast.makeText(MainActivity.this, "Select a city to delete", Toast.LENGTH_SHORT).show();
                            return;}
                        dataList.remove(selectedPosition);
                        cityAdapter.notifyDataSetChanged();

                        // reset selection
                        selectedPosition = -1;}
                }
        );
    }
}

