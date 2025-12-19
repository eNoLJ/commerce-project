package com.enolj.commerce;

import java.util.List;

public class Category {

    private String name;

    private List<Product> products;

    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
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

    public List<Product> getProducts() {
        return products;
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
