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

    private String id_1;
    private String num;
    //从Logincheck.asp获取的id和num

    private String id_2;//从LOGIN_CHK_XS获取,后面获取web信息的唯一标识码

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
                intent.putExtra("id" , id_2);
                startActivity(intent);
        }
    }
}
