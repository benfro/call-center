package net.benfro.callcenter.system;


import net.benfro.callcenter.domain.AbstractCallCenterStaff;

public class SwitchboardEvent {

    private final AbstractCallCenterStaff person;

    public SwitchboardEvent(AbstractCallCenterStaff person) {
        this.person = person;
    }

    public static class AnswerEvent extends SwitchboardEvent {
        public AnswerEvent(AbstractCallCenterStaff person) {
            super(person);
        }
    }

    public static class HangUpEvent extends SwitchboardEvent {
        public HangUpEvent(AbstractCallCenterStaff person) {
            super(person);
        }
    }

    public static class EscalateEvent extends SwitchboardEvent {
        public EscalateEvent(AbstractCallCenterStaff person) {
            super(person);
        }
    }
}
