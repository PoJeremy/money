package com.f1reking.mymoney.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by F1ReKing on 2016/1/11.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentViewId();

    protected abstract void initViews();

    protected abstract void initDatas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initViews();
        initDatas();
    }

    protected <T extends View> T generateFindViewById(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
