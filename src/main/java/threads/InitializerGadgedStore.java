package threads;

import model.Category;
import model.GadgetStore;
import model.Product;
import org.mongodb.morphia.Datastore;
import util.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InitializerGadgedStore implements Runnable {
    public static final Logger LOG = Logger.getLogger(InitializerGadgedStore.class.getName());

    public void run() {
        GadgetStore gadgetMarket = getGadgetStore();

        Datastore ds = DataSource.getInstance().getDataSource("PROG_FORCE");
        ds.delete(ds.createQuery(GadgetStore.class));
        ds.save(gadgetMarket);
        ds.ensureIndexes();

        LOG.info("=====================Data Base filled successfully========================");
        LOG.info("=============================Updating data...=============================");

        List<Category> categoryList = gadgetMarket.getCategoryList();
        Category categoryToAbsent = categoryList.get(0);
        List<Product> productList = categoryToAbsent.getProductList();
        for (Product product : productList) {
            product.setStatus("Absent");
        }
        for (int i = 1; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            List<Product> products = category.getProductList();
            for (int y = 0; y < products.size() / 2; y++) {
                Product product = products.get(y);
                product.setStatus("Expected");
            }
            for (int j = 0; j < products.size(); j++) {
                Product product = products.get(j);
                if (product.getStatus().equals("Available")) {
                    product.setPrice(product.getPrice() * 100 / 80);
                }
            }
        }
        ds.save(gadgetMarket);
        LOG.info("====================Data updated successfully===========================");


        Product product = gadgetMarket.get("benq display");
        LOG.info("====================FOUNDED PRODUCT: " + product);

        gadgetMarket.changePrice("sony display", 1200);
        gadgetMarket.changeStatus("simCard", "Expected");
        gadgetMarket.add("mobile", new Product("Galaxy smartPhone", 1999, "Expected"));
        LOG.info("==================Status/price updated successfully=====================");
    }

    private GadgetStore getGadgetStore() {
        GadgetStore gadgetMarket = GadgetStore.getInstance();
        gadgetMarket.setName("GadgetMarket");
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
