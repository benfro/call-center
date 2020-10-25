package net.benfro.callcenter.domain;

import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class TelephoneCall {

    private UUID id;
    private int durationInSeconds;
    private AbstractPerson caller;

    public TelephoneCall(int durationInSeconds, AbstractPerson caller) {
        this.durationInSeconds = durationInSeconds;
        this.caller = caller;
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id.toString().substring(id.toString().length() - 5))
                .add("durationInSeconds", durationInSeconds)
                .add("caller.name", caller.getName())
                .toString();
    }
}
