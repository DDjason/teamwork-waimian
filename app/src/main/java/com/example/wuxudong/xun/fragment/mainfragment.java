package com.example.wuxudong.xun.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.wuxudong.xun.AbActivity;
import com.example.wuxudong.xun.CashActivity;
import com.example.wuxudong.xun.DealActivity;
import com.example.wuxudong.xun.HistoryActivity;
import com.example.wuxudong.xun.KuangjActivity;
import com.example.wuxudong.xun.R;
import com.example.wuxudong.xun.RecycleActivity;
import com.example.wuxudong.xun.RestingorderActivity;
import com.example.wuxudong.xun.RmbActivity;
import com.example.wuxudong.xun.VirementActivity;
import com.example.wuxudong.xun.adapter.ImageAdapter;

/**
 * Created by wuxudong on 17-3-30.
 */

public class mainfragment extends Fragment  {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
         view = inflater.inflate(R.layout.fragment_main, container,false);
        getgrid();
        return view;
    }

    private void getgrid(){
        GridView gridView = (GridView) view.findViewById(R.id.MainActivityGrid);
        gridView.setAdapter(new ImageAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Intent intent = new Intent(getActivity(), DealActivity.class);

                    startActivity(intent);
                }
                if(i == 1){
                    Intent intent = new Intent(getActivity(), RestingorderActivity.class);

                    startActivity(intent);
                }
                if(i == 2){
                    Intent intent = new Intent(getActivity(), RecycleActivity.class);

                    startActivity(intent);
                }
                if(i == 3){
                    Intent intent = new Intent(getActivity(), VirementActivity.class);

                    startActivity(intent);
                }
                if(i == 4){
                    Intent intent = new Intent(getActivity(), CashActivity.class);
                    startActivity(intent);
                }
                if(i == 5){
                    Intent intent = new Intent(getActivity(), RmbActivity.class);
                    startActivity(intent);
                }
                if(i == 6){
                    Intent intent = new Intent(getActivity(), AbActivity.class);

                    startActivity(intent);
                }
                if(i == 7){
                    Intent intent = new Intent(getActivity(), KuangjActivity.class);

                    startActivity(intent);
                }
                if(i == 8){
                    Intent intent = new Intent(getActivity(), HistoryActivity.class);

                    startActivity(intent);
                }

            }
        });
    }


}
