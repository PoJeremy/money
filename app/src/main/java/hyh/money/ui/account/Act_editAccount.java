package hyh.money.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hyh.money.R;
import hyh.money.model.Account;
import hyh.money.dao.AccountDao;
import hyh.money.ui.main.Act_Main;
import hyh.money.view.Calc.DialogCalculator;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by HuangYH on 2015/4/8.
 */
public class Act_editAccount extends ActionBarActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private EditText account_name;
    private Button save_account;
    private int id;
    private AccountDao dao;
    private Account account;
    private LinearLayout ll_amount_account_edit;
    private TextView amount_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editaccount);
        initView();
        // toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("编辑账户");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));//设置标题颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        dao = new AccountDao(this);
        account = new Account();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        account = dao.find(id);
        account_name.setText(account.getName().toString());
        String str = ""+ account.getAmount();
        amount_account.setText(str.toString());
    }

    public void initView(){
        amount_account = (TextView)findViewById(R.id.tv_amount_account_edit);
        account_name = (EditText) findViewById(R.id.et_editaccount_title);
        account_name.requestFocus();
        save_account = (Button) findViewById(R.id.save_account);
        save_account.setOnClickListener(this);
        ll_amount_account_edit = (LinearLayout) findViewById(R.id.ll_amount_account_edit);
        ll_amount_account_edit.setOnClickListener(this);
    }

    // Menu
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
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
            Intent intent = new Intent(Act_editAccount.this, Act_Main.class);
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
                dao.delete(id);
                Intent i = new Intent(Act_editAccount.this, Act_Main.class);
                i.putExtra("flag",0);
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
        switch (view.getId()) {
            case R.id.ll_amount_account_edit:
                String str1 = amount_account.getText().toString();
                DialogCalculator.OnSureClickListener listener1 = new DialogCalculator.OnSureClickListener() {
                    @Override
                    public void getText(String string) {
                        amount_account.setText(string);
                    }
                };
                DialogCalculator dialogCalculator = new DialogCalculator(Act_editAccount.this, str1, R.style.MyDialog, listener1);
                dialogCalculator.show();
                break;
            case R.id.save_account:
                String str = account_name.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    Toast.makeText(Act_editAccount.this, "账户标题不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    account = new Account();
                    dao = new AccountDao(Act_editAccount.this);
                    Intent intent = getIntent();
                    id = intent.getIntExtra("id", 0);
                    account.id = id;
                    account.name = account_name.getText().toString();
                    String amount = amount_account.getText().toString();
                    double num = Double.parseDouble(amount);
                    account.amount = num;
                    dao.update(account);
                    Toast.makeText(Act_editAccount.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Act_editAccount.this, Act_Main.class);
                    intent1.putExtra("flag",0);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }
                break;
        }
    }

    // exit
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当点击硬键盘上的返回键时，提醒用户是否要退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Act_editAccount.this, Act_Main.class);
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