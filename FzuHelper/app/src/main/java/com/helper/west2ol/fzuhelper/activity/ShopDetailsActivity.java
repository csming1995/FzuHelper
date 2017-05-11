package com.helper.west2ol.fzuhelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.helper.west2ol.fzuhelper.util.ActivityController;

/**
 * Created by deng on 2016/11/20.
 */

public class ShopDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);

    }
}
