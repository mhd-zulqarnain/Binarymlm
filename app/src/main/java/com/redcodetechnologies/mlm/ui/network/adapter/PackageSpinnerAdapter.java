package com.redcodetechnologies.mlm.ui.network.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.redcodetechnologies.mlm.R;
import com.redcodetechnologies.mlm.models.Packages;
import com.redcodetechnologies.mlm.models.users.DropDownMembers;

import java.util.ArrayList;

public class PackageSpinnerAdapter extends BaseAdapter {

    Context c;
    ArrayList<Packages> objects;

    public PackageSpinnerAdapter(Context context, ArrayList<Packages> objects) {
        super();
        this.c = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Packages cur_obj = objects.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.single_row_spinner_item, parent, false);
        TextView tv_item = row.findViewById(R.id.tv_item);
        tv_item.setText(cur_obj.getPackageName());
        return row;
    }
}