package com.example.sklep;

import com.example.sklep.records.ProductRecord;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopOrder {
    private final ArrayList<ProductRecord> productRecords = new ArrayList<>();

    public ProductRecord[] getProducts() {
        return productRecords.toArray( new ProductRecord[0]);
    }

    public void addToOrder(ProductRecord productRecord) {
        productRecords.add(productRecord);
    }
    public void removeFromOrderById(int id) {

        for (ProductRecord p : productRecords) {
            if (p.getId() == id) {
                productRecords.remove(p);
                return;
            }
        }
    }

    public double getTotalPrice() {
        return productRecords.stream().mapToDouble(ProductRecord::getPrice).sum();
    }


    public static class ProductCountsMap extends HashMap<Integer, Integer> {

    }

    public ProductCountsMap getProductCounts() {
        ProductCountsMap counts = new ProductCountsMap();

        for (ProductRecord productRecord : productRecords) {

            Integer cnt = counts.get(productRecord.getId());
            if (cnt == null) {
                counts.put(productRecord.getId(), 1);
                continue;
            }

            counts.put(productRecord.getId(), cnt+1);
        }

        return counts;
    }
}
