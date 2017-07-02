package com.example.wuxudong.xun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wuxudong.xun.R;
import com.example.wuxudong.xun.list.Ab;


import java.util.List;

/**
 * Created by wuxudong on 17-5-6.
 */

public class AbAdapter extends ArrayAdapter<Ab> {
    private int resourceId;
    public AbAdapter(Context context, int textViewResourceId, List<Ab> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Ab ab = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView ab_id = (TextView) view.findViewById(R.id.ab_id);
        TextView ab_changemoney = (TextView) view.findViewById(R.id.ab_changemoney);
        TextView ab_changetime = (TextView) view.findViewById(R.id.ab_changetime);
        TextView ab_intro = (TextView) view.findViewById(R.id.ab_intro);
        ab_id.setText(ab.getId());
        ab_changemoney.setText(ab.getChangeMoney());
        ab_changetime.setText(ab.getChangeTime());
        ab_intro.setText(ab.getIntro());
        return view;

    }
}
