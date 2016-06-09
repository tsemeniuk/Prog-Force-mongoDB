package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

@Entity(noClassnameStored = true)
public class Product {
    @Id
    private ObjectId id = new ObjectId();
    @Indexed
    private String title;
    @Indexed
    private int price;
    @Indexed
    private String status;

    public Product() {
    }

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
