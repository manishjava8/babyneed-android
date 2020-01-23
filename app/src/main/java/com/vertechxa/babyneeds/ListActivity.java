package com.vertechxa.babyneeds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vertechxa.babyneeds.data.DatabaseHandler;

public class ListActivity extends AppCompatActivity {

//    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        db = new DatabaseHandler(this);

//        db.getAllItems();
    }
}
