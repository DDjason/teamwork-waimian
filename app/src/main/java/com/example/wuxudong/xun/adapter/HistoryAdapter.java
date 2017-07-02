package com.example.wuxudong.xun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wuxudong.xun.R;
import com.example.wuxudong.xun.list.History;

import java.util.List;

/**
 * Created by wuxudong on 17-5-8.
 */

public class HistoryAdapter extends ArrayAdapter<History> {
    private int resourceId;
    public HistoryAdapter(Context context, int textViewResourceId, List<History> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        History history = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView history_price = (TextView) view.findViewById(R.id.history_price);
        TextView history_number = (TextView) view.findViewById(R.id.history_number);
        TextView history_real = (TextView) view.findViewById(R.id.history_real);
        TextView history_time = (TextView) view.findViewById(R.id.history_time);
        TextView history_status = (TextView) view.findViewById(R.id.history_status);

        history_price.setText(history.getHistory_price());
        history_number.setText(history.getHistory_number());
        history_real.setText(history.getHistory_real());
        history_time.setText(history.getHistory_time());
        history_status.setText(history.getHistory_status());
        return view;

    }
}
