package com.bmcong2k.sqlite.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bmcong2k.sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ChiTieu.db";
    private static int DATABASE_VERSION = 1;

    public SQliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create database only 1
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, " +
                "category TEXT," +
                "price TEXT," +
                "date TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //getALlItem order by time

    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("items", null, null, null,
                null, null, order);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

    public long addItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items", null, values);
    }

    //get item by date
    public List<Item> getByDate(String date) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereAgrg = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null,
                whereClause, whereAgrg, null, null, null);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

    // Update
    public int update(Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());

        String whereClause = "id=?";
        String[] whereAgrg = {Integer.toString(i.getId())};

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("items", values, whereClause, whereAgrg);
    }
    //Delete
    public int delete(int id){
        String whereClause = "id=?";
        String[] whereAgrg = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("items", whereClause, whereAgrg);
    }

    public List<Item> searchByTitle(String key) {
        List<Item> list = new ArrayList<>();
        //select * from items where title like %key%
        String whereClause = "title like ?";
        String[] whereAgrg = {"%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null,
                whereClause, whereAgrg, null, null, null);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

    public List<Item> searchByCategory(String category) {
        List<Item> list = new ArrayList<>();
        //select * from items where title like %key%
        String whereClause = "category like ?";
        String[] whereAgrg = {category};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null,
                whereClause, whereAgrg, null, null, null);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, title, c, price, date));
        }
        return list;
    }

    public List<Item> searchByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();
        //select * from items where title like %key%
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereAgrg = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null,
                whereClause, whereAgrg, null, null, null);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }
}
