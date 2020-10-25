package net.benfro.callcenter.system;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.domain.Customer;
import net.benfro.callcenter.domain.TelephoneCall;
import net.benfro.callcenter.util.FIFOQueue;

import java.util.Optional;

@Slf4j
@Data
public class SwitchboardFront {

    private final FIFOQueue<TelephoneCall> incomingQueue = new FIFOQueue<>(1000000);
    private final EventBus incomingTelephoneLine = new EventBus("Incoming telephone line");
    private SwitchboardDispatcher dispatcher;
    QueuePoller<TelephoneCall> queuePoller = new QueuePoller<TelephoneCall>(incomingQueue);

    public SwitchboardFront() {
        incomingTelephoneLine.register(this);
    }

    public void connect(Customer customer) {
        log.info("Customer {} connected to frontend", customer);
        customer.setTelephoneLine(incomingTelephoneLine);
    }

    @Subscribe
    public void recieveAndEnqueue(TelephoneCall telephoneCall) {
        log.info("Recieved telephone call {}, queueing it", telephoneCall.getId());
        incomingQueue.enqueue(telephoneCall);
    }

    public TelephoneCall takeFromQueue() {
        log.debug("size of incoming call queue before take call: {}", incomingQueue.size());
        Optional<TelephoneCall> dequeue = incomingQueue.dequeue();
        log.debug("size of incoming call queue after take call: {}", incomingQueue.size());
        if (dequeue.isPresent()) {
            return dequeue.get();
        } else {
            return null;
        }
    }

    public void startPoller() {
        new Thread(queuePoller).start();
    }

    private class QueuePoller<T> implements Runnable {

        private FIFOQueue<T> queue;

        private QueuePoller(FIFOQueue<T> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            Preconditions.checkState(dispatcher != null, "Dispatcher must be initialised");

            while (true) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (queue.size() > 0) {
                    dispatcher.doNotify();
                }
            }

        }
    }


}
