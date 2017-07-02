package com.example.wuxudong.xun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wuxudong.xun.R;
import com.example.wuxudong.xun.list.buyList;

import java.util.List;

/**
 * Created by wuxudong on 17-4-13.
 */

public class buylistAdapter extends ArrayAdapter<buyList>{
    private int resourceId;
    public buylistAdapter(Context context, int textViewResourceId, List<buyList> object){
        super(context,textViewResourceId,object);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        buyList buy = getItem(position);


        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView textView;
        textView = (TextView) view.findViewById(R.id.buy_num);
        textView.setText(buy.getBuy_num());
        textView = (TextView) view.findViewById(R.id.buy_single);
        textView.setText(buy.getBuy_single());
        textView = (TextView) view.findViewById(R.id.buy_sum);
        textView.setText(buy.getBuy_sum());
        textView = (TextView) view.findViewById(R.id.buy_time);
        textView.setText(buy.getBuy_time());
        textView = (TextView) view.findViewById(R.id.buy_volume);
        textView.setText(buy.getBuy_volume());
        textView = (TextView) view.findViewById(R.id.buy_status);
        textView.setText(buy.getBuy_status());
        return view;
    }
}
