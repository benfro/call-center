package net.benfro.callcenter.domain;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import net.benfro.callcenter.system.IllegalStateTransferException;

@Getter
public class AvailabilityState {

    public enum State {
        AVAILABLE, OCCUPIED, WAITING_FOR_ANSWER;
    }

    private State currentState = State.AVAILABLE;

    public void transferToOccupied() throws IllegalStateTransferException {
        if (currentState.equals(State.AVAILABLE)) {
            currentState = State.OCCUPIED;
        } else {
            throw new IllegalStateTransferException();
        }
    }

    public void transferToWaiting() throws IllegalStateTransferException {
        if (currentState.equals(State.OCCUPIED)) {
            currentState = State.WAITING_FOR_ANSWER;
        } else {
            throw new IllegalStateTransferException();
        }
    }

    public void transferToAvailable() throws IllegalStateTransferException {
        if (currentState.equals(State.WAITING_FOR_ANSWER) || currentState.equals(State.OCCUPIED)) {
            currentState = State.AVAILABLE;
        } else {
            throw new IllegalStateTransferException();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("state", currentState.name())
                .toString();
    }
}
