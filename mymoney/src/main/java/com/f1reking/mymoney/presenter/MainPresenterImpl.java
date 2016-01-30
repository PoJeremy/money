package com.f1reking.mymoney.presenter;

import com.f1reking.mymoney.R;
import com.f1reking.mymoney.view.IMainView;

/**
 * Created by F1ReKing on 2016/1/19.
 */
public class MainPresenterImpl implements MainPresenter {

    private IMainView mIMainView;

    public MainPresenterImpl(IMainView IMainView){
        this.mIMainView = IMainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id){
            case R.id.navigation_item_home:
                mIMainView.switchToAccount();
                break;
            case R.id.navigation_item_expense:
                mIMainView.switchToExpense();
                break;
            case R.id.navigation_item_income:
                mIMainView.switchToIncome();
                break;
            case R.id.navigation_item_category:
                mIMainView.switchToCategory();
                break;
            case R.id.navigation_item_report:
                mIMainView.switchToReport();
                break;
            case R.id.navigation_item_setting:
                mIMainView.switchToSetting();
                break;
        }
    }
}
