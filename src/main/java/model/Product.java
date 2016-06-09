package model;

import org.mongodb.morphia.annotations.Embedded;

//@Indexes(@Index(fields = @Field(value = "$**", type = IndexType.TEXT)))
@Embedded
public class Product {

    private String title;
    private int price;
    private String status;

    public Product(String title, int price, String status) {
        this.title = title;
        this.price = price;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
