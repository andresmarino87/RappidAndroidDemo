package com.rappidandroiddemo.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rappidandroiddemo.R;
import com.rappidandroiddemo.item.ApplicationItem;
import com.rappidandroiddemo.util.DrawableManager;

public class AppDetailsActivity extends AppCompatActivity {
    private DrawableManager DM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        //Set screen orientation
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DM=new DrawableManager(this,this.getResources().getDrawable(R.mipmap.ic_launcher));

        Bundle bundle = getIntent().getExtras();
        ApplicationItem appItem = bundle.getParcelable("appItem");

        final ImageView app_image = (ImageView)findViewById(R.id.app_image);
        final TextView app_name = (TextView)findViewById(R.id.app_name);
        final TextView app_summary = (TextView)findViewById(R.id.app_summary);
        app_name.setText(appItem.getName());
        app_summary.setText(appItem.getSummary());
        DM.DisplayImage(appItem.getImage(), app_image, 300 , 300);
        getSupportActionBar().setTitle(appItem.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
