package com.trungpt.wordsforkid.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.trungpt.wordsforkid.R;
import com.trungpt.wordsforkid.model.FolderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 3/26/2015.
 */
public class ListLevelAdapter extends BaseAdapter
{
    List<FolderEntity> folderEntityList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public ListLevelAdapter(Context context, List<FolderEntity> folderEntityList)
    {
        this.context = context;
        this.folderEntityList = folderEntityList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount()
    {
        return folderEntityList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return folderEntityList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        MyViewHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_level, null);
            holder = new MyViewHolder();
            holder.tvLevel = (TextView) convertView.findViewById(R.id.item_level_tvFolder);
            convertView.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder) convertView.getTag();
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/yoshisst.ttf");
        holder.tvLevel.setTypeface(font);
        holder.tvLevel.setText(folderEntityList.get(position).getFolderName());
        return convertView;
    }
    private class MyViewHolder
    {
        TextView tvLevel;
    }
}
