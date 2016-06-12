package com.rappidandroiddemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rappidandroiddemo.R;
import com.rappidandroiddemo.activity.AppDetailsActivity;
import com.rappidandroiddemo.item.ApplicationItem;
import com.rappidandroiddemo.util.DrawableManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 3/16/16.
 */
public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {
    private List<ApplicationItem> applications;
    private Context context;
    private DrawableManager DM;

    public ApplicationAdapter(Context context, ArrayList<ApplicationItem> applications) {
        this.applications = applications;
        this.context = context;
        this.DM = new DrawableManager(context, ResourcesCompat.getDrawable(context.getResources(),R.mipmap.ic_launcher,null));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.application_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ApplicationItem app = applications.get(position);
        holder.application_name.setText(app.getName());
        DM.DisplayImage(app.getImage(), holder.application_image, 150, 150);
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView application_image;
        public TextView application_name;
        public Intent i;

        public ViewHolder(View itemView) {
            super(itemView);
            application_image = (ImageView) itemView.findViewById(R.id.application_image);
            application_name = (TextView) itemView.findViewById(R.id.application_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ApplicationItem appItem = applications.get(getAdapterPosition());
            Intent intent = new Intent(context, AppDetailsActivity.class);
            intent.putExtra("appItem", appItem);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }
}
