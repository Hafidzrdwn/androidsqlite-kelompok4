package com.example.androidsqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.ArrayList;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class DatabaseManager {

    private static final String ROW_ID = "_id";
    private static final String ROW_NAMA = "nama";
    private static final String ROW_TELP = "telp";
    private static final String NAMA_DB = "database1";
    private static final String NAMA_TABEL = "tblpelanggan";
    private static final int DB_VERSION = 3;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + NAMA_TABEL +
            " (" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ROW_NAMA + " TEXT, " +
            ROW_TELP + " TEXT)";

    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

public DatabaseManager(Context ctx) {
    this.context = ctx;
    dbHelper = new DatabaseOpenHelper(context);
    setDb(dbHelper.getWritableDatabase());
}

public SQLiteDatabase getDb() {
    return db;
}
public void setDb(SQLiteDatabase db) {
    this.db = db;
}
private static class DatabaseOpenHelper extends SQLiteOpenHelper {

    public DatabaseOpenHelper(Context context) {
        super(context, NAMA_DB, null, DB_VERSION);
        //TODO Auto-Generated constructor stub 39:
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO Auto-Generated Method Stub
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        //TODO Auto-Generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+NAMA_DB);
        onCreate(db);
    }
}

public void close() {
    dbHelper.close();
}

//private String sSqlQuery;
    public void UpdateRecord (int iId, String sName, String stelp)
    {
        ContentValues values = new ContentValues();
        values.put(ROW_ID, iId);
        values.put(ROW_NAMA, sName);
        values.put(ROW_TELP, stelp);
        try {
            db.update(NAMA_TABEL, values, ROW_ID+"="+iId, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString()); e.printStackTrace();
        }
    }
    //menghapus data
    public void DeleteRecord (int Iid)
    {
        ContentValues values = new ContentValues();
        values.put(ROW_ID, Iid);
        try {
            db.delete(NAMA_TABEL, ROW_ID+"="+Iid, null);
/*sSQLQuery = "DELETE FROM datadiri " +
"WHERE ROW_ID='" + iId + "';";
db.execSQL(sSQLQuery);*/
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString()); e.printStackTrace();
        }
    }
    //menambah data
    public void addRow(int Iid, String anama, String atelp) {
        ContentValues values = new ContentValues();
        values.put(ROW_ID, Iid);
        values.put(ROW_NAMA, anama);
        values.put(ROW_TELP, atelp);
        try {
            db.insert(NAMA_TABEL, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString()); e.printStackTrace();
        }
    }
    //menampung data hasil select, dan ditampung pada arraylist
    public ArrayList<ArrayList<Object>> ambilSemuaBaris() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur;
        try {
            cur = db.query(NAMA_TABEL,
                    new String[] { ROW_ID, ROW_NAMA, ROW_TELP }, null, null,
                    null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getLong(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Database ERROR", e.toString());
        }
        return dataArray;
    }
}



