package net.benfro.callcenter.simulation;


import net.benfro.callcenter.domain.Customer;
import net.benfro.callcenter.domain.PersonFactory;
import net.benfro.callcenter.domain.TelephoneCallFactory;
import net.benfro.callcenter.system.CallCenter;
import net.benfro.callcenter.system.CallCenterFactory;
import net.benfro.callcenter.util.PropertyReader;

public class Main {

    public static void main(String[] args) {

        CallCenter callCenter = CallCenterFactory.INSTANCE.get();

        //test_if_front_is_emptied();

        int numberOfCustomer = PropertyReader.INSTANCE.getIntProperty("numberOfCustomers");

        for (int i = 0; i < numberOfCustomer; i++) {
            Customer customer = PersonFactory.INSTANCE.getCustomer("customer_" + i);
            Thread customerThread = new Thread(customer);
            customerThread.start();
        }

        callCenter.start();
    }

    private static void test_if_front_is_emptied() {
        CallCenter callCenter = CallCenterFactory.INSTANCE.get();

        callCenter.getFront().recieveAndEnqueue(TelephoneCallFactory.INSTANCE.get(PersonFactory.INSTANCE.getCustomer("a")));
        callCenter.getFront().recieveAndEnqueue(TelephoneCallFactory.INSTANCE.get(PersonFactory.INSTANCE.getCustomer("b")));
        callCenter.getFront().recieveAndEnqueue(TelephoneCallFactory.INSTANCE.get(PersonFactory.INSTANCE.getCustomer("c")));

        callCenter.start();

        int idx = 0;
        while (callCenter.getFront().getIncomingQueue().size() > 0) {

        }
        System.out.println("/nYAY!");
    }

}
