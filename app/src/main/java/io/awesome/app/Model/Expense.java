package io.awesome.app.Model;

import java.io.Serializable;

/**
 * Created by sung on 10/06/2018.
 */

public class Expense implements Serializable{
    private String id;
    private String name;
    private int price;
    private int quantity;
    private String unit;
    private String note;
    private long createAt;
    private String createBy;
    private long approvedAt;
    private String approvedBy;
    private String status;

    public Expense(String id, String name, int price, int quantity, String unit, String note, long createAt, String createBy, long approvedAt, String approvedBy, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.note = note;
        this.createAt = createAt;
        this.createBy = createBy;
        this.approvedAt = approvedAt;
        this.approvedBy = approvedBy;
        this.status = status;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(long approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
