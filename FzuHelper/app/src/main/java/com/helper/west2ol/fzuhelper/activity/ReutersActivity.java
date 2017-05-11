package com.helper.west2ol.fzuhelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.adapter.ReutersFragmentPagerAdapter;
import com.helper.west2ol.fzuhelper.fragment.ReutersBusinessFragment;
import com.helper.west2ol.fzuhelper.fragment.ReutersNewsFragment;
import com.helper.west2ol.fzuhelper.fragment.ReutersSchoolInsideFragment;
import com.helper.west2ol.fzuhelper.fragment.ReutersStudentFragment;
import com.helper.west2ol.fzuhelper.util.ActivityController;

import java.util.ArrayList;

/**
 * Created by deng on 2016/10/30.
 */

public class ReutersActivity extends AppCompatActivity implements View.OnClickListener{

    ReutersSchoolInsideFragment schoolInsideFragment;

    private String id;
    private String tabNames[] = { "路透电台" ,  "校内生活" , "商业街店铺" , "学生街店铺" };

    private Button back;
    private TabLayout tabLayout;
    private ReutersFragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private ReutersNewsFragment nF;
    private ReutersSchoolInsideFragment siF;
    private ReutersBusinessFragment bF;
    private ReutersStudentFragment  sF;
     ArrayList<Fragment> fragmentsList;

    Bundle parameterToFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuters);
        ActivityController.addActivity(this);

        id = getIntent().getStringExtra("id");
        //LayoutInflater inflater = getLayoutInflater();
        /*reutersNews =  inflater.inflate(R.layout.content_reuters_news , null);
        reutersSchoolInside = inflater.inflate(R.layout.content_reuters_school_inside , null);
        reutersBusiness = inflater.inflate(R.layout.content_reuters_street_business , null);
        reutersStudent = inflater.inflate(R.layout.content_reuters_street_student , null);*/
        /*viewList.add(reutersNews);
        viewList.add(reutersSchoolInside);
        viewList.add(reutersBusiness);
        viewList.add(reutersStudent);*/

        initViews();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    public void initViews(){
        back = (Button) findViewById(R.id.back_button_in_reuters);
        back.setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.tab_reuters);
        viewPager = (ViewPager) findViewById(R.id.reuters_viewPager);
        fragmentsList = new ArrayList<>();
        nF = new ReutersNewsFragment();
        siF = new ReutersSchoolInsideFragment();
        bF = new ReutersBusinessFragment();
        sF = new ReutersStudentFragment();
        fragmentsList.add(nF);
        fragmentsList.add(siF);
        fragmentsList.add(bF);
        fragmentsList.add(sF);
        fragmentPagerAdapter = new ReutersFragmentPagerAdapter(getSupportFragmentManager(),fragmentsList,tabNames);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void initEvent(){

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_button_in_reuters:
                finish();
                break;
        }
    }
}
