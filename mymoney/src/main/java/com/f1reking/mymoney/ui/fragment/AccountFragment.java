package com.f1reking.mymoney.ui.fragment;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import com.f1reking.mymoney.R;
import com.f1reking.mymoney.ui.activity.RecordActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by F1ReKing on 2016/1/19.
 */
public class AccountFragment extends BaseFragment {

    @Bind(R.id.fab_record)
    FloatingActionButton mFabRecord;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
    }

    @OnClick(R.id.fab_record)
    void onclick() {
        getActivity().startActivity(new Intent(getActivity(), RecordActivity.class));
    }


}
