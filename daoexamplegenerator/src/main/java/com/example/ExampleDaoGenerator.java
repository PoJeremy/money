package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception{

        Schema schema = new Schema(1,"com.f1reking.greendao");

        addAccount(schema);

        new DaoGenerator().generateAll(schema,"E:/workspace/Android Studio/Money/mymoney/src/main/java-gen");


    }

    private static void addAccount(Schema schema) {
        Entity account = schema.addEntity("Account");
        account.addIdProperty().autoincrement();
        account.addStringProperty("name").notNull();
        account.addDoubleProperty("amount").notNull();
    }
}
