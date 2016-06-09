package threads;

import model.Category;
import model.GadgetStore;
import model.Product;
import org.mongodb.morphia.Datastore;
import service.ProductService;
import util.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InitializerGadgetStore implements Runnable {
    public static final Logger LOG = Logger.getLogger(InitializerGadgetStore.class.getName());

    public void run() {
        GadgetStore gadgetMarket = getGadgetStore();

        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        ds.delete(ds.createQuery(GadgetStore.class));

        List<Category> categories = gadgetMarket.getCategoryList();
        for (Category category : categories) {
            ds.save(category.getProductList());
        }
        ds.save(gadgetMarket.getCategoryList());
        ds.save(gadgetMarket);
        ds.ensureIndexes();

        LOG.info("Data Base filled successfully. Updating data...");
        ProductService.getInstance().changeStatusAbsent();
        ProductService.getInstance().changeStatusExpected();
        ProductService.getInstance().changePrice();

        Product TEST_PRODUCT_03 = new Product("TEST PRODUCT 03", 100000000, "Available");
        Product TEST_PRODUCT_04 = new Product("TEST PRODUCT 04", 15000000, "Expected");

        ds.save(TEST_PRODUCT_03);
        ds.save(TEST_PRODUCT_04);
        LOG.info("Data updated successfully: {PRICE, STATUS}");

        Product product = gadgetMarket.get("benq display");
        LOG.info("FOUNDED PRODUCT: " + product);

        gadgetMarket.changePrice("sony display", 1200);
        gadgetMarket.changeStatus("simCard", "Expected");
        gadgetMarket.add("mobile", new Product("Galaxy smartPhone", 1999, "Expected"));
        LOG.info("Data updated successfully: {PRICE, STATUS}");
    }

    private GadgetStore getGadgetStore() {
        GadgetStore gadgetMarket = GadgetStore.getInstance();
        gadgetMarket.setName("Gadget Store");
        Category mobile = new Category();
        Category monitor = new Category();
        Category tools = new Category();
        mobile.setName("mobile");
        monitor.setName("monitor");
        tools.setName("tools");

        List<Product> mobileList = new ArrayList<Product>();
        List<Product> monitorList = new ArrayList<Product>();
        List<Product> toolsList = new ArrayList<Product>();

        Product simCard = new Product("simCard", 20, "Expected");
        Product mobileBattery = new Product("mobileBattery", 32, "Available");
        Product mobileDisplay = new Product("mobileDisplay", 125, "Available");
        Product lg = new Product("lg display", 800, "Available");
        Product benq = new Product("benq display", 950, "Absent");
        Product sony = new Product("sony display", 1150, "Expected");
        Product pen = new Product("pen", 2, "Expected");
        Product pencil = new Product("pencil", 3, "Available");
        Product gummy = new Product("gummy", 4, "Available");

        mobileList.add(simCard);
        mobileList.add(mobileBattery);
        mobileList.add(mobileDisplay);
        monitorList.add(lg);
        monitorList.add(benq);
        monitorList.add(sony);
        toolsList.add(pen);
        toolsList.add(pencil);
        toolsList.add(gummy);

        mobile.setProductList(mobileList);
        monitor.setProductList(monitorList);
        tools.setProductList(toolsList);

        List<Category> gadgetCategories = new ArrayList<Category>();
        gadgetCategories.add(mobile);
        gadgetCategories.add(monitor);
        gadgetCategories.add(tools);
        gadgetMarket.setCategoryList(gadgetCategories);
        return gadgetMarket;
    }
}
