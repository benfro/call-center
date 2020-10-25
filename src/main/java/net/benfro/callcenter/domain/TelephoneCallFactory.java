package net.benfro.callcenter.domain;


import net.benfro.callcenter.util.PropertyReader;
import net.benfro.callcenter.util.RandomGenerator;

public enum TelephoneCallFactory {

    INSTANCE;

    public TelephoneCall get(AbstractPerson person) {
        if (person instanceof Customer) {
            return getInternal(person, "customer.callLengthSecs.min", "customer.callLengthSecs.max");
        } else if (person instanceof Fresher) {
            return getInternal(person, "fresher.callLengthSecs.min", "fresher.callLengthSecs.max");
        } else if (person instanceof TechLead) {
            return getInternal(person, "techlead.callLengthSecs.min", "techlead.callLengthSecs.max");
        } else {
            return getInternal(person, "prodmanager.callLengthSecs.min", "prodmanager.callLengthSecs.max");
        }
    }

    private TelephoneCall getInternal(AbstractPerson person, String minProperty, String maxProperty) {
        int max = PropertyReader.INSTANCE.getIntProperty(maxProperty);
        int min = PropertyReader.INSTANCE.getIntProperty(minProperty);

        int randomLength = RandomGenerator.intInRange(min, max);

        return new TelephoneCall(randomLength, person);
    }
}
