package com.example.joelg.clapp;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    // Import DBhelper Class
    DataBaseHelper dataBaseHelper;
    ArrayAdapter<String> mAdapter;
    ListView NextTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // opens the database
        DataBaseHelper myDbHelper;
        myDbHelper = new DataBaseHelper(this);
        NextTask = (ListView) findViewById(R.id.NextTask);

        myDbHelper.openDataBase();
        LoadJobList();
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
        private void LoadJobList() {
        ArrayList<String> JobList = dataBaseHelper.getJobList();
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





