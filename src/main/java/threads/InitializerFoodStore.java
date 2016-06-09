package threads;

import model.Category;
import model.FoodStore;
import model.Product;
import org.mongodb.morphia.Datastore;
import service.ProductService;
import util.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InitializerFoodStore implements Runnable {
    public static final Logger LOG = Logger.getLogger(InitializerFoodStore.class.getName());

    public void run() {
        DataSource.getInstance().dropDataSource("PROG_FORCE");

        FoodStore foodMarket = getFoodStore();

        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        ds.delete(ds.createQuery(FoodStore.class));

        List<Category> categories = foodMarket.getCategoryList();
        for (Category category : categories) {
            ds.save(category.getProductList());
        }
        ds.save(foodMarket.getCategoryList());
        ds.save(foodMarket);
        ds.ensureIndexes();

        LOG.info("Data Base filled successfully. Updating data...");
        ProductService.getInstance().changeStatusAbsent();
        ProductService.getInstance().changeStatusExpected();
        ProductService.getInstance().changePrice();

        Product TEST_PRODUCT_01 = new Product("TEST PRODUCT 01", 100000000, "Available");
        Product TEST_PRODUCT_02 = new Product("TEST PRODUCT 02", 15000000, "Expected");

        ds.save(TEST_PRODUCT_01);
        ds.save(TEST_PRODUCT_02);
        LOG.info("Data updated successfully: {PRICE, STATUS}");

        Product product = foodMarket.get("candy");
        LOG.info("FOUNDED PRODUCT: " + product);

        foodMarket.changePrice("candy", 120);
        foodMarket.changeStatus("candy", "Expected");
        foodMarket.add("sweet", new Product("Frooty bread", 999, "Expected"));
        LOG.info("Data updated successfully: {PRICE, STATUS}");
    }

    private FoodStore getFoodStore() {
        FoodStore foodStore = FoodStore.getInstance();
        foodStore.setName("Food Store");
        Category meat = new Category();
        Category bread = new Category();
        Category sweet = new Category();
        meat.setName("meat");
        bread.setName("bread");
        sweet.setName("sweet");

        List<Product> meatList = new ArrayList<Product>();
        List<Product> breadList = new ArrayList<Product>();
        List<Product> sweetList = new ArrayList<Product>();

        Product cowMeat = new Product("cowMeat", 10, "Available");
        Product rabbitMeat = new Product("rabbitMeat", 15, "Expected");
        Product chickenMeat = new Product("chikenMeat", 12, "Available");
        Product rowMeat = new Product("rowMeat", 15, "Absent");

        Product whiteBread = new Product("whiteBread", 3, "Available");
        Product darkBread = new Product("darkBread", 4, "Absent");
        Product sweetBread = new Product("sweetBread", 5, "Available");
        Product seedBread = new Product("seedBread", 7, "Available");

        Product candy = new Product("candy", 7, "Absent");
        Product chocolate = new Product("chocolate", 16, "Available");
        Product darkChocolate = new Product("darkChocolate", 17, "Available");
        Product sweetCandy = new Product("sweetCandy", 22, "Absent");
        Product originalCandy = new Product("originalCandy", 18, "Available");

        meatList.add(cowMeat);
        meatList.add(rabbitMeat);
        meatList.add(chickenMeat);
        meatList.add(rowMeat);
        breadList.add(whiteBread);
        breadList.add(darkBread);
        breadList.add(sweetBread);
        breadList.add(seedBread);
        sweetList.add(candy);
        sweetList.add(chocolate);
        sweetList.add(darkChocolate);
        sweetList.add(sweetCandy);
        sweetList.add(originalCandy);

        meat.setProductList(meatList);
        bread.setProductList(breadList);
        sweet.setProductList(sweetList);

        List<Category> foodCaterogries = new ArrayList<Category>();
        foodCaterogries.add(meat);
        foodCaterogries.add(bread);
        foodCaterogries.add(sweet);
        foodStore.setCategoryList(foodCaterogries);
        return foodStore;
    }
}
