package com.rappidandroiddemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rappidandroiddemo.R;
import com.rappidandroiddemo.activity.AppActivity;
import com.rappidandroiddemo.item.ApplicationItem;
import com.rappidandroiddemo.item.CategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 3/16/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryItem> categories;
    private List<ApplicationItem> apps;
    private Context context;

    public CategoryAdapter(Context context, ArrayList<CategoryItem> categories, ArrayList<ApplicationItem> apps) {
        this.categories = categories;
        this.apps = apps;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryItem category = categories.get(position);
        holder.category_name.setText(category.getLabel());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public View background;
        public TextView category_name;
        public Intent i;

        public ViewHolder(View itemView) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            category_name.setOnClickListener(this);
            background = itemView;
            itemView.setClickable(true);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            ArrayList<ApplicationItem> appsList = new ArrayList<ApplicationItem>();
            CategoryItem aux = categories.get(pos);
            for (ApplicationItem app : apps) {
                if (app.getCategory().equals(aux)) {
                    appsList.add(app);
                }
            }
            Intent intent = new Intent(context, AppActivity.class);
            intent.putParcelableArrayListExtra("appsList", appsList);
            intent.putExtra("category",aux.getLabel());
            context.startActivity(intent);
            ((Activity)context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
