package io.awesome.app.Model;

import java.util.List;

/**
 * Created by sung on 20/08/2017.
 */

public class Receipt {
    private String id;
    private String tableId;
    private int totalPrice;
    private List<Ordered> items;
    private int createAt;
    private int payAt;

    public Receipt(String id, String tableId, int totalPrice, List<Ordered> items, int createAt, int payAt) {
        this.id = id;
        this.tableId = tableId;
        this.totalPrice = totalPrice;
        this.items = items;
        this.createAt = createAt;
        this.payAt = payAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Ordered> getItems() {
        return items;
    }

    public void setItems(List<Ordered> items) {
        this.items = items;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getPayAt() {
        return payAt;
    }

    public void setPayAt(int payAt) {
        this.payAt = payAt;
    }
}
