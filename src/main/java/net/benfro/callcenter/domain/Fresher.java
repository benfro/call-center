package net.benfro.callcenter.domain;

import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.system.IllegalStateTransferException;

@Slf4j
public class Fresher extends AbstractCallCenterStaff {

    public Fresher(String name) {
        super(name, Role.FRESHER);
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
