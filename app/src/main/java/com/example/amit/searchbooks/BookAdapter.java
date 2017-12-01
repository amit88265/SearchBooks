package com.example.amit.searchbooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AMIT on 03-Nov-17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, ArrayList<Book> books ) {
        super(context, 0, books);
    }

    public View getView(int position, View view , ViewGroup parent){
        View listItem =view;
        if(listItem==null){
            listItem= LayoutInflater.from(getContext()).inflate(R.layout.book_list_item,parent,false);
        }
        Book book=getItem(position);
        TextView bookName=listItem.findViewById(R.id.BookName);
               bookName.setText(book.getNameOfBook());
        return  listItem;
    }
}
