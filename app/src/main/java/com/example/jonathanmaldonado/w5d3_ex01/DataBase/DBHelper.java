package com.example.jonathanmaldonado.w5d3_ex01.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jonathanmaldonado.w5d3_ex01.DataBase.FeedReaderContract.MovieEntry;
import com.example.jonathanmaldonado.w5d3_ex01.DataBase.FeedReaderContract.GenreEntry;


/**
 * Created by Jonathan Maldonado on 7/26/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =15;
    public static final String DATABASE_NAME="movie.db";

    public static  final String SQL_CREATE_MOVIES = "CREATE TABLE "+
            MovieEntry.TABLE_MOVIES+" ("+
            MovieEntry._ID+" INTEGER PRIMARY KEY,"+
            MovieEntry.COLUMN_MOVIES_NAME+" TEXT,"+
            MovieEntry.COLUMN_MOVIES_DATE+" TEXT,"+
            //MovieEntry.COLUMN_MOVIES_GENRE_ID+" INTEGER )";
            //TASK_CAT + " integer,"
            //+ " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";
            MovieEntry.COLUMN_MOVIES_GENRE_ID+" INTEGER, FOREIGN KEY ("+MovieEntry.COLUMN_MOVIES_GENRE_ID+") REFERENCES "+GenreEntry.TABLE_GENRE+"("+GenreEntry._ID+"));";



    public static  final String SQL_CREATE_GENRE = "CREATE TABLE "+
            GenreEntry.TABLE_GENRE+" ("+
            GenreEntry._ID+" INTEGER PRIMARY KEY,"+
            GenreEntry.COLUMN_GENRE_NAME+" TEXT)";

    public static final String SQL_DELETE_MOVIE_ENTRIES= "DROP TABLE IF EXISTS "+ MovieEntry.TABLE_MOVIES;
    public static final String SQL_DELETE_GENRE_ENTRIES= "DROP TABLE IF EXISTS "+ GenreEntry.TABLE_GENRE;

    public DBHelper (Context context){

        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_GENRE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_MOVIE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_GENRE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
