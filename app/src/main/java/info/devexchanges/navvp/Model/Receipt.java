package info.devexchanges.navvp.Model;

import java.util.List;

/**
 * Created by sung on 20/08/2017.
 */

public class Receipt {
    private String id;
    private String tableId;
    private int totalPrice;
    private List<Ordered> items;

    public Receipt(String id, String tableId, int totalPrice, List<Ordered> items) {
        this.id = id;
        this.tableId = tableId;
        this.totalPrice = totalPrice;
        this.items = items;
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

    public List<Ordered> getListOrdered() {
        return items;
    }

    public void setListOrdered(List<Ordered> listOrdered) {
        this.items = listOrdered;
    }
}
