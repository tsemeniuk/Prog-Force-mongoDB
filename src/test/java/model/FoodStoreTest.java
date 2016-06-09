package model;

import org.junit.Assert;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import util.DataSource;

import java.util.List;

/**
 * Created by Tim on 09.06.2016.
 */
public class FoodStoreTest {

    @org.junit.Test
    public void testChangeStatus() throws Exception {
        String title = "candy";
        String status = "Available";
        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        /*FoodStore store = ds.find(FoodStore.class).get();
        for (Category category : store.getCategoryList()) {
            for (Product product : category.getProductList()) {
                if (title.equals(product.getTitle())) {
                    product.setStatus(status);
                }
            }
        }
        ds.save(store);*/


        Query q = ds.createQuery(Product.class).filter("status", "Available");
        List productList = q.asList();

        System.out.println(productList.size());
        Assert.assertNotNull(productList);

        /*dataSource = DataSource.getInstance();
        MongoDatabase db = dataSource.getDb("PROG_FORCE");
        MongoCollection<Document> store = db.getCollection("store");
        store.updateOne(eq("title", title), set("status", status));*/
    }
}