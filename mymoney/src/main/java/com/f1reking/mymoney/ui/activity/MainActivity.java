package com.f1reking.mymoney.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.f1reking.mymoney.R;
import com.f1reking.mymoney.presenter.MainPresenter;
import com.f1reking.mymoney.presenter.MainPresenterImpl;
import com.f1reking.mymoney.ui.fragment.AccountFragment;
import com.f1reking.mymoney.ui.fragment.CategoryFragment;
import com.f1reking.mymoney.ui.fragment.ExpenseFragment;
import com.f1reking.mymoney.ui.fragment.IncomeFragment;
import com.f1reking.mymoney.ui.fragment.ReportFragment;
import com.f1reking.mymoney.ui.fragment.SettingFragment;
import com.f1reking.mymoney.view.IMainView;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements IMainView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.frame_content)
    FrameLayout mFrameContent;
    @Bind(R.id.main_content)
    CoordinatorLayout mMainContent;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private MainPresenter mMainPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mToolbar.setTitle(R.string.navigation_account); //设置在setSupportActionBar之前，否则默认显示应用名
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string
                .drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerContent(mNavigationView);
        mMainPresenter = new MainPresenterImpl(this);
        switchToAccount();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mMainPresenter.switchNavigation(menuItem.getItemId());
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void switchToAccount() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AccountFragment()).commit();
        mToolbar.setTitle(R.string.navigation_account);
    }

    @Override
    public void switchToExpense() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ExpenseFragment()).commit();
        mToolbar.setTitle(R.string.navigation_expense);
    }

    @Override
    public void switchToIncome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new IncomeFragment()).commit();
        mToolbar.setTitle(R.string.navigation_income);
    }

    @Override
    public void switchToCategory() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new CategoryFragment()).commit();
        mToolbar.setTitle(R.string.navigation_category);
    }

    @Override
    public void switchToReport() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ReportFragment()).commit();
        mToolbar.setTitle(R.string.navigation_report);
    }

    @Override
    public void switchToSetting() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new SettingFragment()).commit();
        mToolbar.setTitle(R.string.navigation_setting);
    }
}
