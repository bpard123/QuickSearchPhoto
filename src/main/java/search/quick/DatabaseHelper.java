package search.quick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Asus on 12-03-2019.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String DATABASE_NAME="imagesDB";
    private final int VERSION=1;
    private Context context;
    private final String TABLE_NAME="images";
    public DatabaseHelper(Context context) {
        super(context, "imagesDB", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"(id integer primary key autoincrement,image blob,tag text);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+";");
        onCreate(sqLiteDatabase);
    }

    public void insertPhoto(byte img[],String tag){
        SQLiteDatabase dbHelper=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("image",img);
        contentValues.put("tag",tag);
        dbHelper.insert(TABLE_NAME,null,contentValues);
    }

    public ArrayList<byte[]> retrieveImage(String tag){
        SQLiteDatabase dbHelper=this.getReadableDatabase();

        String query="select * from "+TABLE_NAME+" where tag=\""+tag+"\";";
        Cursor cursor=dbHelper.rawQuery(query,null);
        ArrayList<byte[]> list=new ArrayList<>();

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            list.add(cursor.getBlob(cursor.getColumnIndex("image")));
            cursor.moveToNext();
        }


        return list;
    }







}
