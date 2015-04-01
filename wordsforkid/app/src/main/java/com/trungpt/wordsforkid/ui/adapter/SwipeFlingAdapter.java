package com.trungpt.wordsforkid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.trungpt.wordsforkid.R;
import com.trungpt.wordsforkid.model.WordEntity;

import java.util.List;

/**
 * Created by Trung on 3/30/2015.
 */
public class SwipeFlingAdapter extends ArrayAdapter<WordEntity>
{
    private LayoutInflater mInflater;
    private List<WordEntity> mPeople;

    public SwipeFlingAdapter(Context context, int resource,
                             List<WordEntity> objects)
    {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
        mPeople = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        ViewHolder holder;
        if (convertView == null)
        {
            view = mInflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.tvWord = (TextView) view.findViewById(R.id.item_tvWord);
            view.setTag(holder);
        }
        else
        {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        WordEntity wordEntity = mPeople.get(position);
        holder.tvWord.setText(wordEntity.getWord());
        return view;
    }

    private class ViewHolder
    {
        public TextView tvWord;
    }

    public List<WordEntity> getmPeople()
    {
        return mPeople;
    }

    public void setmPeople(List<WordEntity> mPeople)
    {
        this.mPeople = mPeople;
    }
}
