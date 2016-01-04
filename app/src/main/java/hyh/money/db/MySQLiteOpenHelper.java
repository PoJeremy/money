package hyh.money.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HuangYH on 2015/4/12.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context) {
        super(context, "money.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //账户表
        String sql = "create table account (account_id integer primary key autoincrement , name varchar(20) not null ,amount double not null);";
        db.execSQL(sql);

        //收入表
        sql = "create table income (income_id integer primary key autoincrement, amount double not null, category_id integer not null ,account_id integer not null ,datetime datetime not null, remark varchar(200) not null ,"+"foreign key(account_id) references account(account_id)"+ "on delete cascade, "+"foreign key(category_id) references category(category_id)"+"on delete cascade);";
        db.execSQL(sql);

        //支出表
        sql = "create table expense (expense_id integer primary key autoincrement, amount double not null ,  category_id integer not null ,account_id integer not null,datetime datetime not null, remark varchar(200) not null,"+"foreign key(account_id) references account(account_id)"+ "on delete cascade ,"+"foreign key(category_id) references category(category_id)"+"on delete cascade);";
        db.execSQL(sql);

        //分类表
        sql = "create table category (category_id integer primary key autoincrement,title varchar(20) not null, type int not null);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
