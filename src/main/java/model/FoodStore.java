package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.*;
import util.DataSource;

import java.util.List;

@Entity(noClassnameStored = true)
public class FoodStore implements Store {
    @Id
    private ObjectId id = new ObjectId();
    @Indexed
    private String name;
    @Reference
    private List<Category> categoryList;
    private static FoodStore ourInstance = new FoodStore();

    public static FoodStore getInstance() {
        return ourInstance;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product get(String title) {
        Datastore db = DataSource.getInstance().getDataSource("PROG_FORCE");
        return db.find(Product.class).field("title").equal(title).get();
    }

    public void add(String categoryName, Product product) {
        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        ds.save(product);
    }

    public void changePrice(String title, int price) {
        Datastore db = DataSource.getInstance().getDataSource("PROG_FORCE");
        Product product = db.find(Product.class).field("title").equal(title).get();
        product.setPrice(price);
        db.save(product);
    }

    public void changeStatus(String title, String status) {
        Datastore db = DataSource.getInstance().getDataSource("PROG_FORCE");
        Product product = db.find(Product.class).field("title").equal(title).get();
        product.setStatus(status);
        db.save(product);
    }

    @Override
    public String toString() {
        return "FoodStore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryList=" + categoryList +
                '}';
    }
}
