package com.vertechxa.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.vertechxa.babyneeds.model.Item;
import com.vertechxa.babyneeds.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    // We create our table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BABY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + " ("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.KEY_GROCERY_ITEM + " TEXT, "
                + Constants.KEY_QTY_NUMBER + " INTEGER, "
                + Constants.KEY_COLOR + " TEXT, "
                + Constants.KEY_ITEM_SIZE + " INTEGER, "
                + Constants.KEY_DATE_NAME + " LONG " + ");";

        db.execSQL(CREATE_BABY_TABLE); // creating our table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME, new String[]{Constants.DATABASE_NAME});
        //Create a table again
        onCreate(db);
    }

    // CRUD operations
    public void addBabyItem(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, item.getItem());
        values.put(Constants.KEY_QTY_NUMBER, item.getItemQuantity());
        values.put(Constants.KEY_COLOR, item.getItemColor());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("Main", "Item is added." );
//        db.close();
    }

    // Get all items
    public List<Item> getAllItems() {

        List<Item> items = new ArrayList<Item>();
        String rawQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setItem(cursor.getString(1));
                item.setItemQuantity(cursor.getInt(2));
                item.setItemColor(cursor.getString(3));
                item.setItemSize(cursor.getInt(4));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                item.setDateItemAdded(formattedDate);

                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public Item getItem(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER,
                        Constants.KEY_COLOR, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE_NAME},
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
        item.setItem(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
        item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
        item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
        item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
        item.setDateItemAdded(formattedDate);

        return item;
    }

    // Delete Single Item
    public void deleteContact(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    //Get Contacts count
    public int getCount() {

        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    // Update Item
    public int updateItem(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, item.getItem());
        values.put(Constants.KEY_QTY_NUMBER, item.getItemQuantity());
        values.put(Constants.KEY_COLOR, item.getItemColor());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        // update the row
        //update(tablename, values, where id =3
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});
    }
}
