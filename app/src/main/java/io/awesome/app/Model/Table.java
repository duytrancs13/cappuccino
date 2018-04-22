package io.awesome.app.Model;

import java.util.List;

public class Table {

    private String id;
    private String name;
    private int x;
    private int y;
    private boolean visible;
    private String status;
    private String receiptId;

    public Table(String id, String name, int x, int y, boolean visible, String status, String receiptId) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.status = status;
        this.receiptId = receiptId;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
}
