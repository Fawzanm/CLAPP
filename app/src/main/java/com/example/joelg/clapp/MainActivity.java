package com.example.joelg.clapp;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    // Import DBhelper Class

    ArrayAdapter<String> mAdapter;
    ListView NextTask;
    DataBaseHelper myDbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDbHelper = new DataBaseHelper(this);


      // opens the database
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


        NextTask = (ListView) findViewById(R.id.NextTask);
        LoadJobList();
    }


    //
    //public double RunningTime() {
      //  Date currentTime = Calendar.getInstance().getTime();
    //}

    private void LoadJobList() {
        ArrayList<String> JobList = myDbHelper.getTaskList();
        if(mAdapter==null){
            mAdapter = new ArrayAdapter<>(this,R.layout.row,R.id.Job_Detail,JobList);
            NextTask.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(JobList);
            mAdapter.notifyDataSetChanged();

        }
    }

    }





