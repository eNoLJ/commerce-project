package com.enolj.commerce;

public class Product {

    private String name;

    private int price;

    private String content;

    private int quantity;

    public Product(String name, int price, String content, int quantity) {
        this.name = name;
        this.price = price;
        this.content = content;
        this.quantity = quantity;
    }

    public String printProduct() {
        return name + " " + price + " " + content;
    }
}
