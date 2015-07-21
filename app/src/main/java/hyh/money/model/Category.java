package hyh.money.model;

/**
 * Created by HuangYH on 2015/4/12.
 */
public class Category {

//    public static final String TABLE = "category";
//    public static final String KEY_ID = "id";
//    public static final String KEY_TITLE = "title";
//    public static final String KEY_TYPE = "type";

    public int id;
    public String title; //标题
    public int type; // 0: 支出 ，1：收入

    public Category(int id, String title) {
        super();
        this.id = id;
        this.title = title;
    }
    public Category(int id, String title, int type) {
        super();
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public Category(String title){
        super();
        this.title = title;
    }

    public Category(String title, int type) {
        super();
        this.title = title;
        this.type = type;
    }

    public Category(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
