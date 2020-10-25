package net.benfro.callcenter.system;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.domain.*;
import net.benfro.callcenter.util.Monitor;

import java.util.Optional;

@Slf4j
@Getter
class SwitchboardDispatcher implements Runnable {

    private SwitchboardFront front;
    private SwitchboardBackend backend;
    private boolean running = true;
    private Object monitorHook = new Object();
    private Monitor monitor = new Monitor(monitorHook);

    public void doWait() {
        monitor.doWait();
    }

    public void doNotify() {
        monitor.doNotify();
    }

    public SwitchboardDispatcher(SwitchboardFront front, SwitchboardBackend backend) {
        this.front = front;
        this.backend = backend;
    }

    public void start() {
        log.debug("Starting dispatcher");
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {

        // Start the staff threads
        for (BackendConnection connection : backend.getConnections()) {
            log.debug("Starting connection {}", connection);
            connection.start();
        }

        // Poll incoming telephone call queue for new waiting calls
        while (running) {
            doWait();
            Optional<TelephoneCall> peekTelephone = front.getIncomingQueue().peek();
            for (BackendConnection connection : backend.getStaffByType(getCallHandler(peekTelephone.orElse(null)))) {

                if (connection.isAvailable()) {
                    TelephoneCall telephoneCall = front.takeFromQueue();
                    if (telephoneCall != null) {
                        try {
                            connection.getEmployee().answerCall(telephoneCall);
                            // Notify waiting caller that call has been answered
                            telephoneCall.getCaller().doNotify();
                        } catch (IllegalStateTransferException e) {
                            log.error("Illegal transition", e);
                        }
                    } else {
                        log.warn("Ooops! Telephone call was null");
                    }
                }

            }
        }

    }


    public Class<?> getCallHandler(TelephoneCall telephoneCall) {
        if (telephoneCall.getCaller() instanceof Customer) {
            return Fresher.class;
        }
        if (telephoneCall.getCaller() instanceof Fresher) {
            return TechLead.class;
        }
        if (telephoneCall.getCaller() instanceof TechLead) {
            return ProductManager.class;
        }
        throw new RuntimeException("Puff!");
    }
}
