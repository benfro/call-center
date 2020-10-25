package net.benfro.callcenter.system;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.domain.AbstractCallCenterStaff;
import net.benfro.callcenter.util.FIFOQueue;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<BackendConnection> getStaffByType(final Class<?> staffType) {
        return connected.getBackingCollection().stream().filter(backendConnection -> backendConnection.getEmployee().getClass().equals(staffType)).collect(Collectors.toList());
    }
}
