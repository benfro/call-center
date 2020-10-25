package net.benfro.callcenter.system;

import net.benfro.callcenter.domain.AbstractCallCenterStaff;
import net.benfro.callcenter.domain.AvailabilityState;
import net.benfro.callcenter.domain.Fresher;
import net.benfro.callcenter.domain.TelephoneCallFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BackendConnectionTest {

    private BackendConnection instance;

    private AbstractCallCenterStaff employee;

    @Before
    public void setUp() throws Exception {
        employee = new Fresher("sven");
        instance = BackendConnection.get(employee);
    }

    @Test
    public void state_is_available_by_default() throws Exception {
        assertThat(instance.getCurrentState(), is(AvailabilityState.State.AVAILABLE));
    }

    @Test
    public void employee_answer_call_transforms_state_to_occupied() throws Exception, IllegalStateTransferException {
        employee.answerCall(TelephoneCallFactory.INSTANCE.get(employee));
        assertThat(instance.getCurrentState(), is(AvailabilityState.State.OCCUPIED));
    }

    @Test
    public void employee_escalate_transforms_state_to_waiting() throws Exception, IllegalStateTransferException {
        employee.answerCall(TelephoneCallFactory.INSTANCE.get(employee));
        employee.escalate();
        assertThat(instance.getCurrentState(), is(AvailabilityState.State.WAITING_FOR_ANSWER));
    }

    @Test
    public void employee_hangup_transforms_state_to_available() throws Exception, IllegalStateTransferException {
        employee.answerCall(TelephoneCallFactory.INSTANCE.get(employee));
        employee.escalate();
        employee.hangUp();
        assertThat(instance.getCurrentState(), is(AvailabilityState.State.AVAILABLE));
    }
}