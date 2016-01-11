package com.f1reking.mymoney.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by F1ReKing on 2016/1/11.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initViews();
    protected abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
