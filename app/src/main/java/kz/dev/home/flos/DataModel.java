package kz.dev.home.flos;

public class DataModel {
    String title;
    String desc;
    public DataModel(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
