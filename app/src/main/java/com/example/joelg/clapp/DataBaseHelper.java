package com.example.joelg.clapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by joelg on 9/11/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    // deceleration of table info and table colum information
    String DB_PATH = null;
    private static String DB_NAME = "ListTemplate";
    private static String DB_TABLE = "infoTable";
    private static String DB_ID = "_id";
    private static String DB_JOB_INFO = "jobInfo";
    private static String DB_IS_DONE = "isdone";
    private SQLiteDatabase sqLiteDatabase;
    private final Context ListContext;





    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */

    public DataBaseHelper (Context context) {
        super (context , DB_NAME, null, 1);
        this.ListContext = context;
        this.DB_PATH = "/data/data" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }

  public void createDataBase() throws IOException {
      boolean dbExist = checkDatabase();


   if (dbExist) {
   // do nothing - database already exist
  } else {


       this.getReadableDatabase();
       try {

           copyDataBase();
       } catch (IOException e){

           // database does't exist yet.

           throw new Error ("Error copy database");
       }
       }
  }

  private boolean checkDatabase(){
      SQLiteDatabase checkDB = null;

      try {
          String myPath = DB_PATH + DB_NAME;
          checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
      } catch (SQLiteException e){

      }
  if (checkDB !=null){

      checkDB.close();
  }
   return checkDB != null ? true : false;
}
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */

     private void copyDataBase() throws IOException {
         //Open your local db is the input stream
         InputStream myInput = ListContext.getAssets().open(DB_NAME);
         // Path to the just created empty db
         String outFileName = DB_PATH + DB_NAME;
         // open the empty db as the output stream
         OutputStream myOutput = new FileOutputStream(outFileName);
         // transfer bytes from the inputfile to the outputfile
         byte [] buffer = new byte[1024];
         int length;
         while ((length = myInput.read(buffer))>0) {
                myOutput.write(buffer, 0, length);
         }
         myOutput.flush();
         myOutput.close();
         myInput.close();
              }

     public void openDataBase() throws SQLiteException {

           String myPath = DB_PATH + DB_NAME;
           sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
         //Opens the database
     }


     /*
     *this is where the getJoblist , is set up.
     * */
   public ArrayList<String> getJobList(){
       ArrayList<String> JobList = new ArrayList<>();
       SQLiteDatabase db  = this.getReadableDatabase();
       Cursor cursor = db.query(DB_TABLE,new String[]{DB_JOB_INFO},null,null,null,null,null);
       while (cursor.moveToFirst()){
           int index = cursor.getColumnIndex(DB_JOB_INFO);
           JobList.add(cursor.getString(index));
       }
       cursor.close();
       db.close();
       return JobList;
   }



     @Override
        public synchronized void close(){
         if (sqLiteDatabase != null)
         sqLiteDatabase.close();
     }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
