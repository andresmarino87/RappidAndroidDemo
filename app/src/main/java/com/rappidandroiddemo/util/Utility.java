package com.rappidandroiddemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Andres on 3/15/16.
 */
public class Utility {

    public static JSONObject readJsonFromUrl(String urlString) {
        JSONObject json = null;
        URL url;
        HttpURLConnection urlConnection = null;
        String line,jsonText;
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                jsonText = response.toString();
                json = new JSONObject(jsonText);
            }else{
                json = new JSONObject();
            }
        } catch (MalformedURLException e) {
            json = new JSONObject();
            e.printStackTrace();
        } catch (IOException e) {
            json = new JSONObject();
            e.printStackTrace();
        } catch (JSONException e) {
            json = new JSONObject();
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return json;
    }

    public static boolean checkConn(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null) {
            return false;
        }
        if (!i.isConnected()) {
            return false;
        }
        if (!i.isAvailable()) {
            return false;
        }
        return true;
    }

    public static void CopyStream(InputStream is, OutputStream os){
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static InputStream fetch(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = null;
        urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        return in;
    }

    public static void saveJSONArray(Context c, String prefName, String key, JSONArray array) {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, array.toString());
        editor.commit();
    }

    public static JSONArray loadJSONArray(Context c, String prefName, String key) throws JSONException {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        return new JSONArray(settings.getString(key, null));
    }
}
