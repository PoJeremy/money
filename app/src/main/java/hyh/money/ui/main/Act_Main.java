package hyh.money.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import hyh.money.R;
import hyh.money.ui.account.Frag_Account;
import hyh.money.ui.category.Frag_Category;
import hyh.money.ui.expense.Frag_Expense;
import hyh.money.ui.income.Frag_Income;
import hyh.money.ui.report.Frag_Report;
import hyh.money.ui.setting.Act_GestureLock;
import hyh.money.ui.setting.Frag_Setting;
import hyh.money.ui.setting.Act_Setting;
import me.drakeet.materialdialog.MaterialDialog;


public class Act_Main extends AppCompatActivity implements View.OnClickListener {

    public static final int ACCOUNT = 0, EXPENSE = 1, INCOME = 2, REPORT= 3, CATEGORY = 4, SETTING = 5;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private TextView font_account;
    private TextView font_expense;
    private TextView font_income;
    private TextView font_report;
    private TextView font_category;
    private TextView font_setting;

    private LinearLayout drawer_account;
    private LinearLayout drawer_expense;
    private LinearLayout drawer_income;
    private LinearLayout drawer_report;
    private LinearLayout drawer_category;
    private LinearLayout drawer_setting;


    // Fragments
    private Fragment[] mFragments = new Fragment[6];
    private FragmentManager mManager;

    // Temp fields
    private int mCurrent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        //判断有没有加密
//        SharedPreferences preferences = getSharedPreferences("locked",MODE_PRIVATE);
//        Boolean isbidExist = preferences.getBoolean("have", false);
//
//        if(isbidExist) {
//            Intent intent = new Intent(Act_Main.this, Act_GestureLock.class);
//            intent.putExtra("flag",10);
//            startActivity(intent);
//        }else{

        // 获取控件
        findViews();

        // font控件
        fontViews();

        // Toolbar
        mToolbar.setTitle("Money");//设置Toolbar标题
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));//设置标题颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // 创建返回键，并实现打开关闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Fragments
        mFragments[ACCOUNT] = new Frag_Account();
        mFragments[EXPENSE] = new Frag_Expense();
        mFragments[INCOME] = new Frag_Income();
        mFragments[REPORT] = new Frag_Report();
        mFragments[CATEGORY] = new Frag_Category();
        mFragments[SETTING] = new Frag_Setting();

        mManager = getSupportFragmentManager();

        FragmentTransaction ft = mManager.beginTransaction();
        for (Fragment f : mFragments) {
            ft.add(R.id.content_frame, f);
            ft.hide(f);
        }
        ft.commit();

//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int page = getIntent().getIntExtra("flag", 0);
        switch (page) {
            case 0:
                mToolbar.setTitle(R.string.text_account);
                switchTo(ACCOUNT);
                drawer_account.setBackgroundColor(getResources().getColor(R.color.colorLinearLayout));
                break;
            case 1:
                mToolbar.setTitle(R.string.text_expense);
                switchTo(EXPENSE);
                drawer_expense.setBackgroundColor(getResources().getColor(R.color.colorLinearLayout));
                break;
            case 2:
                mToolbar.setTitle(R.string.text_income);
                switchTo(INCOME);
                drawer_income.setBackgroundColor(getResources().getColor(R.color.colorLinearLayout));
                break;
            case 3:
                mToolbar.setTitle(R.string.text_category);
                switchTo(CATEGORY);
                drawer_category.setBackgroundColor(getResources().getColor(R.color.colorLinearLayout));
                break;
            case 4:
                mToolbar.setTitle(R.string.text_report);
                switchTo(REPORT);
                drawer_report.setBackgroundColor(getResources().getColor(R.color.colorLinearLayout));
                break;

        }


    }

    private void switchAndRefresh(int id) {
        if (id != 0) {
            SwipeRefreshLayout.OnRefreshListener l = (SwipeRefreshLayout.OnRefreshListener) mFragments[id];
            l.onRefresh();
        }
        switchTo(id);
    }

    // 切换drawer items
    private void switchTo(int id) {

        FragmentTransaction ft = mManager.beginTransaction();

        for (int i = 0; i < mFragments.length; i++) {
            Fragment f = mFragments[i];

            if (f != null) {
                if (i != id) {
                    ft.hide(f);
                } else {
                    ft.show(f);
                }
            }
        }

        ft.commit();

        mCurrent = id;

        mDrawerLayout.closeDrawers();
    }

    public void accounts() {
        switchTo(ACCOUNT);
    }

    public void expenses() {
        switchTo(EXPENSE);
    }

    public void income() {
        switchTo(INCOME);
    }

    public void reports() {
        switchTo(REPORT);
    }

    public void categories() {
        switchTo(CATEGORY);
    }

    public void settings() {
//        switchTo(SETTING);
        Intent intent = new Intent(Act_Main.this, Act_Setting.class);
        intent.putExtra("flag",0);
        startActivity(intent);
    }

    // onClick
    @Override
    public void onClick(View v) {

        Resources mResources = getResources();

        switch (v.getId()) {
            case R.id.drawer_accounts:
                accounts();
                mToolbar.setTitle(R.string.text_account);
                drawer_account.setBackgroundColor(mResources.getColor(R.color.colorLinearLayout));
                drawer_expense.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_income.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_report.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_category.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_setting.setBackgroundColor(mResources.getColor(R.color.white));
                break;
            case R.id.drawer_expenses:
                expenses();
                mToolbar.setTitle(R.string.text_expense);
                drawer_expense.setBackgroundColor(mResources.getColor(R.color.colorLinearLayout));
                drawer_account.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_income.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_report.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_category.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_setting.setBackgroundColor(mResources.getColor(R.color.white));
                break;
            case R.id.drawer_income:
                income();
                mToolbar.setTitle(R.string.text_income);
                drawer_income.setBackgroundColor(mResources.getColor(R.color.colorLinearLayout));
                drawer_account.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_expense.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_report.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_category.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_setting.setBackgroundColor(mResources.getColor(R.color.white));
                break;
            case R.id.drawer_reports:
                reports();
                mToolbar.setTitle(R.string.text_report);
                drawer_report.setBackgroundColor(mResources.getColor(R.color.colorLinearLayout));
                drawer_account.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_expense.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_income.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_category.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_setting.setBackgroundColor(mResources.getColor(R.color.white));
                break;
            case R.id.drawer_categories:
                categories();
                mToolbar.setTitle(R.string.text_category);
                drawer_category.setBackgroundColor(mResources.getColor(R.color.colorLinearLayout));
                drawer_account.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_expense.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_income.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_report.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_setting.setBackgroundColor(mResources.getColor(R.color.white));
                break;
            case R.id.drawer_settings:
                settings();
                mToolbar.setTitle(R.string.text_settings);
                drawer_setting.setBackgroundColor(mResources.getColor(R.color.colorLinearLayout));
                drawer_account.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_expense.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_income.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_report.setBackgroundColor(mResources.getColor(R.color.white));
                drawer_category.setBackgroundColor(mResources.getColor(R.color.white));
                break;
        }
    }


    // Init view
    public void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // drawer items
        drawer_account = (LinearLayout) findViewById(R.id.drawer_accounts);
        drawer_expense = (LinearLayout) findViewById(R.id.drawer_expenses);
        drawer_income = (LinearLayout) findViewById(R.id.drawer_income);
        drawer_report = (LinearLayout) findViewById(R.id.drawer_reports);
        drawer_category = (LinearLayout) findViewById(R.id.drawer_categories);
        drawer_setting = (LinearLayout) findViewById(R.id.drawer_settings);
        // bind events
        drawer_account.setOnClickListener(this);
        drawer_expense.setOnClickListener(this);
        drawer_income.setOnClickListener(this);
        drawer_report.setOnClickListener(this);
        drawer_category.setOnClickListener(this);
        drawer_setting.setOnClickListener(this);
    }

    public void fontViews() {

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        font_account = (TextView) findViewById(R.id.font_accounts);
        font_account.setText(getString(R.string.fa_credit_card));
        font_account.setTypeface(font);

        font_expense = (TextView) findViewById(R.id.font_expenses);
        font_expense.setText(getString(R.string.fa_shopping_cart));
        font_expense.setTypeface(font);

        font_income = (TextView) findViewById(R.id.font_income);
        font_income.setText(getString(R.string.fa_btc));
        font_income.setTypeface(font);

        font_report = (TextView) findViewById(R.id.font_reports);
        font_report.setText(getString(R.string.fa_bar_chart_o));
        font_report.setTypeface(font);

        font_category = (TextView) findViewById(R.id.font_categories);
        font_category.setText(getString(R.string.fa_list));
        font_category.setTypeface(font);

        font_setting = (TextView) findViewById(R.id.font_settings);
        font_setting.setText(getString(R.string.fa_cog));
        font_setting.setTypeface(font);
    }

    // exit
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当点击硬键盘上的返回键时，提醒用户是否要退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void showDialog() {
        // MaterialDialog
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("退出提示");
        mMaterialDialog.setMessage("是否退出应用?");
        mMaterialDialog.setPositiveButton("是", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        mMaterialDialog.setNegativeButton("否", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialDialog.dismiss();
            }
        });

        mMaterialDialog.show();
    }

}










