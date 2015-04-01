package com.trungpt.wordsforkid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.trungpt.wordsforkid.R;
import com.trungpt.wordsforkid.model.WordEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 3/31/2015.
 */
public class ViewPagerAdapter extends PagerAdapter
{
    // Declare Variables
    Context context;
    List<WordEntity> wordEntities = new ArrayList<>();

    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, List<WordEntity> wordEntities)
    {
        this.context = context;
        this.wordEntities = wordEntities;
    }

    @Override
    public int getCount()
    {
        return wordEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        TextView tvWord;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item, container,
                false);
        tvWord = (TextView) itemView.findViewById(R.id.item_tvWord);
        tvWord.setText(wordEntities.get(position).getWord());
        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}