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

    public String printInfo() {
        return name + " | " + price + "원 | " + content;
    }

    public String printDetailInfo() {
        return name + " | " + price + "원 | " + content + " | 재고: " + quantity;
    }

    public void buy(int quantity) {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        } else {
            System.out.println("재고가 부족합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getContent() {
        return content;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
