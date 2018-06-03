package io.awesome.app.Model;

import java.util.List;

/**
 * Created by sung on 18/08/2017.
 */

public class Menu {

    private String id;
    private String name;
    private int price;
    private String urlImage;
    private String categoryId;
    private boolean visible;

    public Menu(String id, String name, int price, String urlImage, String categoryId, boolean visible) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
        this.categoryId = categoryId;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
