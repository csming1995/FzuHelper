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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button login_button;
    @Override
    public void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_login);

        login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                this.finish();
                Intent intent = new Intent(LoginActivity.this , MainContainerActivity.class);
                startActivity(intent);
        }
    }
}
