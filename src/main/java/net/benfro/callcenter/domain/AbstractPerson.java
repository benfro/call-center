package net.benfro.callcenter.domain;


import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.benfro.callcenter.util.Monitor;

import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
public abstract class AbstractPerson implements Runnable {

    private final UUID id = UUID.randomUUID();

    private String name;

    public AbstractPerson(String name) {
        this.name = name;
    }

    private Object monitorHook = new Object();
    private Monitor monitor = new Monitor(monitorHook);

    public AbstractPerson() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("monitorHook", monitorHook)
                .add("monitor", monitor)
                .toString();
    }

    public void doWait() {
        monitor.doWait();
    }

    public void doNotify() {
        monitor.doNotify();
    }
}
