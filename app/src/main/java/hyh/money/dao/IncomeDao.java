package hyh.money.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hyh.money.model.Expense;
import hyh.money.model.Income;
import hyh.money.db.MySQLiteOpenHelper;

/**
 * Created by HuangYH on 2015/4/12.
 */
public class IncomeDao {

    MySQLiteOpenHelper mySQLiteOpenHelper;

    public IncomeDao(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    // add
    public void add(Income i) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("insert into income(amount,category_id,account_id,datetime,remark) values(?,?,?,?,?)", new Object[]{ i.getAmount(),i.getCategoryId(),i.getAccountId(),i.getDatetime(),i.getRemark()});
    }

    // delete
    public void delete(Integer id) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("delete from income where income_id=?", new Object[]{id});
    }

    // update
    public void update(Income i) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("update income set amount=?,category_id=?,account_id=?,datetime=?,remark=? where income_id=?", new Object[]{i.getAmount(), i.getCategoryId(), i.getAccountId(), i.getDatetime(), i.getRemark(), i.getId()});
    }

    // select id
    public Income find(Integer id) {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from income where income_id=?", new String[]{id.toString()});
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("income_id"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            int categoryId = cursor.getInt(cursor.getColumnIndex("category_id"));
            int accountId = cursor.getInt(cursor.getColumnIndex("account_id"));
            String datetime = cursor.getString(cursor.getColumnIndex("datetime"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Income i = new Income(id,amount,categoryId,accountId,datetime,remark);
            return i;
        }
        cursor.close();
        return null;
    }

    // findAll
    public List<Income> getAll() {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        List<Income> incomes = new ArrayList<Income>();
        Cursor cursor =db.rawQuery("select * from income",null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("income_id"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            int categoryId = cursor.getInt(cursor.getColumnIndex("category_id"));
            int accountId = cursor.getInt(cursor.getColumnIndex("account_id"));
            String datetime = cursor.getString(cursor.getColumnIndex("datetime"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Income i = new Income(id,amount,categoryId,accountId,datetime,remark);
            incomes.add(i);
        }
        cursor.close();
        db.close();
        return incomes;
    }

    // getSum(amount)
    public double getSum() {
        double sum = 0;
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        String sql = "select sum(amount) as sum from income";
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

    public List<Income> getSumCategory() {
        double sum = 0;
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        List<Income> expenses = new ArrayList<Income>();
        String sql = "select category_id,sum(amount) as sum from income group by category_id";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int categoryId = cursor.getInt(cursor.getColumnIndex("category_id"));
            sum = cursor.getDouble(cursor.getColumnIndex("sum"));
            Income i = new Income( categoryId, sum);
            expenses.add(i);
        }
        cursor.close();
        db.close();
        return expenses;
    }

}
