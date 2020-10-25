package net.benfro.callcenter.system;

import com.google.common.eventbus.EventBus;
import lombok.Getter;
import net.benfro.callcenter.domain.TelephoneCall;

@Getter
public class CallCenter {

    private final SwitchboardFront front;
    private final SwitchboardBackend backend;
    private final SwitchboardDispatcher dispatcher;
    public static EventBus INBOUND_TELEPHONE_LINE;

    public CallCenter(SwitchboardFront front, SwitchboardBackend backend) {
        this.front = front;
        this.backend = backend;
        this.dispatcher = new SwitchboardDispatcher(front, backend);
        this.front.setDispatcher(dispatcher);
        this.backend.setDispatcher(dispatcher);
        INBOUND_TELEPHONE_LINE = front.getIncomingTelephoneLine();
    }

    public static EventBus incomingTelephoneLine() {
        return INBOUND_TELEPHONE_LINE;
    }

    public void start() {
        new Thread(dispatcher).start();
        front.startPoller();
    }

    public void recieve(TelephoneCall telephoneCall) {
        front.recieveAndEnqueue(telephoneCall);
    }

}
