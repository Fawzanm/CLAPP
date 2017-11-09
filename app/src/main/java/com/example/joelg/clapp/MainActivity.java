package com.example.joelg.clapp;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // opens the database
        DataBaseHelper myDbHelper;
        myDbHelper = new DataBaseHelper(this);


        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("unable to create database");

        }
        try {
            myDbHelper.openDataBase();

        } catch (SQLiteException sqle) {

            throw sqle;

        }



    }
}




