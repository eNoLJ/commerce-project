package com.enolj.commerce;

import java.util.List;

public class Category {

    private String name;

    private List<Product> products;

    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public int productSize() {
        return products.size();
    }

    public Product getProduct(int i) {
        return products.get(i);
    }

    public String getName() {
        return name;
    }
}
