package net.benfro.callcenter.util;

/**
 * Simple thread notification utility class
 */
public class Monitor {

    final Object myMonitorObject;
    boolean wasSignalled = false;

    public Monitor(Object myMonitorObject) {
        this.myMonitorObject = myMonitorObject;
    }

    public void doWait() {
        synchronized (myMonitorObject) {
            if (!wasSignalled) {
                try {
                    myMonitorObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //clear signal and continue running.
            wasSignalled = false;
        }
    }

    public void doNotify() {
        synchronized (myMonitorObject) {
            wasSignalled = true;
            myMonitorObject.notify();
        }
    }
}
