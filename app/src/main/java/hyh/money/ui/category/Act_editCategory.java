package hyh.money.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import hyh.money.R;
import hyh.money.model.Category;
import hyh.money.dao.CategoryDao;
import hyh.money.ui.main.Act_Main;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by HuangYH on 2015/5/9.
 */
public class Act_editCategory extends ActionBarActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Spinner spinner_category;
    private EditText category_title;
    private Button save_category;
    private int id;
    private int type;
    private CategoryDao dao;
    private Category category;
    private String[] categoryList = {"支出", "收入"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editcategory);
        initView();
        // toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.text_edit_category));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));//���ñ�����ɫ
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        dao = new CategoryDao(this);
        category = new Category();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        type = intent.getIntExtra("type", 0);
        category = dao.find(id);
        category_title.setText(category.getTitle().toString());
        spinner_category.setSelection(type == 0 ? 0 : 1, true);
    }

    private void initView() {
        spinner_category = (Spinner) findViewById(R.id.spinner_category);
        category_title = (EditText) findViewById(R.id.et_category_title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, categoryList);
        adapter.setDropDownViewResource(R.layout.item_spinner_category);
        spinner_category.setAdapter(adapter);
        spinner_category.setGravity(Gravity.CENTER_HORIZONTAL);
        spinner_category.setVisibility(View.VISIBLE);
        save_category = (Button) findViewById(R.id.save_category);
        save_category.setOnClickListener(this);
    }

    // Menu
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.delete_category:
                    showDialog();
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editcategory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Act_editCategory.this, Act_Main.class);
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
        mMaterialDialog.setTitle("删除类型");
        mMaterialDialog.setMessage("是否删除这个类型?");
        mMaterialDialog.setPositiveButton("是", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.delete(id);
                Intent i = new Intent(Act_editCategory.this, Act_Main.class);
                i.putExtra("flag",3);
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
            case R.id.save_category:
                String str = category_title.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    Toast.makeText(Act_editCategory.this, "分类标题不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    category = new Category();
                    dao = new CategoryDao(Act_editCategory.this);
                    String spinner = spinner_category.getSelectedItem().toString();
                    int i;
                    if (spinner.equals("支出")) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    Intent intent = getIntent();
                    id = intent.getIntExtra("id", 0);
                    category.id = id;
                    category.title = category_title.getText().toString();
                    category.type = i;
                    dao.update(category);
                    Toast.makeText(Act_editCategory.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Act_editCategory.this, Act_Main.class);
                    intent1.putExtra("flag",3);
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
            Intent intent = new Intent(Act_editCategory.this, Act_Main.class);
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
