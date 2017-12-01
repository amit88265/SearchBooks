package com.example.amit.searchbooks;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by AMIT on 03-Nov-17.
 */

public class FetchData {
    static String jsonrepo=" ";
    static ArrayList<Book> fetchData(String url){
        Log.d("vivz",url+"in process");
        ArrayList<Book > data=null;

        try {
            URL url1=new URL(url);
            jsonrepo=makeHttpRequest(url1);
            data=parseJson(jsonrepo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static String makeHttpRequest(URL url){
        String json=" ";
        if (url==null){
            return json;
        }
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        try {
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode()==200){
                inputStream=httpURLConnection.getInputStream();
                json=readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (httpURLConnection!=null)
                httpURLConnection.disconnect();
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }
    private static String readFromStream(InputStream in){
        StringBuilder out=new StringBuilder();
        if (in!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(in, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String l=null;
            try {
                l=bufferedReader.readLine();
                while (l!=null){
                    out.append(l);
                    l=bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return out.toString();
    }
    private static ArrayList<Book >parseJson(String json){
        ArrayList<Book> bookArrayList=new ArrayList<>();
 if (TextUtils.isEmpty(json)){
     return null;
 }
        try {
            JSONObject root=new JSONObject(json);
            JSONArray Items=root.getJSONArray("items");
            if (Items.length()>0){
                for (int i=0;i<Items.length();i++){
                    JSONObject info=Items.getJSONObject(i);
                    JSONObject volInfo=info.getJSONObject("volumeInfo");
                    String title=volInfo.getString("title");
                    bookArrayList.add(new Book(title));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return bookArrayList;
    }
}
