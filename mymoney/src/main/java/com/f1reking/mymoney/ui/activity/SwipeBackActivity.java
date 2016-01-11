package com.f1reking.mymoney.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.f1reking.mymoney.R;
import com.f1reking.mymoney.ui.view.SwipeBackLayout;

import org.androidannotations.annotations.ViewById;

/**
 * Created by F1ReKing on 2016/1/11.
 */
public abstract class SwipeBackActivity extends BaseActivity {

    @ViewById
    SwipeBackLayout swipeBackLayout;
    @ViewById
    Toolbar toolbar;

    protected void initToolBar(){
        setTitle();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setTitle() {
    }


    @Override
    protected void initViews() {
        initToolBar();
        swipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
    }

}
