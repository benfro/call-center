package net.benfro.callcenter.domain;

public class ProductManager extends AbstractCallCenterStaff {

    private ProductManager(String name, Role role) {
        super(name, role);
    }

    public ProductManager(String name) {
        this(name, Role.PRODUCT_MANAGER);
    }

    public ProductManager() {
        this("");
    }

    @Override
    public void run() {

    }
}
