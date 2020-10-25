package net.benfro.callcenter.system;

import net.benfro.callcenter.domain.Customer;
import net.benfro.callcenter.domain.CustomerFactory;
import net.benfro.callcenter.domain.TelephoneCall;
import net.benfro.callcenter.domain.TelephoneCallFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SwitchboardFrontTest {

    private SwitchboardFront instance;

    @Before
    public void setUp() throws Exception {
        instance = new SwitchboardFront();
    }

    @Test
    public void incoming_call_added_to_queue() throws Exception {
        instance.recieveAndEnqueue(TelephoneCallFactory.INSTANCE.get(new Customer("test")));

        assertThat(instance.getIncomingQueue().size(), is(1));
    }

    @Test
    public void connect_customer_and_recieve_phone_call() throws Exception {
        Customer customer = CustomerFactory.INSTANCE.get("arne");
        instance.connect(customer);
    }

    @Test
    public void take() throws Exception {
        Customer customer = CustomerFactory.INSTANCE.get("arne");
        TelephoneCall telephoneCall = TelephoneCallFactory.INSTANCE.get(customer);
        instance.getIncomingQueue().enqueue(telephoneCall);
        assertThat(instance.getIncomingQueue().size(), is(1));
        instance.takeFromQueue();
        assertThat(instance.getIncomingQueue().size(), is(0));
    }
}