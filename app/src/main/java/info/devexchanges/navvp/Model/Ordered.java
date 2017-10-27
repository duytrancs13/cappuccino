package info.devexchanges.navvp.Model;

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

    public Ordered(String itemId, String name, int price, String urlImage, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
        this.quantity = quantity;
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

    public String getSrcImage() {
        return urlImage;
    }

    public void setSrcImage(String srcImage) {
        this.urlImage = srcImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
