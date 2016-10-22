package com.helper.west2ol.fzuhelper.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.fragment.CourseTableFragment;
import com.helper.west2ol.fzuhelper.fragment.GradeFragment;
import com.helper.west2ol.fzuhelper.fragment.MathFragment;
import com.helper.west2ol.fzuhelper.util.ActivityController;

public class MainContainerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CourseTableFragment courseTableFragment;
    GradeFragment gradeFragment;
    MathFragment mathFragment;

    NavigationView navigationView;
    MenuItem course_Item;

    Bundle parameterToFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        ActivityController.addActivity(this);

        courseTableFragment = new CourseTableFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container , courseTableFragment)
                .commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        course_Item = navigationView.getMenu().getItem(0);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
    @Override
    public void onResume(){
        super.onResume();
        course_Item.setChecked(true);
        courseTableFragment = new CourseTableFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container , courseTableFragment)
                .commit();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*
    菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.course_table, menu);
        return true;
    }
    /*
    菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                courseTableFragment = new CourseTableFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_container , courseTableFragment)
                        .commit();
                break;
            case R.id.item2:
                gradeFragment = new GradeFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_container , gradeFragment)
                        .commit();
                break;
            case R.id.item3:
                mathFragment = new MathFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_container , mathFragment)
                        .commit();
                break;
            case R.id.item4:
                Intent intent4 = new Intent(MainContainerActivity.this , OtherActivity.class);
                startActivity(intent4);
                break;
            case R.id.item8:
                Intent intent_9 = new Intent(MainContainerActivity.this,SettingActivity.class);
                startActivity(intent_9);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
