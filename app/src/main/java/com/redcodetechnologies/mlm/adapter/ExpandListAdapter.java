package com.redcodetechnologies.mlm.adapter;

/**
 * Created by aliah on 17-Oct-18.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.redcodetechnologies.mlm.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Ali Siddiqui on 6/12/2017.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private ImageView group, icon;



    public ExpandListAdapter(Context context, List<String> listDataHeader,
                             HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_sublist, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_list, null);
        }


        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        group = ((ImageView) convertView.findViewById(R.id.group));
//        if ( getChildrenCount( groupPosition ) == 0 ) {
//            group.setVisibility( View.INVISIBLE );
//        }
//        else {
//            group.setVisibility( View.VISIBLE );
//            group.setImageResource( isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
//        }

        icon = (ImageView) convertView.findViewById(R.id.icon1);
        if (headerTitle == "Home") {
            //    icon.setImageResource(R.drawable.ic_home_black_24dp);
            group.setVisibility(View.INVISIBLE);
        }
        if (headerTitle == "Faqeer Parbirham") {
            icon.setImageResource(R.drawable.ic_menu_camera);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        }
        if (headerTitle == "Pithoro") {
            icon.setImageResource(R.drawable.ic_menu_send);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        }
        if (headerTitle == "Product4") {
            icon.setImageResource(R.drawable.ic_menu_gallery);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        }
        if (headerTitle == "Product5") {
            icon.setImageResource(R.drawable.ic_menu_share);
            group.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}