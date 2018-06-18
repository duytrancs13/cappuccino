package io.awesome.app.Model;

import java.util.List;

/**
 * Created by sung on 20/08/2017.
 */

public class Receipt {
    private String id;
    private String tableId;
    private String tableName;
    private List<Ordered> items;
    private long createAt;
    private boolean isPay;
    private int payAt;

    public Receipt(String id, String tableId, String tableName, List<Ordered> items, long createAt, boolean isPay, int payAt) {
        this.id = id;
        this.tableId = tableId;
        this.tableName = tableName;
        this.items = items;
        this.createAt = createAt;
        this.isPay = isPay;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Ordered> getItems() {
        return items;
    }

    public void setItems(List<Ordered> items) {
        this.items = items;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public int getPayAt() {
        return payAt;
    }

    public void setPayAt(int payAt) {
        this.payAt = payAt;
    }


}
