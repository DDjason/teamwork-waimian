package com.example.wuxudong.xun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wuxudong.xun.R;
import com.example.wuxudong.xun.list.Rmb;

import java.util.List;

/**
 * Created by wuxudong on 17-5-6.
 */

public class RmbAdapter extends ArrayAdapter<Rmb> {
    private int resourceId;
    public RmbAdapter(Context context, int textViewResourceId, List<Rmb> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        Rmb rmb = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView rmb_id = (TextView) view.findViewById(R.id.rmb_id);
        TextView rmb_changemoney = (TextView) view.findViewById(R.id.rmb_changemoney);
        TextView rmb_changetime = (TextView) view.findViewById(R.id.rmb_changetime);
        TextView rmb_intro = (TextView) view.findViewById(R.id.rmb_intro);
        rmb_id.setText(rmb.getId());
        rmb_changemoney.setText(rmb.getChangeMoney());
        rmb_changetime.setText(rmb.getChangeTime());
        rmb_intro.setText(rmb.getIntro());
        return view;

    }

}
