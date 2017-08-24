package com.nabwera.github_api_challenge.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nabwera.github_api_challenge.R;
import com.nabwera.github_api_challenge.api.model.GithubUsers;


import java.util.List;

/**
 * Created by nabwera on 24/08/2017.
 */

public class GithubUsersAdapter extends ArrayAdapter<GithubUsers> {

    private Context context;
    private List<GithubUsers> values;

    public GithubUsersAdapter(Context context, List<GithubUsers> values){
        super(context, R.layout.list_item_paginator, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_paginator, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.list_item_paginator_text);

        GithubUsers item = values.get(position);
        String message = item.getName();
        textView.setText(message);

        return row;
    }
}
