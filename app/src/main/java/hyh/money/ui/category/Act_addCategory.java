package hyh.money.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import hyh.money.R;
import hyh.money.model.Category;
import hyh.money.dao.CategoryDao;
import hyh.money.ui.main.Act_Main;
import hyh.money.utils.ToastUtil;

/**
 * Created by HuangYH on 2015/5/1.
 */
public class Act_addCategory extends ActionBarActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private Button saveButton;
    private EditText et_category_title;
    private Spinner spinner_category;
    private CategoryDao categoryDao;
    private String[] categoryList = {"支出","收入"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_addcategory);
        initView();
        // toolbar
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.text_new_category));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryDao = new CategoryDao(this);

    }

    private void initView(){
        // save
        saveButton = (Button)findViewById(R.id.bt_add_category);
        saveButton.setOnClickListener(this);
        et_category_title = (EditText)findViewById(R.id.et_category_title);
        spinner_category = (Spinner)findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, categoryList);
        adapter.setDropDownViewResource(R.layout.item_spinner_category);
        spinner_category.setAdapter(adapter);
        spinner_category.setGravity(Gravity.CENTER_HORIZONTAL);
        spinner_category.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Act_addCategory.this, Act_Main.class);
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
        switch (view.getId()){
            case R.id.bt_add_category:
                String str = et_category_title.getText().toString().trim();
                if(TextUtils.isEmpty(str)){
                    ToastUtil.show(this, "分类标题不能为空");
                }else{

                    String title = et_category_title.getText().toString();
                    String spinner = spinner_category.getSelectedItem().toString();
                    int i;
                    if(spinner.equals("支出")){
                        i = 0;
                    }else{
                        i = 1;
                    }
                    int type= i;
                    Category accountBean = new Category(title,type);
                    categoryDao.add(accountBean);
                    ToastUtil.show(this, "添加成功");
                    Intent intent = new Intent(this, Act_Main.class);
                    intent.putExtra("flag",3);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Act_addCategory.this.finish();
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
            Intent intent = new Intent(Act_addCategory.this, Act_Main.class);
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
