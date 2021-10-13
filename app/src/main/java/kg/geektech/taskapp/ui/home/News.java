package kg.geektech.taskapp.ui.home;

import java.io.Serializable;

public class News implements Serializable {

    private String title;

    public News(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
