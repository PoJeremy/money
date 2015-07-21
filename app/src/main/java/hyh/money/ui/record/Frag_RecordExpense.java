package hyh.money.ui.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hyh.money.R;
import hyh.money.model.Account;
import hyh.money.model.Category;
import hyh.money.model.Expense;
import hyh.money.dao.AccountDao;
import hyh.money.dao.CategoryDao;
import hyh.money.dao.ExpenseDao;
import hyh.money.ui.main.Act_Main;
import hyh.money.utils.ToastUtil;
import hyh.money.view.Account.DialogAccount;
import hyh.money.view.Calc.DialogCalculator;
import hyh.money.view.Category.DialogExpenseCategory;
import hyh.money.view.DateTimePick.SlideDateTimeListener;
import hyh.money.view.DateTimePick.SlideDateTimePicker;

public class Frag_RecordExpense extends Fragment implements View.OnClickListener {

    public static final String TAG = Frag_RecordExpense.class.getSimpleName();
    private TextView category_title;
    private TextView text_datetime;
    private TextView tv_amount_expense;
    private TextView account_name;
    private TextView account_amount;
    private LinearLayout ll_record0;
    private LinearLayout ll_record2;
    private LinearLayout ll_account_record;
    private TextView account_tip;
    private TextView category_tip;
    private Button btn_save;
    private EditText et_remark;
    private String str_name;
    private double str_amount;
    private List<Account> accountList;
    private Account account;
    private List<Category> categoryList;
    private Category category;
    private ExpenseDao expenseDao;
    private AccountDao accountDao;
    private CategoryDao categoryDao;
    private int accountsId;
    private int categorysId;


    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_expense_record, container, false);
        expenseDao = new ExpenseDao(getActivity());
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        // 账户
        ll_account_record = (LinearLayout) view.findViewById(R.id.ll_account_record);
        ll_account_record.setOnClickListener(this);

        // 分类
        category_title = (TextView) view.findViewById(R.id.category_title);
        ll_record2 = (LinearLayout) view.findViewById(R.id.ll_record2);
        ll_record2.setOnClickListener(this);

        text_datetime = (TextView) view.findViewById(R.id.text_datetime);
        text_datetime.setText(mFormatter.format(new Date()));
        text_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
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
        ll_record0 = (LinearLayout) view.findViewById(R.id.ll_record0);
        ll_record0.setOnClickListener(this);
        tv_amount_expense = (TextView) view.findViewById(R.id.tv_amount_expense);

        account_name = (TextView) view.findViewById(R.id.account_name);
        account_amount = (TextView) view.findViewById(R.id.account_amount);

        btn_save = (Button) view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        et_remark = (EditText) view.findViewById(R.id.et_remark);

        account_tip = (TextView) view.findViewById(R.id.account_tip);
        category_tip = (TextView) view.findViewById(R.id.category_tip);

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

    public void initData() {

        accountDao = new AccountDao(getActivity());
        accountList = accountDao.getAll();

        categoryDao = new CategoryDao(getActivity());
        categoryList = categoryDao.getExpenseList();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    ;

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
                DialogCalculator dialogCalculator = new DialogCalculator(getActivity(), str, R.style.MyDialog, listener1);
                dialogCalculator.show();
                break;
            case R.id.ll_account_record:
                DialogAccount.OnItemClickListener listener2 = new DialogAccount.OnItemClickListener() {

                    @Override
                    public void getText(int accountId, String str_name, double str_amount) {
                        accountsId = accountId;
                        account_name.setText(str_name);
                        account_amount.setText(str_amount + "");
                        account_tip.setVisibility(View.GONE);
                    }
                };
                DialogAccount dialogAccount = new DialogAccount(getActivity(), listener2, R.style.MyDialog);
                dialogAccount.show();
                dialogAccount.setCanceledOnTouchOutside(false);
                break;
            case R.id.ll_record2:
                DialogExpenseCategory.OnItemClickListener listener3 = new DialogExpenseCategory.OnItemClickListener() {
                    @Override
                    public void getText(int categoryId, String str_title) {
                        categorysId = categoryId;
                        category_title.setText(str_title);
                        category_tip.setVisibility(View.GONE);
                    }
                };
                DialogExpenseCategory dialogExpenseCategory = new DialogExpenseCategory(getActivity(), listener3, R.style.MyDialog);
                dialogExpenseCategory.show();
                dialogExpenseCategory.setCanceledOnTouchOutside(false);
                break;
            case R.id.btn_save:
                String amount = tv_amount_expense.getText().toString();
                if (amount.equals("0.0")) {
                    ToastUtil.show(getActivity(), "请输入金额", 200);
                } else {
                    double num = Double.parseDouble(amount);
                    int category = categorysId;
                    int accounts = accountsId;
                    String dateTime = text_datetime.getText().toString();
                    String mark = et_remark.getText().toString();
                    Expense expense = new Expense(num, category, accounts, dateTime, mark);
                    account = accountDao.find(accounts);
                    double amountNum = account.getAmount();
                    double amounts = amountNum - num;
                    account.id = accounts;
                    account.name = account.getName();
                    account.amount = amounts;
                    if (num > account.amount) {
                        ToastUtil.show(getActivity(), "输入的金额不能大于账号金额");
                    } else {
                        accountDao.update(account);
                        expenseDao.add(expense);
                        ToastUtil.show(getActivity(), "添加成功");
                        Intent intent = new Intent(getActivity(), Act_Main.class);
                        intent.putExtra("flag",1);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                        break;
                    }
                }
        }
    }

}
