package com.example.sklep;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ShopOrder {
    private final ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addToOrder(Product product) {
        products.add(product);
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
