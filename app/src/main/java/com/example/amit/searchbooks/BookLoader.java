package com.example.amit.searchbooks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by AMIT on 03-Nov-17.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book> > {
    String mUrl;
    public BookLoader(Context context,String url) {
        super(context);
        Log.d("vivz","BookLoaderConstructor");
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d("vivz","onStartLoad");
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        ArrayList<Book > result =FetchData.fetchData(mUrl);
        Log.d("vivz","LoadInBackground");
        return result;
    }
}
