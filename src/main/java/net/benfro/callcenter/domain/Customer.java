package net.benfro.callcenter.domain;

import com.google.common.eventbus.EventBus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.system.CallCenter;
import net.benfro.callcenter.util.RandomGenerator;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends AbstractPerson {


    private EventBus telephoneLine;

    private boolean inBackendQueue = true;

    public Customer(String name) {
        super(name);
    }


    public TelephoneCall makeCall() {
        return TelephoneCallFactory.INSTANCE.get(this);
    }

    public void getAnswer(TelephoneCall telephoneCall) {
        if (telephoneCall.getCaller().equals(this)) {
            doNotify();
        }
    }

    @Override
    public void run() {

        log.debug("Running Customer {}", this);
        setTelephoneLine(CallCenter.incomingTelephoneLine());
        telephoneLine.register(this);

        do {
            // Pause between inquiries to call center
            try {
                int waitBetweenCalls = RandomGenerator.intInRange(10, 30);
                log.info("Customer {} waiting for {} seconds between calls", this, waitBetweenCalls);
                Thread.sleep((long) waitBetweenCalls * 1000);
            } catch (InterruptedException e) {
                log.warn("Customer {} was interupted during pause", getId());
            }

            // Make the call
            TelephoneCall telephoneCall = makeCall();
            log.debug("Customer {} making call {} to call center", this, telephoneCall);
            telephoneLine.post(telephoneCall);
            // Wait in queue
            doWait();
            log.debug("Customer {} got answer on call {}", this, telephoneCall);

        } while (true);

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
