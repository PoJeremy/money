package com.f1reking.mymoney.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.f1reking.mymoney.R;

import butterknife.Bind;

/**
 * Created by F1ReKing on 2016/1/11.
 */
public abstract class SwipeBackActivity extends BaseActivity {



    @Bind(R.id.toolbar)
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
//        swipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
//            @Override
//            public void onFinish() {
//                finish();
//            }
//        });
    }

}
