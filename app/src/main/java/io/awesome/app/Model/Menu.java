package io.awesome.app.Model;

import java.util.List;

/**
 * Created by sung on 18/08/2017.
 */

public class Menu {

    private String _id;
    private String name;
    private int price;
    private String urlImage;
    private String description;
    private boolean visible;
    private int quantity;


    public Menu(String _id, String name, int price, String urlImage, String description, boolean visible) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
        this.description = description;
        this.visible = visible;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public class ListMenu{
        private List<Menu> response;

        public List<Menu> getResponse() {
            return response;
        }

        public void setResponse(List<Menu> response) {
            this.response = response;
        }
    }
}
