package io.awesome.app.Model;

import java.util.List;

/**
 * Created by sung on 17/08/2017.
 */

public class Category {
    private String id;
    private String name;
    private String urlImage;
    private boolean visible;

    public Category(String id, String name, String urlImage, boolean visible) {
        this.id = id;
        this.name = name;
        this.urlImage = urlImage;
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
