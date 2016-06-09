package model;

public interface Store {
    public Product get(String title);
    public void add(String categoryTarget, Product product);
    public void changePrice(String title,int price);
    public void changeStatus(String title, String status);
}
