package io.awesome.app.Model;

import java.util.List;

/**
 * Created by sung on 17/08/2017.
 */

public class Category {
    private String id;
    private String name;
    private int createAt;
    private String urlImage;
    private List<String> listMenuOfCategory;

    public Category(String id, String name, int createAt, String urlImage, List<String> listMenuOfCategory) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.urlImage = urlImage;
        this.listMenuOfCategory = listMenuOfCategory;
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

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<String> getListMenuOfCategory() {
        return listMenuOfCategory;
    }

    public void setListMenuOfCategory(List<String> listMenuOfCategory) {
        this.listMenuOfCategory = listMenuOfCategory;
    }

    public class ListCategory{
        private List<Category> response;

        public List<Category> getListCategory() {
            return response;
        }

        public void setListCategory(List<Category> listCategory) {
            this.response = listCategory;
        }
    }
}
