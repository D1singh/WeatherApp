package com.example.retrone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchPlaceNameActivity extends AppCompatActivity {
    EditText search_city_by_name;
    ImageView icon_search;
    public static final String EXTRA_TEXT = "com.example.retrone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place_name);
        search_city_by_name = findViewById(R.id.search_city_by_name);
        icon_search = findViewById(R.id.icon_search);

        icon_search.setOnClickListener(v -> {
            if (search_city_by_name.getText().toString().equals("")) {
                Toast.makeText(this, "input required", Toast.LENGTH_SHORT).show();
            } else {
                String str = search_city_by_name.getText().toString();
                Intent intent = new Intent(SearchPlaceNameActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_TEXT, str);
                startActivity(intent);
            }
        });
    }
}