package hyh.money.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mrengineer13.snackbar.SnackBar;

import hyh.money.R;
import hyh.money.model.Account;
import hyh.money.dao.AccountDao;
import hyh.money.ui.main.Act_Main;
import hyh.money.utils.ToastUtil;
import hyh.money.view.Calc.DialogCalculator;

/**
 * Created by HuangYH on 2015/4/7.
 */
public class Act_addAccount extends ActionBarActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Button saveButton;
    private EditText et_account_name;
    private AccountDao accountDao;
    private TextView amount_account;
    private LinearLayout ll_amount_account_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_addaccount);

        // toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.text_new_account));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));//设置标题颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        accountDao = new AccountDao(this);
        initView();
    }

    public void initView() {

        saveButton = (Button) findViewById(R.id.bt_add_account);
        saveButton.setOnClickListener(this);
        et_account_name = (EditText) findViewById(R.id.et_account_name);
        amount_account = (TextView) findViewById(R.id.tv_amount_account_add);
        ll_amount_account_add = (LinearLayout) findViewById(R.id.ll_amount_account_add);
        ll_amount_account_add.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Act_addAccount.this, Act_Main.class);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_account:
                String str = et_account_name.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    ToastUtil.show(this, "账户标题不能为空");
                } else {
                    String name = et_account_name.getText().toString();
                    String amount = amount_account.getText().toString();
                    double num = Double.parseDouble(amount);
                    Account account = new Account(name,num);
                    accountDao.add(account);
                    ToastUtil.show(this, "添加成功");
                    Intent intent = new Intent(this, Act_Main.class);
                    intent.putExtra("flag",0);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Act_addAccount.this.finish();
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }
                break;
            case R.id.ll_amount_account_add:
                String str1 = amount_account.getText().toString();
                DialogCalculator.OnSureClickListener listener1 = new DialogCalculator.OnSureClickListener() {
                    @Override
                    public void getText(String string) {
                        amount_account.setText(string);
                    }
                };
                DialogCalculator dialogCalculator = new DialogCalculator(Act_addAccount.this, str1, R.style.MyDialog, listener1);
                dialogCalculator.show();
                break;
        }
    }

    // exit
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当点击硬键盘上的返回键时，提醒用户是否要退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Act_addAccount.this, Act_Main.class);
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
