package net.benfro.callcenter.domain;

import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.system.IllegalStateTransferException;

@Slf4j
public class Fresher extends AbstractCallCenterStaff {

    private Fresher(String name, Role role) {
        super(name, role);
    }

    public Fresher(String name) {
        this(name, Role.FRESHER);
    }

    public Fresher() {
        this("");
    }

    @Override
    public void run() {

        while (true) {

            log.debug("{} waiting for call", this);
            doWait();

            try {
                log.debug("{} got call {}, chatting for {} seconds", this, getCurrentTelephoneCall(), getCurrentTelephoneCall().getDurationInSeconds());
                Thread.sleep((long) getCurrentTelephoneCall().getDurationInSeconds() * 1000);
            } catch (InterruptedException e) {
                log.error("Problems chatting", e);
            }

            try {
                log.debug("{} hanging up");
                hangUp();
            } catch (IllegalStateTransferException e) {
                log.error("Problems ending phone call", e);
            }
        }

    }
}
