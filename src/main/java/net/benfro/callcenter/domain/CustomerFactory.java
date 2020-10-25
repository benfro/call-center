package net.benfro.callcenter.domain;

public enum CustomerFactory {

    INSTANCE;

    public Customer get(String name) {
        return new Customer(name);
    }
}
