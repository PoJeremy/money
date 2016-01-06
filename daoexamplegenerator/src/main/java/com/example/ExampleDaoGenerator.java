package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception{

        Schema schema = new Schema(1,"com.f1reking.greendao");

        addNote(schema);

        new DaoGenerator().generateAll(schema,"E:/workspace/Android Studio/Money/mymoney/src/main/java-gen");


    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }
}
