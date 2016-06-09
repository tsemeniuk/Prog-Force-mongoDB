package util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import model.Category;
import model.FoodStore;
import model.Product;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.logging.Logger;


public class DataSource {
    public static final Logger LOG = Logger.getLogger(DataSource.class.getName());
    private MongoClient mongoClient;
    private static DataSource ourInstance = new DataSource();

    public static DataSource getInstance() {
        return ourInstance;
    }

    private DataSource() {
    }

    public MongoDatabase getDb(String name) {
        LOG.info("Connecting to database...");
        mongoClient = new MongoClient();
        return mongoClient.getDatabase(name);
    }
    public Datastore getDataSource(String name) {
        LOG.info("Connecting to database...");
        Morphia morphia = new Morphia();
        morphia.map(Product.class).map(Category.class).map(FoodStore.class);
        MongoClient mongoClient = new MongoClient();

        return morphia.createDatastore(mongoClient, name);
    }

}
