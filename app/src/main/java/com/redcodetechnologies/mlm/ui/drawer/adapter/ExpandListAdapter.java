package com.redcodetechnologies.mlm.ui.drawer.adapter;

/**
 * Created by aliah on 17-Oct-18.
 */

import android.content.Context;
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

        TextView txtListChild = convertView.findViewById(R.id.lblListItem);
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
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        group = ((ImageView) convertView.findViewById(R.id.group));
//        if ( getChildrenCount( groupPosition ) == 0 ) {
//            group.setVisibility( View.INVISIBLE );
//        }
//        else {
//            group.setVisibility( View.VISIBLE );gen
//            group.setImageResource( isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
//        }

        icon = (ImageView) convertView.findViewById(R.id.icon1);
        if (headerTitle == "Dashboard") {
            icon.setImageResource(R.drawable.icon_dashboard);
            group.setVisibility(View.INVISIBLE);
        }
        if (headerTitle == "Tutorials") {
            icon.setImageResource(R.drawable.ic_tutorial);
            group.setVisibility(View.INVISIBLE);
        }
        if (headerTitle == "Network") {
            icon.setImageResource(R.drawable.icon_network);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        }
        if (headerTitle == "Genealogy Table") {
            icon.setImageResource(R.drawable.genealogy_table);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        }
        if (headerTitle == "E-Wallet") {
            icon.setImageResource(R.drawable.e_wallet);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        }
        if (headerTitle == "Video Packs") {
            icon.setImageResource(R.drawable.icon_videos);
            group.setVisibility(View.INVISIBLE);
        }
        if (headerTitle == "Notication List") {
            icon.setImageResource(R.drawable.notification_bell);
            group.setVisibility(View.INVISIBLE);
        }

        if (headerTitle == "Reports") {
            icon.setImageResource(R.drawable.reports);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        }
        if (headerTitle == "Sponsor Support") {
            icon.setImageResource(R.drawable.sponsor_support);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        }
        if (headerTitle == "IT Support") {
            icon.setImageResource(R.drawable.it_support);
            group.setVisibility(View.VISIBLE);
            group.setImageResource(isExpanded ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
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