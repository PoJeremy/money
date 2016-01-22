package com.f1reking.mymoney.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f1reking.mymoney.R;

/**
 * Created by F1ReKing on 2016/1/20.
 */
public class ExpenseFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense,container,false);
        return view;
    }
}
