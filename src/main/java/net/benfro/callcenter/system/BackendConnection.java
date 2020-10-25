package net.benfro.callcenter.system;

import com.google.common.eventbus.Subscribe;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.domain.*;
import net.benfro.callcenter.util.FIFOQueue;
import net.benfro.callcenter.util.PropertyReader;

@Slf4j
@Data
@EqualsAndHashCode(of = "employee")
public class BackendConnection {

    public static BackendConnection get(AbstractCallCenterStaff staff) {
        int maxQueueSize = 0;
        if (staff instanceof Fresher) {
            maxQueueSize = PropertyReader.INSTANCE.getIntProperty("fresher.queueSize");
        } else if (staff instanceof TechLead) {
            maxQueueSize = PropertyReader.INSTANCE.getIntProperty("techlead.queueSize");
        } else {
            maxQueueSize = PropertyReader.INSTANCE.getIntProperty("prodmanager.queueSize");
        }
        return new BackendConnection(maxQueueSize, staff);
    }

    private final AbstractCallCenterStaff employee;
    private final FIFOQueue<TelephoneCall> employeeQueue;
    private Thread thread;

    private BackendConnection(int maxQueueLength, AbstractCallCenterStaff employee) {
        this.employee = employee;
        this.employeeQueue = new FIFOQueue<TelephoneCall>(maxQueueLength);
    }

    public AvailabilityState.State getCurrentState() {
        return employee.getState().getCurrentState();
    }

    public void start() {
        log.debug("Starting backend connection {}", this);
        thread = new Thread(employee);
        thread.start();
    }

    public boolean isAvailable() {
        return getCurrentState().isAvailable();
    }

    @Subscribe
    public void receieveAnswerEvent(SwitchboardEvent.AnswerEvent event) throws IllegalStateTransferException {
        employee.getState().transferToOccupied();
    }

    @Subscribe
    public void receieveHangUpEvent(SwitchboardEvent.HangUpEvent event) throws IllegalStateTransferException {
        employee.getState().transferToAvailable();
    }

    @Subscribe
    public void receieveEscalateEvent(SwitchboardEvent.EscalateEvent event) throws IllegalStateTransferException {
        employee.getState().transferToWaiting();
    }


}
