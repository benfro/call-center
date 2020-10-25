package net.benfro.callcenter.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class TelephoneCallFactoryTest {

    @Test
    public void get_call_for_customer() throws Exception {
        TelephoneCall telephoneCall = TelephoneCallFactory.INSTANCE.get(new Customer("joppa"));

        assertThat(telephoneCall, is(not(nullValue())));
        assertThat(telephoneCall.getDurationInSeconds() > 0, is(true));
    }

    @Test
    public void get_call_for_fresher() throws Exception {
        TelephoneCall telephoneCall = TelephoneCallFactory.INSTANCE.get(new Fresher("mille"));

        assertThat(telephoneCall, is(not(nullValue())));
        assertThat(telephoneCall.getDurationInSeconds() > 0, is(true));
    }

    @Test
    public void get_call_for_techlead() throws Exception {
        TelephoneCall telephoneCall = TelephoneCallFactory.INSTANCE.get(new TechLead("knegarn"));

        assertThat(telephoneCall, is(not(nullValue())));
        assertThat(telephoneCall.getDurationInSeconds() > 0, is(true));
    }

    @Test
    public void get_call_for_prodmanager() throws Exception {
        TelephoneCall telephoneCall = TelephoneCallFactory.INSTANCE.get(new ProductManager("lasse"));

        assertThat(telephoneCall, is(not(nullValue())));
        assertThat(telephoneCall.getDurationInSeconds() > 0, is(true));
    }
}