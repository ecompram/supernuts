package com.parsjavid.supernuts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.models.EntityBase;

import java.util.List;

public class CustomEntityAdapter<T extends EntityBase> extends BaseAdapter
{
    Context ctx;
    List<T> ps;
    LayoutInflater inflater;
    public CustomEntityAdapter(Context ctx,List<T> ps)
    {
        this.ctx=ctx;
        this.ps=ps;
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return ps.size();
    }

    @Override
    public T getItem(int position) {
        return ps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ps.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null)
            rowView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent,
                    false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);

        T proj = getItem(position);
        try {
            textView.setText(proj.getName());

        } catch (Exception e) {

        }

        return rowView;
    }
}
