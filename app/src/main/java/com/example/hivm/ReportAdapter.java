package com.example.hivm;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.ContentHandler;
import java.util.ArrayList;

public class ReportAdapter extends CursorAdapter {


    private final Report activity; //IMPORTANT: for calling update() in MainActivity

    public ReportAdapter(Report context, Cursor c) { //IMPORTANT: MainActivity context is needed as I will be calling update() if sale button is clicked
        super(context, c, 0/*flags*/);
        activity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_repord, viewGroup, false);
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        ImageView reportImage = (ImageView) view.findViewById(R.id.report_image);
        TextView drug_name = (TextView) view.findViewById(R.id.drug_name);
        final TextView total = (TextView) view.findViewById(R.id.tolal);
        final TextView sold_drug = (TextView) view.findViewById(R.id.sold_drug);
        final TextView avail_drug = (TextView) view.findViewById(R.id.avail_drug);


        reportImage.setImageURI(Uri.parse( //setImage corresponding to Uri
                cursor.getString( //get Value
                        cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM_IMAGE //get Column Index
                        )
                )
        ));

         String drug_names  = cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM_NAME));
                drug_name.setText("Report for "+drug_names);

        int quantity = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_TOTAL));
        int avail = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM_QUANTITY));
        int price = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM_PRICE));

        int sold = (quantity-avail);
        double totals = (price*sold);

        sold_drug.setText(String.valueOf(sold)+" "+drug_names+" were sold");
        avail_drug.setText(String.valueOf(avail)+" "+drug_names+" are available");
        total.setText("Total amount M"+String.valueOf(totals));


    }


    }
