package net.benfro.callcenter.system;

import net.benfro.callcenter.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SwitchboardDispatcherTest {

    private SwitchboardDispatcher instance;

    @Before
    public void setUp() throws Exception {
        instance = new SwitchboardDispatcher(null, null);
    }

    @Test
    public void get_call_handler_returns_Fresher_when_Customer_calls() throws Exception {

        TelephoneCall telephoneCall = TelephoneCallFactory.INSTANCE.get(new Customer("lars"));

        assertThat(instance.getCallHandler(telephoneCall), is(Fresher.class.getClass()));
    }

    @Test
    public void get_call_handler_returns_Techlead_when_Fresher_calls() throws Exception {

        TelephoneCall telephoneCall = TelephoneCallFactory.INSTANCE.get(new Fresher("lars"));

        assertThat(instance.getCallHandler(telephoneCall), is(TechLead.class.getClass()));
    }

    @Test
    public void get_call_handler_returns_ProdManager_when_Techlead_calls() throws Exception {

        TelephoneCall telephoneCall = TelephoneCallFactory.INSTANCE.get(new TechLead("lars"));

        assertThat(instance.getCallHandler(telephoneCall), is(ProductManager.class.getClass()));
    }

}