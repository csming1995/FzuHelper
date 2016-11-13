package com.helper.west2ol.fzuhelper.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helper.west2ol.fzuhelper.R;


/**
 * Created by deng on 2016/10/30.
 */

public class ReutersSchoolInsideFragment extends android.support.v4.app.Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_reuters_school_inside , container , false);
        int a;
        return view;
    }
}
