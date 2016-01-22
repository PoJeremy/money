package com.f1reking.mymoney.presenter;

import com.f1reking.mymoney.R;
import com.f1reking.mymoney.view.MainView;

/**
 * Created by F1ReKing on 2016/1/19.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView){
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id){
            case R.id.navigation_item_home:
                mMainView.switchToAccount();
                break;
            case R.id.navigation_item_expense:
                mMainView.switchToExpense();
                break;
            case R.id.navigation_item_income:
                mMainView.switchToIncome();
                break;
            case R.id.navigation_item_category:
                mMainView.switchToCategory();
                break;
            case R.id.navigation_item_report:
                mMainView.switchToReport();
                break;
            case R.id.navigation_item_setting:
                mMainView.switchToSetting();
                break;
            default:
                mMainView.switchToAccount();
                break;
        }
    }
}
