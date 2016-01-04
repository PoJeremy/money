package hyh.money.model;


/**
 * Created by HuangYH on 2015/4/12.
 */
public class Expense {

    public int id;           //支出编号ID
    public double amount;   //支出金额
    public int categoryId;  //支出类型编号ID
    public int accountId;   //支出账户编号ID
    public String datetime; //支出时间
    public String remark;   //支出备注
    public double sum;

    public Expense(double amount, int categoryId, int accountId, String datetime, String remark) {
        super();
        this.amount = amount;
        this.categoryId = categoryId;
        this.accountId = accountId;
        this.datetime = datetime;
        this.remark = remark;
    }

    public Expense(int id, double amount, int categoryId, int accountId, String datetime, String remark) {
        super();
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
        this.accountId = accountId;
        this.datetime = datetime;
        this.remark = remark;
    }

    public Expense( int categoryId, double sum) {
        super();
        this.categoryId = categoryId;
        this.sum = sum;
    }

    public Expense() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
