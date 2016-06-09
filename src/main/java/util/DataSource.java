package util;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.logging.Logger;


public class DataSource {
    public static final Logger LOG = Logger.getLogger(DataSource.class.getName());
    private static DataSource ourInstance = new DataSource();

    public static DataSource getInstance() {
        return ourInstance;
    }

    private DataSource() {
    }

    public Datastore getDataSource(String name) {
        LOG.info("Connecting to database...");
        Morphia morphia = new Morphia();
        morphia.mapPackage("src.main.java.model");
        MongoClient mongoClient = new MongoClient();

        return morphia.createDatastore(mongoClient, name);
    }

    public void dropDataSource(String name) {
        LOG.info("Drop data Base...");
        new MongoClient().dropDatabase(name);
    }

}
