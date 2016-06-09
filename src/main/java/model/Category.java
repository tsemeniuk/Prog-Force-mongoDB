package model;


import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

@Embedded
public class Category {

    private String name;
    @Embedded
    private List<Product> productList;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }


    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", productList=" + productList +
                '}';
    }
}
