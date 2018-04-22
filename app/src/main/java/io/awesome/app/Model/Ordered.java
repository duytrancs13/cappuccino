package io.awesome.app.Model;

import java.io.Serializable;

/**
 * Created by sung on 01/10/2017.
 */

public class Ordered implements Serializable {

    private String itemId;
    private String name;
    private int price;
    private String urlImage;
    private int quantity;
    private String note;

    public Ordered(String itemId, String name, int price, String urlImage, int quantity, String note) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
        this.quantity = quantity;
        this.note = note;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
