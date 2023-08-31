package com.example.appuinsu;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.UserData;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "app.db";
    private static final int DB_VERSION = 1;
    private static final String PREF_USER_ID = "username";
    private SharedPreferences sharedPreferences;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        sharedPreferences = context.getSharedPreferences("username", Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id interger PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE tb_anggota(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, username text, password text, role text)");
        db.execSQL("CREATE TABLE tb_keuangan(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, keterangan text, nominal text, jenis text)");
        db.execSQL("CREATE TABLE tb_kegiatan(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, waktu text, tempat text, deskripsi text, foto BLOB)");
        db.execSQL("CREATE TABLE tb_absensi(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, waktu text, status text)");
        db.execSQL("INSERT INTO session(id, login) VALUES (1,'kosong')");
        db.execSQL("INSERT INTO tb_anggota(id, nama, username, password, role) VALUES (1,'Admin Organisasi','adminaplikasi', '12345', 'admin')");
        db.execSQL("INSERT INTO tb_anggota(id, nama, username, password, role) VALUES (2,'Pengguna Satu','user123', '12345', 'anggota')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int db1, int db2) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS tb_anggota");
        onCreate(db);
    }

    public Boolean checkSession(String sessionValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{sessionValues});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //upgrade session
    public Boolean upgradeSession(String sessionValues, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", sessionValues);
        long update = db.update("session", contentValues, "id="+id, null);
        if (update == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_anggota WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    //Cek Role
    public String checkRole(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM tb_anggota WHERE username = ?", new String[]{username});
        String role = null;
        if(cursor.moveToFirst()){
            role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
        }
        cursor.close();
        return role;
    }

    public String saveLoggedInUserId(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER_ID, username);
        editor.apply();
        return username;
    }

    public String getLoggedInUserId() {
        return sharedPreferences.getString(PREF_USER_ID, null);
    }

    public boolean isUserIdExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM tb_anggota WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }

        return count > 0;
    }

    public UserData getUserData(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_anggota WHERE username = ?", new String[]{username});
        UserData userData = null;
        if(cursor.moveToFirst()){
            String valueNama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
            String valueUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String valuePassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String valueRole = cursor.getString(cursor.getColumnIndexOrThrow("role"));
            userData = new UserData(valueNama, valueUsername, valuePassword, valueRole);
        }
        cursor.close();
        return userData;
    }

    public boolean updateUserData(String oldUname,  String nama, String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("username", username);
        values.put("password",password);
        values.put("role",role);
        int rowsAffected = db.update("tb_anggota", values, "username" + "=?", new String[]{oldUname});
        db.close();

        return rowsAffected > 0;
    }

    public int getRowCount(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return count;
    }

    public Boolean insertAnggota(String nama, String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("role", role);
        long insert = db.insert("tb_anggota", null, contentValues);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean insertKeuangan(String nama, String keterangan, String nominal, String jenis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("keterangan", keterangan);
        contentValues.put("nominal",nominal);
        contentValues.put("jenis",jenis);
        long insert = db.insert("tb_keuangan", null, contentValues);
        if (insert == -1){
            return false;
        }else {
            return true;
        }
    }

    public double getTotalPemasukanByJenis(String jenis) {
        double total = 0;
        String query = "SELECT " + "nominal" + " FROM " + "tb_keuangan" +
                " WHERE " + "jenis" + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{jenis});

        if (cursor.moveToFirst()) {
            do {
                String pemasukanText = cursor.getString(cursor.getColumnIndexOrThrow("nominal"));
                double pemasukanValue = convertToDouble(pemasukanText); // Konversi teks ke angka
                total += pemasukanValue;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return total;
    }

    public ArrayList<HashMap<String, String>> getFinance(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_keuangan ORDER BY id DESC",null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("keterangan", cursor.getString(2));
                map.put("nominal", cursor.getString(3));
                map.put("jenis", cursor.getString(4));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getAnggota(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_anggota WHERE role != 'calon'",null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("username", cursor.getString(2));
                map.put("password", cursor.getString(3));
                map.put("role", cursor.getString(4));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getAnggotaApprove(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_anggota WHERE role == 'calon'",null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("username", cursor.getString(2));
                map.put("password", cursor.getString(3));
                map.put("role", cursor.getString(4));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private double convertToDouble(String text) {
        try {
            return Double.parseDouble(text.replace(",", "")); // Hapus tanda koma jika ada
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public class UserData{
        private String valueNama;
        private String valueUsername;
        private String valuePassword;
        private String valueRole;

        public String getValueNama() {
            return valueNama;
        }

        public String getValueUsername() {
            return valueUsername;
        }

        public String getValuePassword() {
            return valuePassword;
        }

        public String getValueRole() {
            return valueRole;
        }

        public UserData (String valueNama, String valueUsername, String valuePassword, String valueRole){
            this.valueNama = valueNama;
            this.valueUsername = valueUsername;
            this.valuePassword = valuePassword;
            this.valueRole = valueRole;
        }
    }
}
