package com.helper.west2ol.fzuhelper.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.util.ActivityController;

/**
 * Created by Administrator on 2016/10/20.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    Button logout;
    @Override
    public void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_setting);
        ActivityController.addActivity(this);

        logout = (Button)findViewById(R.id.logout_button);
        logout.setOnClickListener(this);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_button:
                ActivityController.finashAll();
                Intent intent = new Intent(SettingActivity.this , LoginActivity.class);
                startActivity(intent);
        }
    }
}
