package com.f1reking.mymoney.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.f1reking.mymoney.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_edit)
    EditText mEtEdit;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void initViews() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContentViewId();
        ButterKnife.bind(this);
    }


}
