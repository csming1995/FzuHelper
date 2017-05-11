package com.helper.west2ol.fzuhelper.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.activity.MainContainerActivity;

/**
 * Created by Administrator on 2016/10/20.
 */

public class CourseTableFragment extends Fragment{
    Button menu_button_in_course_table;
    DrawerLayout drawer;
    @Override
    public void onCreate(Bundle savedIntenceState){
        super.onCreate(savedIntenceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedIntanceState){
        View rootView = inflater.inflate(R.layout.fragment_course_table , container , false);
        FloatingActionButton add = (FloatingActionButton) rootView.findViewById(R.id.more_button_in_coursetable);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CourseTable" , "onClick");
                // add onClick
            }
        });
        drawer = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        menu_button_in_course_table = (Button)rootView.findViewById(R.id.menu_button_in_course_table);
        menu_button_in_course_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        return rootView;
    }
}
