package com.f1reking.mymoney.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f1reking.mymoney.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by F1ReKing on 2016/1/19.
 */
public class AccountFragment extends BaseFragment {

    @Bind(R.id.fab_record)
    FloatingActionButton mFabRecord;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


}
