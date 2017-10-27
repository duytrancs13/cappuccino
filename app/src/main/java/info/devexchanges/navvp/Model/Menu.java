package info.devexchanges.navvp.Model;

/**
 * Created by sung on 18/08/2017.
 */

public class Menu {

    private String id;
    private String name;
    private int price;
    private String urlImage;
    private String description;
    private int favorite;

    public Menu(String id, String name, int price, String urlImage, String description, int favorite) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
        this.description = description;
        this.favorite = favorite;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
