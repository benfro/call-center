package net.benfro.callcenter.domain;

public class ProductManager extends AbstractCallCenterStaff {

    public ProductManager(String name) {
        super(name, Role.PRODUCT_MANAGER);
    }

    public ProductManager() {
        this("");
    }

    @Override
    public void run() {

    }
}
