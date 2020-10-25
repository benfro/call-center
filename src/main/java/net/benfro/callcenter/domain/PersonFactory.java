package net.benfro.callcenter.domain;

import java.lang.reflect.InvocationTargetException;

public enum PersonFactory {

    INSTANCE;

    public AbstractCallCenterStaff getCallCenterStaffPerson(Class<? extends AbstractCallCenterStaff> clazz, String name) {
        try {
            return clazz.getDeclaredConstructor(String.class).newInstance(name);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Boom!");
    }

    public Customer getCustomer(String name) {
        return new Customer(name);
    }
}
