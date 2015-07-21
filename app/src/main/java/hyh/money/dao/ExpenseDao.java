package hyh.money.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hyh.money.model.Expense;
import hyh.money.db.MySQLiteOpenHelper;

/**
 * Created by HuangYH on 2015/4/12.
 */
public class ExpenseDao {

    MySQLiteOpenHelper mySQLiteOpenHelper;

    public ExpenseDao(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    // add
    public void add(Expense e) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("insert into expense(amount,category_id,account_id,datetime,remark) values(?,?,?,?,?)", new Object[]{e.getAmount(), e.getCategoryId(), e.getAccountId(), e.getDatetime(), e.getRemark()});
    }

    // delete
    public void delete(Integer id) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("delete from expense where expense_id=?", new Object[]{id});
    }

    // update
    public void update(Expense e) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("update expense set amount=?,category_id=?,account_id=?,datetime=?,remark=? where expense_id=?", new Object[]{e.getAmount(), e.getCategoryId(), e.getAccountId(), e.getDatetime(), e.getRemark(), e.getId()});
    }

    // select id
    public Expense find(Integer id) {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from expense where expense_id=?", new String[]{id.toString()});
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("expense_id"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            int categoryId = cursor.getInt(cursor.getColumnIndex("category_id"));
            int accountId = cursor.getInt(cursor.getColumnIndex("account_id"));
            String datetime = cursor.getString(cursor.getColumnIndex("datetime"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Expense e = new Expense(id, amount, categoryId, accountId, datetime, remark);
            return e;
        }
        cursor.close();
        return null;
    }

    // findAll
    public List<Expense> getAll() {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        List<Expense> expenses = new ArrayList<Expense>();
        Cursor cursor = db.rawQuery("select * from expense", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("expense_id"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            int categoryId = cursor.getInt(cursor.getColumnIndex("category_id"));
            int accountId = cursor.getInt(cursor.getColumnIndex("account_id"));
            String datetime = cursor.getString(cursor.getColumnIndex("datetime"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Expense e = new Expense(id, amount, categoryId, accountId, datetime, remark);
            expenses.add(e);
        }
        cursor.close();
        db.close();
        return expenses;
    }

    // getSum(amount)
    public double getSum() {
        double sum = 0;
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        String sql = "select sum(amount) as sum from expense";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            sum = cursor.getDouble(cursor.getColumnIndex("sum"));
        }
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
        return sum;
    }

    public List<Expense> getSumCategory() {
        double sum = 0;
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        List<Expense> expenses = new ArrayList<Expense>();
        String sql = "select category_id,sum(amount) as sum from expense group by category_id";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int categoryId = cursor.getInt(cursor.getColumnIndex("category_id"));
            sum = cursor.getDouble(cursor.getColumnIndex("sum"));
            Expense e = new Expense( categoryId, sum);
            expenses.add(e);
        }
        cursor.close();
        db.close();
        return expenses;
    }
}
