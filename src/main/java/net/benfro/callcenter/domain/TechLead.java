package net.benfro.callcenter.domain;

public class TechLead extends AbstractCallCenterStaff {

    private TechLead(String name, Role role) {
        super(name, role);
    }

    public TechLead(String name) {
        this(name, Role.TECH_LEAD);
    }

    public TechLead() {
        this("");
    }

    @Override
    public void run() {

    }
}
