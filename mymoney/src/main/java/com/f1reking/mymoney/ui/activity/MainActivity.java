package com.f1reking.mymoney.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.f1reking.mymoney.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;
    @ViewById
    FloatingActionButton fab;

    @AfterViews
    void initView() {
        setSupportActionBar(toolbar);
    }

    @Click
    void fab() {
         Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
    }

}
