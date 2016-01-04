package hyh.money.ui.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import hyh.money.R;
import hyh.money.ui.main.Act_Main;
import hyh.money.utils.ToastUtil;

/**
 * Created by HuangYH on 2015/6/7.
 */
public class Act_Setting extends ActionBarActivity {

    private LinearLayout ll_share;
    private LinearLayout ll_setpw;
    private LinearLayout ll_mydata;
    private Toolbar mToolbar;
    private SwitchCompat pwswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_setting);


        // toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.text_settings));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));//设置标题颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {

        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        ll_share.setOnClickListener(new OnshareClick());
        ll_setpw = (LinearLayout) findViewById(R.id.ll_setpw);
        ll_setpw.setOnClickListener(new OnsetPw());
        ll_mydata = (LinearLayout) findViewById(R.id.ll_mydata);
        ll_mydata.setOnClickListener(new OnmyData());
        pwswitch = (SwitchCompat) findViewById(R.id.pw_switch);
        pwswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {

                }
                else {
                    SharedPreferences preferences = getSharedPreferences("locked",MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.clear();
                    edit.commit();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Act_Setting.this, Act_Main.class);
            int page = getIntent().getIntExtra("flag", 0);
            intent.putExtra("flag", page);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 分享应用事件
    class OnshareClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, "你总以为你不会理财，那是因为你还没用App'我的钱包' ，下载地址:https://fir.im/mymoney");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "分享到"));
        }
    }

    //设置密码事件
    class OnsetPw implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Act_Setting.this,Act_GestureLock.class);
            startActivity(intent);
        }
    }

    //我的数据事件
    class OnmyData implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(Act_Setting.this,Act_Data.class);
            startActivity(intent1);
        }
    }

    // exit
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当点击键盘上的返回键时，提醒用户是否要退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Act_Setting.this, Act_Main.class);
            int page = getIntent().getIntExtra("flag", 0);
            intent.putExtra("flag", page);
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
