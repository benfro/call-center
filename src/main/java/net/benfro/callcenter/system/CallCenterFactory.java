package net.benfro.callcenter.system;


import net.benfro.callcenter.domain.*;
import net.benfro.callcenter.util.PropertyReader;

public enum CallCenterFactory {

    INSTANCE;

    public CallCenter get() {
        int numberOfFreshers = PropertyReader.INSTANCE.getIntProperty("numberOfFreshers");
        int numberOfTechLeads = PropertyReader.INSTANCE.getIntProperty("numberOfTechLeads");
        int numberOfProductManagers = PropertyReader.INSTANCE.getIntProperty("numberOfProductManagers");

        SwitchboardBackend switchboardBackend = new SwitchboardBackend();
        for (int i = 0; i < numberOfFreshers; i++) {
            AbstractCallCenterStaff fresher = PersonFactory.INSTANCE.getCallCenterStaffPerson(Fresher.class, "Fresher_" + i);
            switchboardBackend.connect(fresher);
        }
        for (int i = 0; i < numberOfTechLeads; i++) {
            AbstractCallCenterStaff tl = PersonFactory.INSTANCE.getCallCenterStaffPerson(TechLead.class, "TechLead_" + i);
            switchboardBackend.connect(tl);
        }
        for (int i = 0; i < numberOfProductManagers; i++) {
            AbstractCallCenterStaff pm = PersonFactory.INSTANCE.getCallCenterStaffPerson(ProductManager.class, "ProductManager_" + i);
            switchboardBackend.connect(pm);
        }
        return new CallCenter(new SwitchboardFront(), switchboardBackend);
    }

}
