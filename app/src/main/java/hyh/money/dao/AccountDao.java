package hyh.money.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hyh.money.model.Account;
import hyh.money.db.MySQLiteOpenHelper;

/**
 * Created by HuangYH on 2015/4/12.
 */
public class AccountDao {

    MySQLiteOpenHelper mySQLiteOpenHelper;
    private static final String TABLE = "account";

    public AccountDao(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    // add
    public void add(Account a) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("insert into account(name,amount) values(?,?)", new Object[]{a.getName(), a.getAmount()});
        db.close();
    }

    // delete
    public void delete(Integer id) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("delete from account where account_id=?", new Object[]{id});
        db.close();
    }

    // update
    public void update(Account a) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        db.execSQL("update account set name=?,amount=? where account_id=?", new Object[]{a.getName(), a.getAmount(), a.getId()});
        db.close();
    }

    // select id
    public Account find(Integer id) {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from account where account_id=?", new String[]{id.toString()});
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("account_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            Account a = new Account(id, name, amount);
            return a;
        }
        cursor.close();
        db.close();
        return null;
    }

    // getSum(amount)
    public double getSum() {

        double sum = 0;
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
            String sql = "select sum(amount) as sum from account";
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

    // findAll
    public List<Account> getAll() {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        List<Account> accounts = new ArrayList<Account>();
        Cursor cursor =db.rawQuery("select * from account",null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("account_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            Account a = new Account(id, name, amount);
            accounts.add(a);
        }
        cursor.close();
        db.close();
        return accounts;
    }

}
