package service;

import model.Category;
import model.Product;
import org.mongodb.morphia.Datastore;
import util.DataSource;

import java.util.List;
import java.util.Random;

public class ProductService {
    private static ProductService ourInstance = new ProductService();

    public static ProductService getInstance() {
        return ourInstance;
    }

    private ProductService() {
    }

    public synchronized void changePrice() {
        Datastore db = DataSource.getInstance().getDataSource("PROG_FORCE");
        List<Product> products = db.find(Product.class).field("status").equal("Available").asList();
        if (products.size() != 0) {
            for (Product product : products) {
                product.setPrice(product.getPrice() * 100 / 80);
            }
        }
        db.save(products);
    }

    public synchronized void changeStatusExpected() {
        Datastore db = DataSource.getInstance().getDataSource("PROG_FORCE");
        List<Product> products = db.find(Product.class).field("status").notEqual("Absent").asList();
        if (products.size() != 0) {
            for (int i = 0; i < products.size() / 4; i++) {
                products.get(i).setStatus("Expected");
            }
        }
        db.save(products);
    }

    public synchronized void changeStatusAbsent() {
        Datastore db = DataSource.getInstance().getDataSource("PROG_FORCE");
        List<Category> categories = db.find(Category.class).asList();
        List<Product> products = categories.get(new Random().nextInt(categories.size())).getProductList();
        if (products.size() != 0) {
            for (Product product : products) {
                product.setStatus("Absent");
            }
        }
        db.save(products);
    }
}
