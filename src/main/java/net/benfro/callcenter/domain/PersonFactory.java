package net.benfro.callcenter.domain;

public enum PersonFactory {

    INSTANCE;

    public AbstractCallCenterStaff getCallCenterStaffPerson(Class<? extends AbstractCallCenterStaff> clazz, String name) {
        try {
            AbstractCallCenterStaff instance = clazz.newInstance();
            instance.setName(name);
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Boom!");
    }

    public Customer getCustomer(String name) {
        return new Customer(name);
    }
}
