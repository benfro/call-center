package net.benfro.callcenter.system;

import lombok.Data;
import net.benfro.callcenter.domain.AbstractCallCenterStaff;
import net.benfro.callcenter.domain.Fresher;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Data
public class SwitchboardBackendTest {

    private SwitchboardBackend instance;

    @Before
    public void setUp() throws Exception {
        instance = new SwitchboardBackend();
    }

    @Test
    public void connected_is_added_to_list() throws Exception {
        AbstractCallCenterStaff fresher = new Fresher("perra");
        instance.connect(fresher);
        assertThat(instance.getConnected().size(), is(1));
    }

    @Test
    public void disconnect_removes_connected() throws Exception {
        AbstractCallCenterStaff fresher = new Fresher("brylle");
        instance.connect(fresher);
        instance.disconnect(fresher);
        assertThat(instance.getConnected().size(), is(0));
    }
}