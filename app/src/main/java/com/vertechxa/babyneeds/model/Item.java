package com.vertechxa.babyneeds.model;

public class Item {

    private String item, itemColor;

    private String dateItemAdded;

    private int id, itemQuantity, itemSize;

    public Item() {
    }

    public Item(String item, String itemColor, String dateItemAdded, int itemQuantity, int itemSize) {
        this.item = item;
        this.itemColor = itemColor;
        this.dateItemAdded = dateItemAdded;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
    }

    public Item(String item, String itemColor, String dateItemAdded, int id, int itemQuantity, int itemSize) {
        this.item = item;
        this.itemColor = itemColor;
        this.dateItemAdded = dateItemAdded;
        this.id = id;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }
}
