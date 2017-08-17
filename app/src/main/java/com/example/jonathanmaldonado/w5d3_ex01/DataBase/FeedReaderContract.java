package com.example.jonathanmaldonado.w5d3_ex01.DataBase;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jonathan Maldonado on 7/26/2017.
 */

public final class FeedReaderContract {

    private FeedReaderContract(){

    }

    public static final String CONTENT_AUTHORITY="com.example.jonathanmaldonado.w5d3_ex01.DataBase";
    public static final String PATH_MOVIE="movie";
    public static final String PATH_GENRE="genre";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static class MovieEntry implements BaseColumns{

        // content://com.example.jonathanmaldonado.w5d3_ex01.DataBase/movie
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE= "vnd.android.cursor.dir/"+CONTENT_URI+ "/"+PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/"+CONTENT_URI+"/"+PATH_MOVIE;

        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }



        public static final String TABLE_MOVIES = "Movies";
        public static String COLUMN_MOVIES_NAME = "name";
        public static String COLUMN_MOVIES_DATE = "date";
        public static String COLUMN_MOVIES_GENRE_ID = "genre_id";

    }
    public static class GenreEntry implements BaseColumns{

        // content://com.example.jonathanmaldonado.w5d3_ex01.DataBase/movie
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_GENRE).build();

        public static final String CONTENT_TYPE= "vnd.android.cursor.dir/"+CONTENT_URI+ "/"+PATH_GENRE;

        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/"+CONTENT_URI+"/"+PATH_GENRE;

        public static Uri buildGenreUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }


        public static final String TABLE_GENRE = "Genre";
        public static String COLUMN_GENRE_NAME = "name";
    }

}

