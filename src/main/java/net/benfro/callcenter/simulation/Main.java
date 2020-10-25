package net.benfro.callcenter.simulation;


import net.benfro.callcenter.domain.Customer;
import net.benfro.callcenter.domain.PersonFactory;
import net.benfro.callcenter.system.CallCenter;
import net.benfro.callcenter.system.CallCenterFactory;
import net.benfro.callcenter.util.PropertyReader;

public class Main {

    public static void main(String[] args) {

        CallCenter callCenter = CallCenterFactory.INSTANCE.get();

        int numberOfCustomer = PropertyReader.INSTANCE.getIntProperty("numberOfCustomers");

        for (int i = 0; i < numberOfCustomer; i++) {
            Customer customer = PersonFactory.INSTANCE.getCustomer("customer_" + i);
            Thread customerThread = new Thread(customer);
            customerThread.start();
        }

        callCenter.start();
    }

}
