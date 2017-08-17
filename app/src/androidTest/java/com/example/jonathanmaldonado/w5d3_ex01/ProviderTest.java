package com.example.jonathanmaldonado.w5d3_ex01;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.example.jonathanmaldonado.w5d3_ex01.DataBase.FeedReaderContract;

/**
 * Created by Jonathan Maldonado on 8/17/2017.
 */

public class ProviderTest extends AndroidTestCase{

    private static final String TEST_GENRE_NAME="Family";
    private static final String TEST_UPDATE_GENRE_NAME="SciFi";
    private static final String TEST_MOVIE_NAME = "Back to the Future";
    private static final String TEST_UPDATE_MOVIE_NAME="Back to the future II";
    private static final String TEST_MOVIE_RELEASE_DATE = "1985-09-11";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testDeleteAllRecords();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        testDeleteAllRecords();
    }

    public void testDeleteAllRecords(){
        mContext.getContentResolver().delete(
                FeedReaderContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                FeedReaderContract.GenreEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                FeedReaderContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0,cursor.getCount());

        cursor = mContext.getContentResolver().query(
                FeedReaderContract.GenreEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0,cursor.getCount());

        cursor.close();

    }

    public void testGetType(){
        //vnd.android.cursor.dir/com.../genre
        String type = mContext.getContentResolver().getType(FeedReaderContract.GenreEntry.CONTENT_URI);
        assertEquals(FeedReaderContract.GenreEntry.CONTENT_TYPE,type);

        type = mContext.getContentResolver().getType(FeedReaderContract.GenreEntry.buildGenreUri(0));
        assertEquals(FeedReaderContract.GenreEntry.CONTENT_ITEM_TYPE,type);

        type = mContext.getContentResolver().getType(FeedReaderContract.MovieEntry.CONTENT_URI);
        assertEquals(FeedReaderContract.MovieEntry.CONTENT_TYPE,type);

        type = mContext.getContentResolver().getType(FeedReaderContract.MovieEntry.buildMovieUri(0));
        assertEquals(FeedReaderContract.MovieEntry.CONTENT_ITEM_TYPE,type);

    }
}
