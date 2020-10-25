package net.benfro.callcenter.domain;

import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.benfro.callcenter.system.IllegalStateTransferException;

@Slf4j
@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractCallCenterStaff extends AbstractPerson {

    public enum Role {
        FRESHER(Fresher.class),
        TECH_LEAD(TechLead.class),
        PRODUCT_MANAGER(ProductManager.class);

        private Class<?> type;

        Role(Class<?> type) {
            this.type = type;
        }

        public Class<?> getType() {
            return type;
        }
    }

    protected Role role;

    private TelephoneCall currentTelephoneCall;

    private final AvailabilityState state = new AvailabilityState();

    protected AbstractCallCenterStaff(String name, Role role) {
        super(name);
        this.role = role;
    }

    protected AbstractCallCenterStaff(Role role) {
        super("");
        this.role = role;
    }

    public void answerCall(TelephoneCall telephoneCall) throws IllegalStateTransferException {
        log.debug("{} answering call {}", this, telephoneCall);
        this.currentTelephoneCall = telephoneCall;
        this.getState().transferToOccupied();
        doNotify();
    }

    public void hangUp() throws IllegalStateTransferException {
        log.debug("Ending {}", currentTelephoneCall);
        this.currentTelephoneCall = null;
        this.getState().transferToAvailable();
    }

    public void escalate() throws IllegalStateTransferException {
        log.debug("Escalating {}", currentTelephoneCall);
        this.getState().transferToWaiting();
    }

    public AvailabilityState.State getCurrentState() {
        return state.getCurrentState();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", getName())
                .add("role", role)
                .add("state", getCurrentState().name())
                .toString();
    }
}
