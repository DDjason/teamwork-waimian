package com.example.wuxudong.xun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuxudong.xun.R;

/**
 * Created by wuxudong on 17-4-8.
 * 菜单适配器
 */

public class ImageAdapter extends BaseAdapter {

    private Context Context;
    public ImageAdapter(Context c){Context = c;}
    private Integer[]images = {
            R.drawable.deal,
            R.drawable.restingorder,R.drawable.recycling,
            R.drawable.wallet,R.drawable.cash,R.drawable.yuan,
            R.drawable.a,R.drawable.kuangj,R.drawable.history,R.drawable.more
    };
    private String[] texts = {
            "交易中心",
            "挂单", "回收",
            "转账", "提现","人民币",
            "A币", "矿机","交易记录","更多"
    };
    //get the number
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    //get the current selector's id number
    @Override
    public long getItemId(int position) {
        return position;
    }
    //create view method
    @Override
    public View getView(int position, View view, ViewGroup viewgroup) {
        ImgTextWrapper wrapper;
        if(view==null) {
            wrapper = new ImgTextWrapper();
            LayoutInflater inflater = LayoutInflater.from(Context);
            view = inflater.inflate(R.layout.gird, null);
            view.setTag(wrapper);
            view.setPadding(8, 8, 8, 8);  //每格的间距
            view.setLayoutParams(new GridView.LayoutParams(150,150));
        } else {
            wrapper = (ImgTextWrapper)view.getTag();
        }

        wrapper.imageView = (ImageView)view.findViewById(R.id.MainActivityImage);
        wrapper.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        wrapper.imageView.setBackgroundResource(images[position]);

        wrapper.textView = (TextView)view.findViewById(R.id.MainActivityText);
        wrapper.textView.setText(texts[position]);
        return view;
    }
}

class ImgTextWrapper {
    ImageView imageView;
    TextView textView;
}