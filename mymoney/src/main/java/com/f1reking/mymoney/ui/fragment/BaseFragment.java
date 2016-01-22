package com.f1reking.mymoney.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by F1ReKing on 2016/1/11.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected abstract void initViews();
    protected abstract void initDatas();
    protected abstract int getContentViewId();

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentViewId(),container,false);
        ButterKnife.bind(this,rootView);
        initViews();
        initDatas();
        return rootView;
    }
}
