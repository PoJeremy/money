package hyh.money.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import hyh.money.R;
import hyh.money.utils.BackupTask;
import hyh.money.utils.ToastUtil;

/**
 * Created by HuangYH on 2015/6/7.
 */
public class Act_Data extends ActionBarActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private CardView dataRecover;
    private CardView dataBackup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_data);

        // toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.text_mydata));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));//设置标题颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

    }

    private void initView() {

        dataRecover = (CardView) findViewById(R.id.dataRecover);
        dataRecover.setOnClickListener(this);
        dataBackup = (CardView) findViewById(R.id.dataBackup);
        dataBackup.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Act_Data.this, Act_Setting.class);
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
        switch (view.getId()){
            case R.id.dataRecover:
                new BackupTask(this).execute("restroeDatabase");
                ToastUtil.show(this, "恢复成功");
                finish();
                break;

            case R.id.dataBackup:
                new BackupTask(this).execute("backupDatabase");
                ToastUtil.show(this, "备份成功");
                finish();
                break;
        }
    }
}
