package io.awesome.app.Model;

import java.util.List;

public class Table {

    private String id;
    private String name;
    private int x;
    private int y;
    private int width;
    private int height;
    private String receiptId;

    public Table(String id, String name, int x, int y, int width, int height, String receiptId) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }


    public class ListTable{
        private List<Table> response;

        public List<Table> getResponse() {
            return response;
        }

        public void setResponse(List<Table> response) {
            this.response = response;
        }
    }








}
