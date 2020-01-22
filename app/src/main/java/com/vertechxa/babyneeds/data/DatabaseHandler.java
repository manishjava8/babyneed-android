package com.vertechxa.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.vertechxa.babyneeds.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    // We create our table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTABLE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + " ("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.KEY_NAME + " TEXT, "
                + Constants.KEY_PHONE_NO + " TEXT " + ")";

        db.execSQL(CREATE_CONTABLE_TABLE); // creating our table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Constants.DATABASE_NAME});

        //Create a table again
        onCreate(db);
    }

    // Add contact
    public void addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_NAME, contact.getName());
        values.put(Constants.KEY_PHONE_NO, contact.getPhoneNo());

        // Insert to row
        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("DatabaseHandler", "Added Contact Successfully :)");
        db.close(); // closing db connection
    }

    // Get a contact
    public Contact getContact(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.KEY_NAME, Constants.KEY_PHONE_NO},
                Constants.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNo(cursor.getString(2));

        return contact;
    }

    // Get all contacts
    public List<Contact> getAllContacts() {

        List<Contact> contactList = new ArrayList<Contact>();

        // Select all contacts
        String sellectAll = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sellectAll, null);

        // lop throgh  our data
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNo(cursor.getString(2));

                // add contact to contact list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    // Update Contact
    public int updateContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_NAME, contact.getName());
        values.put(Constants.KEY_PHONE_NO, contact.getPhoneNo());

        // update the row
        //update(tablename, values, where id =3
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }

    // Delete Single Contact
    public void deleteContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    //Get Contacts count
    public int getCount() {

        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return  cursor.getCount();
    }
}
