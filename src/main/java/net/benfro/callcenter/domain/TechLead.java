package net.benfro.callcenter.domain;

public class TechLead extends AbstractCallCenterStaff {

    public TechLead(String name) {
        super(name, Role.TECH_LEAD);
    }

    public TechLead() {
        this("");
    }

    @Override
    public void run() {

    }
}
