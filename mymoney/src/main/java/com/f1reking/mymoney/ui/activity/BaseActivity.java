package com.f1reking.mymoney.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.f1reking.mymoney.app.AppApplication;

import butterknife.ButterKnife;

/**
 * Created by F1ReKing on 2016/1/11.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutRes();

    protected abstract void initViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initViews();
        AppApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppApplication.getInstance().removeActivity(this);
    }
}
