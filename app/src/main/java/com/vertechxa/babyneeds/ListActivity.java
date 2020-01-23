package com.vertechxa.babyneeds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.vertechxa.babyneeds.data.DatabaseHandler;
import com.vertechxa.babyneeds.model.Item;
import com.vertechxa.babyneeds.ui.RecyclerViewAdapter;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private DatabaseHandler db;

    private List<Item> itemList;

    private RecyclerView recyclerView;

    private RecyclerViewAdapter recyclerViewAdapter;

    private FloatingActionButton fab;

    private AlertDialog.Builder builder;

    private  AlertDialog alertDialog;

    private Button saveButton;

    private EditText babyItem, itemQuantity, itemColor, itemSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);

        db = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = db.getAllItems();
        for(Item item : itemList) {
            Log.d("ListActivity" , "OnCreate: " + item.getItem());
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });
    }

    private void createPopDialog() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        babyItem = view.findViewById(R.id.babyItem);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        itemColor = view.findViewById(R.id.itemColor);
        itemSize = view.findViewById(R.id.itemSize);

        saveButton = view.findViewById(R.id.saveButton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!babyItem.getText().toString().isEmpty()
                        && !itemColor.getText().toString().isEmpty()
                        && !itemSize.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()) {
                    saveItem(v);
                } else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveItem(View view) {
        // save each baby item to db

        Item item = new Item();

        String newItem =  babyItem.getText().toString().trim();
        String newColor = itemColor.getText().toString().trim();
        int quantity = Integer.parseInt(itemQuantity.getText().toString().trim());
        int size = Integer.parseInt(itemSize.getText().toString().trim());

        item.setItem(newItem);
        item.setItemColor(newColor);
        item.setItemQuantity(quantity);
        item.setItemSize(size);

        db.addBabyItem(item);

        Snackbar.make(view, "Item Saved.", Snackbar.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Code to be run
                alertDialog.dismiss();
                // move to next screen
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();
            }
        }, 1200);

    }
}
