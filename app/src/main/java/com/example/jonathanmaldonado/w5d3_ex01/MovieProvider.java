package com.example.jonathanmaldonado.w5d3_ex01;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.jonathanmaldonado.w5d3_ex01.DataBase.DBHelper;
import com.example.jonathanmaldonado.w5d3_ex01.DataBase.FeedReaderContract;

/**
 * Created by Jonathan Maldonado on 8/16/2017.
 */

public class MovieProvider extends ContentProvider {

    private static final int GENRE=100;
    private static final int GENRE_ID=101;
    private static final int MOVIE=200;
    private static final int MOVIE_ID=201;

    private static final UriMatcher uriMatcher= buildUriMatcher();

    private DBHelper dbHelper;

    public static UriMatcher buildUriMatcher(){
        String content = FeedReaderContract.CONTENT_AUTHORITY;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(content, FeedReaderContract.PATH_GENRE, GENRE);
        matcher.addURI(content, FeedReaderContract.PATH_GENRE+"/#", GENRE_ID);
        matcher.addURI(content, FeedReaderContract.PATH_MOVIE, MOVIE);
        matcher.addURI(content, FeedReaderContract.PATH_MOVIE+"/#", MOVIE_ID);

        return matcher;
    }



    @Override
    public boolean onCreate() {
        dbHelper=new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor returnCursor;
        long _id=0;
        switch (uriMatcher.match(uri)){

            case GENRE:
                returnCursor=db.query(
                        FeedReaderContract.GenreEntry.TABLE_GENRE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder

                );
                break;
            case GENRE_ID:
                _id = ContentUris.parseId(uri);
                returnCursor =db.query(
                        FeedReaderContract.GenreEntry.TABLE_GENRE,
                        projection,
                        FeedReaderContract.GenreEntry._ID + " =?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE:
                returnCursor=db.query(
                        FeedReaderContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder

                );
                break;
            case MOVIE_ID:
                _id = ContentUris.parseId(uri);
                returnCursor =db.query(
                        FeedReaderContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        FeedReaderContract.MovieEntry._ID + " =?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);

        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)){
            case GENRE:
                return FeedReaderContract.GenreEntry.CONTENT_TYPE;
            case GENRE_ID:
                return FeedReaderContract.GenreEntry.CONTENT_ITEM_TYPE;
            case MOVIE:
                return FeedReaderContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return FeedReaderContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri:"+uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch (uriMatcher.match(uri)){
            case GENRE:
                _id  = db.insert(FeedReaderContract.GenreEntry.TABLE_GENRE, null, contentValues);
                if(_id > 0){
                    returnUri = FeedReaderContract.GenreEntry.buildGenreUri(_id);
                }else{
                    throw new UnsupportedOperationException("Unable to insert into: "+uri);
                }
                break;
            case MOVIE:
                _id  = db.insert(FeedReaderContract.MovieEntry.TABLE_MOVIES, null, contentValues);
                if(_id > 0){
                    returnUri = FeedReaderContract.MovieEntry.buildMovieUri(_id);
                }else{
                    throw new UnsupportedOperationException("Unable to insert into: "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknow uria: "+ uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final  SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;
        switch (uriMatcher.match(uri)){
            case GENRE:
                rows= db.delete(FeedReaderContract.GenreEntry.TABLE_GENRE, selection,selectionArgs);
                break;
            case MOVIE:
                rows= db.delete(FeedReaderContract.MovieEntry.TABLE_MOVIES, selection,selectionArgs);
                break;
            default:
                throw  new UnsupportedOperationException("Unknown uri: "+ uri);
        }

        if(selection ==null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {


        final  SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;
        switch (uriMatcher.match(uri)){
            case GENRE:
                rows= db.update(FeedReaderContract.GenreEntry.TABLE_GENRE, values,selection,selectionArgs);
                break;
            case MOVIE:
                rows= db.update(FeedReaderContract.MovieEntry.TABLE_MOVIES, values,selection,selectionArgs);
                break;
            default:
                throw  new UnsupportedOperationException("Unknown uri: "+ uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }





}
