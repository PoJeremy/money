package com.f1reking.mymoney.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f1reking.mymoney.ui.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by F1ReKing on 2016/1/11.
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;
    private BaseActivity mActivity;

    protected abstract int getLayoutRes();

    protected abstract void initViews();

    protected abstract void initDatas();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        initDatas();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 获取Activity
     * @return
     */
    public BaseActivity getBaseActivity() {
        if (null == mActivity) {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }
}
