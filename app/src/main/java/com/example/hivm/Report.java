package com.example.hivm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Report extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {
    private static final int INVENTORY_LOADER = 1;

    ReportAdapter mReportAdapter;


    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                InventoryContract.InventoryEntry._ID,   //IMPORTANT: without this onLoadFinished fail(even though i don't want to display it in list_items inside ListView)
                InventoryContract.InventoryEntry.COLUMN_ITEM_IMAGE,
                InventoryContract.InventoryEntry.COLUMN_ITEM_NAME,
                InventoryContract.InventoryEntry.COLUMN_ITEM_PRICE,
                InventoryContract.InventoryEntry.COLUMN_TOTAL,
                InventoryContract.InventoryEntry.COLUMN_ITEM_QUANTITY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this, InventoryContract.InventoryEntry.CONTENT_URI, projection, null, null, null);
    }


    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //update with new cursor containing updated Inventory data
        mReportAdapter.swapCursor(data);
    }


    public void onLoaderReset(Loader<Cursor> loader) {
        //Callbacks called when data needs to be deleted
        mReportAdapter.swapCursor(null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ListView inventoryListView  = (ListView)findViewById(R.id.report_view);

        getSupportActionBar().setTitle("Report for PCH");
        mReportAdapter = new ReportAdapter(this, null);
        inventoryListView.setAdapter(mReportAdapter);

        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);


    }

}

