package com.example.jonathanmaldonado.w5d3_ex01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.jonathanmaldonado.w5d3_ex01.DataBase.DBHelper;
import com.example.jonathanmaldonado.w5d3_ex01.DataBase.FeedReaderContract;

public class MainActivity extends AppCompatActivity {
    private static final String TAG=MainActivity.class.getSimpleName()+"_TAG";


    private DBHelper helper;
    private SQLiteDatabase database;
    private TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        helper = new DBHelper(this);
        database = helper.getWritableDatabase();
        Log.d(TAG, "onCreate: ");
        resultTV= (TextView) findViewById(R.id.result_TV);
        saveRecord();
        readRecord();
    }



    private void saveRecord(){




            String title ="MovieName";
            String subtitle= "MovieDate";
            ContentValues values= new ContentValues();

            values.put(FeedReaderContract.MovieEntry.COLUMN_MOVIES_NAME,title);
            values.put(FeedReaderContract.MovieEntry.COLUMN_MOVIES_DATE,subtitle);
            long recordId = database.insert(FeedReaderContract.MovieEntry.TABLE_MOVIES,null,values);
            if (recordId>0){
                Log.d(TAG, "Record Saved");
                //saveNoteResult.setText("");
                //saveNoteResult.setText("Note Saved: "+" Title: "+ saveNoteTitleET.getText().toString()+ " Content: "+ saveNoteContentET.getText().toString());

            }


    }


    private void readRecord(){

        String[] projection={
                FeedReaderContract.MovieEntry._ID,
                FeedReaderContract.MovieEntry.COLUMN_MOVIES_NAME,
                FeedReaderContract.MovieEntry.COLUMN_MOVIES_DATE
        };
        String selection = FeedReaderContract.MovieEntry.COLUMN_MOVIES_NAME+"= ?";
        String[] selectionArg = {
                "Record title"
        };
        String sortOtder = FeedReaderContract.MovieEntry.COLUMN_MOVIES_DATE+"DESC";

        Cursor cursor = database.query(
                FeedReaderContract.MovieEntry.TABLE_MOVIES,   //Table
                projection,             //Projection
                null,                   //Selection (WHERE)
                null,                   //Values for selection
                null,                   //Group by
                null,                   //Filters
                null                    //Sort order

        );



        String newMessage="";
        while (cursor.moveToNext()){
            long entryID =cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.MovieEntry._ID));
            String entryTitle=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.MovieEntry.COLUMN_MOVIES_NAME));
            String entrySubTitle=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.MovieEntry.COLUMN_MOVIES_DATE));





            newMessage += "Note id: "+ entryID+ " Title: "+ entryTitle+ " Content "+entrySubTitle+"\n";

        }

        resultTV.setText("");
        resultTV.setText(newMessage);

    }









}
