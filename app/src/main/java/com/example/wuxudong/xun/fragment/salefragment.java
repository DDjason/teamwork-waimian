package com.example.wuxudong.xun.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuxudong.xun.R;

/**
 * Created by wuxudong on 17-4-15.
 */

public class salefragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.sale_list,container,false);
        return view;
    }
}
