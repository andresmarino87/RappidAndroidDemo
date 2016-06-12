package com.rappidandroiddemo.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rappidandroiddemo.R;
import com.rappidandroiddemo.adapter.CategoryAdapter;
import com.rappidandroiddemo.item.ApplicationItem;
import com.rappidandroiddemo.item.CategoryItem;
import com.rappidandroiddemo.util.AppConstant;
import com.rappidandroiddemo.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView categoriesView;
    private ProgressBar progressBar;
    private CategoryAdapter adapter;
    private ArrayList<CategoryItem> categoryArrayList;
    private ArrayList<ApplicationItem> applicationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        final boolean isPotrait = getResources().getBoolean(R.bool.portrait_only);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        categoriesView = (RecyclerView)findViewById(R.id.categoryListView);
        categoryArrayList = new ArrayList<>();
        applicationArrayList = new ArrayList<>();
        //Set screen orientation & layoutmanager
        if(isPotrait){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            categoriesView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            categoriesView.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        }

        adapter = new CategoryAdapter(context, categoryArrayList, applicationArrayList);
        categoriesView.setAdapter(adapter);

        //Check if have WRITE_EXTERNAL_STORAGE permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.PERMISSIONS_REUQES_WRITE_EXTERNAL_STORAGE);
        }
        new JSONGetUrl().execute(AppConstant.ApiUrl);
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public class JSONGetUrl extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(LinearLayout.VISIBLE);
            categoriesView.setVisibility(LinearLayout.GONE);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            JSONObject result = null;
            String url = urls[0];
            if(Utility.checkConn(context)) {
                try {
                    result = Utility.readJsonFromUrl(url);
                    result.put("isConnectionValid", true);
                } catch (JSONException e) {
                    Log.e(context.getApplicationInfo().packageName, "Error doInBackground JSONException " + e);
                }
            }else {
                try {
                    result = new JSONObject();
                    result.put("isConnectionValid", false);
                }catch(JSONException e){
                    Log.e(context.getApplicationInfo().packageName, "Error doInBackground JSONException " + e);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            progressBar.setVisibility(LinearLayout.GONE);
            categoriesView.setVisibility(LinearLayout.VISIBLE);
            boolean hasConnection=false;
            JSONArray entry;
            try{
                hasConnection = result.getBoolean("isConnectionValid");
            }catch(JSONException e){}

            if(hasConnection){
                try {
                    entry = (result.getJSONObject("feed")).getJSONArray("entry");
                    DataLoaderAux(entry);
                    Utility.saveJSONArray(context,AppConstant.AppName,"AppsEntries",entry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setTitle(R.string.no_connection);
                dialogBuilder.setMessage(R.string.no_connection_message);
                dialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new JSONGetUrl().execute(AppConstant.ApiUrl);
                    }
                });
                dialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        JSONArray entry;
                        try {
                            entry = Utility.loadJSONArray(context,AppConstant.AppName,"AppsEntries");
                            DataLoaderAux(entry);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                dialogBuilder.show();
            }
        }
    }

    private void DataLoaderAux(JSONArray entry) throws JSONException {
        JSONObject aux;
        CategoryItem item;
        ApplicationItem appItem;
        String name, imageUrl, summary;

        for(int i = 0; i < entry.length(); i++){
            aux = entry.getJSONObject(i);
            name = (aux.getJSONObject("im:name")).getString("label");
            imageUrl = ((JSONObject)((aux.getJSONArray("im:image")).get(2))).getString("label");
            summary  = (aux.getJSONObject("summary")).getString("label");
            aux = (aux.getJSONObject("category")).getJSONObject("attributes");
            item = new CategoryItem(
                    aux.getString("im:id"),
                    aux.getString("term"),
                    aux.getString("scheme"),
                    aux.getString("label")
            );
            appItem = new ApplicationItem(
                    name,
                    imageUrl,
                    summary,
                    item
            );
            applicationArrayList.add(appItem);
            if(!categoryArrayList.contains(item)) {
                categoryArrayList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
