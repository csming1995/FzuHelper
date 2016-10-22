package com.helper.west2ol.fzuhelper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.util.ActivityController;

/**
 * Created by Administrator on 2016/10/20.
 */

public class OtherActivity extends AppCompatActivity implements View.OnClickListener{
    Button back_button;
    @Override
    public void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_other);
        ActivityController.addActivity(this);
        back_button = (Button)findViewById(R.id.back_button_in_other);
        back_button.setOnClickListener(this);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button_in_other:
                finish();
        }
    }
}
