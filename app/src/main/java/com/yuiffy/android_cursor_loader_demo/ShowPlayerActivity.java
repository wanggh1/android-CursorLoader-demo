
package com.yuiffy.android_cursor_loader_demo;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowPlayerActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PLAYER_LOADER = 0;
    PlayerDbHelper mDbHelper;
    SQLiteDatabase db;

    ListView lv;
    SimpleCursorAdapter mAdapter;

    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
    String[] projection = {
            MyContract.PlayerEntry._ID,
            MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME,
            MyContract.PlayerEntry.COLUMN_NAME_SEX,
            MyContract.PlayerEntry.COLUMN_NAME_AGE
    };
    // How you want the results sorted in the resulting Cursor
    String sortOrder =
            MyContract.PlayerEntry._ID /*+ " DESC"*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_player);

        mDbHelper = new PlayerDbHelper(this);
        db = mDbHelper.getReadableDatabase();
        lv = (ListView) findViewById(R.id.lv_show_players);

        mAdapter = new SimpleCursorAdapter(ShowPlayerActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                null,
                new String[]{MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME},
                new int[]{android.R.id.text1},
                0);
        lv = (ListView) findViewById(R.id.lv_show_players);
        lv.setAdapter(mAdapter);
        getLoaderManager().initLoader(PLAYER_LOADER, null, this);
        Log.d("WOW!", "create over~");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    /*
     * Takes action based on the ID of the Loader that's being created
     */

        Uri uri = Uri.parse("content://yui_player");

        switch (id) {
            case PLAYER_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        ShowPlayerActivity.this,   // Parent activity context
                        uri,        // Table to query
                        projection,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        sortOrder
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
        Log.d("Loader", "wow,over!");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        Log.v("Loader", "onLoaderReset" + loader);
    }
}
