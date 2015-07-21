package hyh.money.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hyh.money.model.Category;
import hyh.money.db.MySQLiteOpenHelper;

/**
 * Created by HuangYH on 2015/5/1.
 */
public class CategoryDao {

    MySQLiteOpenHelper mySQLiteOpenHelper;
    private static final String TABLE = "category";

    public CategoryDao(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    // add
    public void add(Category c) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("insert into category(title,type) values(?,?)", new Object[]{c.getTitle(), c.getType()});
        db.close();
    }

    // delete
    public void delete(Integer id) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("delete from category where category_id=?", new Object[]{id});
        db.close();
    }

    // update
    public void update(Category c) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("update category set title=? where category_id=?", new Object[]{c.getTitle(), c.getId()});
        db.close();
    }

    // select id
    public Category find(Integer id) {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from category where category_id=?", new String[]{id.toString()});
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("category_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            Category c = new Category(id, title, type);
            return c;
        }
        cursor.close();
        db.close();
        return null;
    }

    // findAll
    public List<Category> getAll() {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = db.query("category", new String[]{"category_id", "title"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("category_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Category c = new Category(id, title);
            categories.add(c);
        }
        cursor.close();
        db.close();
        return categories;
    }

    public List<Category> getExpenseList(){
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = db.rawQuery("select category_id,title from category where type='0' ", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("category_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Category c = new Category(id, title);
            categories.add(c);
        }
        cursor.close();
        db.close();
        return categories;
    }

    public List<Category> getIncomeList(){
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = db.rawQuery("select category_id,title from category where type='1' ",null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("category_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Category c = new Category(id, title);
            categories.add(c);
        }
        cursor.close();
        db.close();
        return categories;
    }

}
