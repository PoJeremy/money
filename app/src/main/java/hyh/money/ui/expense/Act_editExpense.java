package hyh.money.ui.expense;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hyh.money.R;
import hyh.money.dao.AccountDao;
import hyh.money.dao.CategoryDao;
import hyh.money.dao.ExpenseDao;
import hyh.money.model.Account;
import hyh.money.model.Category;
import hyh.money.model.Expense;
import hyh.money.ui.main.Act_Main;
import hyh.money.utils.ToastUtil;
import hyh.money.view.Account.DialogAccount;
import hyh.money.view.Calc.DialogCalculator;
import hyh.money.view.Category.DialogExpenseCategory;
import hyh.money.view.DateTimePick.SlideDateTimeListener;
import hyh.money.view.DateTimePick.SlideDateTimePicker;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by HuangYH on 2015/5/12.
 */
public class Act_editExpense extends ActionBarActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView category_title;
    private TextView text_datetime;
    private TextView tv_amount_expense;
    private TextView account_name;
    private TextView account_amount;
    private LinearLayout ll_record0;
    private LinearLayout ll_record2;
    private LinearLayout ll_account_record;
    private Button btn_save;
    private EditText et_remark;
    private String str_name;
    private double str_amount;
    private List<Account> accountList;
    private List<Category> categoryList;
    private ExpenseDao expenseDao;
    private int id;
    private Expense expense;
    private CategoryDao categoryDao;
    private Category category;
    private AccountDao accountDao;
    private Account account;
    private int accountsId;
    private int categorysId;

    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editrecord_expense);
        initView();
        initData();
        // toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("编辑支出记录");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));//设置标题颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) onMenuItemClick);
        expenseDao = new ExpenseDao(this);
        expense = new Expense();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        accountsId = intent.getIntExtra("accountId",0);
        categorysId = intent.getIntExtra("categoryId",0);
        expense = expenseDao.find(id);
        categoryDao = new CategoryDao(this);
        category = new Category();
        category = categoryDao.find(expense.getCategoryId());
        accountDao = new AccountDao(this);
        account = new Account();
        account = accountDao.find(expense.getAccountId());
        tv_amount_expense.setText(expense.getAmount()+"");
        category_title.setText(category.getTitle());
        account_name.setText(account.getName());
        account_amount.setText(account.getAmount()+"");
        text_datetime.setText(expense.getDatetime().toString());
        et_remark.setText(expense.getRemark().toString());

    }

    private void initView() {
        // 账户
        ll_account_record = (LinearLayout) findViewById(R.id.ll_account_record);
        ll_account_record.setOnClickListener(this);

        // 分类
        category_title = (TextView) findViewById(R.id.category_title);
        ll_record2 = (LinearLayout) findViewById(R.id.ll_record2);
        ll_record2.setOnClickListener(this);

        text_datetime = (TextView) findViewById(R.id.text_datetime);
        text_datetime.setText(mFormatter.format(new Date()));
        text_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                                //.setMinDate(minDate)
                                //.setMaxDate(maxDate)
                        .setIs24HourTime(true)
                                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                                //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });
        ll_record0 = (LinearLayout) findViewById(R.id.ll_record0);
        ll_record0.setOnClickListener(this);
        tv_amount_expense = (TextView) findViewById(R.id.tv_amount_expense);

        account_name = (TextView) findViewById(R.id.account_name);
        account_amount = (TextView) findViewById(R.id.account_amount);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        et_remark = (EditText) findViewById(R.id.et_remark);
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            text_datetime.setText(mFormatter.format(date));
        }

        @Override
        public void onDateTimeCancel() {
        }
    };

    private void initData() {

    }


    // Menu
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.delete_account:
                    showDialog();
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editaccount, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Act_editExpense.this, Act_Main.class);
            int page = getIntent().getIntExtra("flag", 0);
            intent.putExtra("flag",page);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        // MaterialDialog
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("删除账户");
        mMaterialDialog.setMessage("是否删除这个账户?");
        mMaterialDialog.setPositiveButton("是", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expenseDao.delete(id);
                Intent i = new Intent(Act_editExpense.this, Act_Main.class);
                i.putExtra("flag",1);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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


    @Override
    public void onClick(View view) {
        String str = tv_amount_expense.getText().toString();
        switch (view.getId()) {
            case R.id.ll_record0:
                DialogCalculator.OnSureClickListener listener1 = new DialogCalculator.OnSureClickListener() {
                    @Override
                    public void getText(String string) {
                        tv_amount_expense.setText(string);
                    }
                };
                DialogCalculator dialogCalculator = new DialogCalculator(this, str, R.style.MyDialog, listener1);
                dialogCalculator.show();
                break;
            case R.id.ll_account_record:
                DialogAccount.OnItemClickListener listener2 = new DialogAccount.OnItemClickListener() {

                    @Override
                    public void getText(int accountId,String str_name, double str_amount) {
                        accountsId = accountId;
                        account_name.setText(str_name);
                        account_amount.setText(str_amount + "");
                    }
                };
                DialogAccount dialogAccount = new DialogAccount(this, listener2, R.style.MyDialog);
                dialogAccount.show();
                dialogAccount.setCanceledOnTouchOutside(false);
                break;
            case R.id.ll_record2:
                DialogExpenseCategory.OnItemClickListener listener3 = new DialogExpenseCategory.OnItemClickListener() {
                    @Override
                    public void getText(int categoryId,String str_title) {
                        categorysId =categoryId;
                        category_title.setText(str_title);
                    }
                };
                DialogExpenseCategory dialogExpenseCategory = new DialogExpenseCategory(this, listener3, R.style.MyDialog);
                dialogExpenseCategory.show();
                dialogExpenseCategory.setCanceledOnTouchOutside(false);
                break;
            case R.id.btn_save:
                String amount = tv_amount_expense.getText().toString();
                if(amount.equals("0.0")){
                    ToastUtil.show(this, "请输入金额", 200);
                }else {
                    double num = Double.parseDouble(amount);
                    int category = categorysId;
                    int account = accountsId;
                    String dateTime = text_datetime.getText().toString();
                    String mark = et_remark.getText().toString();
                    Expense expense = new Expense(num, category, account, dateTime, mark);
                    System.out.println(expense);
                    Intent intent = getIntent();
                    id = intent.getIntExtra("id", 0);
                    expense.id = id;
                    expenseDao.update(expense);
                    ToastUtil.show(this, "修改成功");
                    Intent i = new Intent(this, Act_Main.class);
                    i.putExtra("flag",1);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    break;
                }
        }
    }

    // exit
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当点击硬键盘上的返回键时，提醒用户是否要退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Act_editExpense.this, Act_Main.class);
            int page = getIntent().getIntExtra("flag", 0);
            intent.putExtra("flag",page);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
