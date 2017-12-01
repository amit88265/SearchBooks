package com.example.amit.searchbooks;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class BookSearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book> > {

    private EditText mInput;
    private ListView mList;
    private Button mSearch;
    private String mUrl1="https://www.googleapis.com/books/v1/volumes?q=";
    private String mUrl2="&maxResults=20",mUrl="";
BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        mInput= findViewById(R.id.searchBox);
        mList=findViewById(R.id.list);

        bookAdapter=new BookAdapter(this,new ArrayList<Book>());
        mList.setAdapter(bookAdapter);

        mSearch=findViewById(R.id.searchButton);

        Log.d("vivz","onCreate");

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUrl=mUrl1+mInput.getText()+mUrl2;
                mList.setVisibility(View.GONE);
                Log.d("vivz","click"+mUrl);
                doThis();

            }
        });


    }
public void doThis() {

    LoaderManager loaderManager = getLoaderManager();
    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected() && loaderManager.getLoader(1) == null) {
        loaderManager.initLoader(1, null, this);
    } else if (networkInfo != null && networkInfo.isConnected() && loaderManager.getLoader(1) != null) {
        loaderManager.restartLoader(1, null, this);
    }
    else{
        Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
    }

}
    @Override
    public Loader<ArrayList<Book> >onCreateLoader(int i, Bundle bundle) {
        Log.d("vivz","onCreateLoader");
        return new BookLoader(BookSearchActivity.this,mUrl);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book> >loader, ArrayList<Book> book) {
 bookAdapter.clear();
ProgressBar progressBar=findViewById(R.id.progressBar);
progressBar.setVisibility(View.GONE);
mList.setVisibility(View.VISIBLE);
 if(book!=null&&!book.isEmpty()){
     bookAdapter.addAll(book);
 }
        Log.d("vivz","LoadFinished");

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book> >loader)
    {
        Log.d("vivz","reset");
bookAdapter.clear();
    }
}
