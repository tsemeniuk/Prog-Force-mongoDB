package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import util.DataSource;

import java.util.List;

@Entity(noClassnameStored = true)
public class FoodStore implements Store {
    @Id
    private ObjectId id = new ObjectId();
    @Property(value = "name")
    private String name;
    @Embedded
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
        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        FoodStore store = ds.find(FoodStore.class).get();
        for (Category category : store.getCategoryList()) {
            for (Product product : category.getProductList()) {
                if (title.equals(product.getTitle())) {
                    return product;
                }
            }
        }
        return null;
    }

    public void add(String categoryName, Product product) {
        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        FoodStore store = ds.find(FoodStore.class).get();

        for (Category category : store.getCategoryList()) {
            if (categoryName.equals(category.getName())) {
                category.getProductList().add(product);
                ds.save(store);
            }
        }
    }

    public void changePrice(String title, int price) {
        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        FoodStore store = ds.find(FoodStore.class).get();
        for (Category category : store.getCategoryList()) {
            for (Product product : category.getProductList()) {
                if (title.equals(product.getTitle())) {
                    product.setPrice(price);
                    ds.save(store);
                }
            }
        }

        /*dataSource = DataSource.getInstance();
        MongoDatabase db = dataSource.getDb("PROG_FORCE");
        MongoCollection<Document> store = db.getCollection("store");
        store.updateOne(eq("title", title), set("price", price));*/
    }

    public void changeStatus(String title, String status) {
        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        FoodStore store = ds.find(FoodStore.class).get();
        for (Category category : store.getCategoryList()) {
            for (Product product : category.getProductList()) {
                if (title.equals(product.getTitle())) {
                    product.setStatus(status);
                }
            }
        }
        ds.save(store);

        /*dataSource = DataSource.getInstance();
        MongoDatabase db = dataSource.getDb("PROG_FORCE");
        MongoCollection<Document> store = db.getCollection("store");
        store.updateOne(eq("title", title), set("status", status));*/
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
