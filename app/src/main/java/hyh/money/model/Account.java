package hyh.money.model;

import java.io.Serializable;

/**
 * Created by HuangYH on 2015/4/12.
 */
public class Account implements Serializable {

    public Integer id;
    public String name;
    public double amount;

    public Account(Integer id, String name, double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Account(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Account(String name, double amount) {
        super();
        this.name = name;
        this.amount = amount;
    }

    public Account(String name) {
        super();
        this.name = name;
    }

    public Account() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
