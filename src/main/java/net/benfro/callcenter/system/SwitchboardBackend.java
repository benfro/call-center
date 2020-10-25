package net.benfro.callcenter.system;


import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.domain.AbstractCallCenterStaff;
import net.benfro.callcenter.util.FIFOQueue;

import java.util.List;

@Slf4j
@Data
class SwitchboardBackend {

    private final FIFOQueue<BackendConnection> connected = new FIFOQueue<BackendConnection>(100000);
    private SwitchboardDispatcher dispatcher;

    public void connect(AbstractCallCenterStaff employee) {
        log.debug("Connecting {} to backend", employee);
        this.connected.enqueue(BackendConnection.get(employee));
    }

    public void disconnect(AbstractCallCenterStaff employee) {
        log.debug("Disconnecting {} from backend", employee);
        this.connected.remove(BackendConnection.get(employee));
    }

    public List<BackendConnection> getConnections() {
        return connected.getBackingCollection();
    }

    public ImmutableList<BackendConnection> getStaffByType(final Class<?> staffType) {
        return FluentIterable.from(connected.getBackingCollection())
                .filter(new Predicate<BackendConnection>() {
                    @Override
                    public boolean apply(BackendConnection backendConnection) {
                        return backendConnection.getEmployee().getClass().equals(staffType);
                    }
                }).toList();
    }
}
