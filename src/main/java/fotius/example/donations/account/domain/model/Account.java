package fotius.example.donations.account.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fotius.example.donations.common.model.Currency;
import lombok.Data;

@Data
public class Account {

    private Long id;
    private String name;
    private String surname;
    private Status status;
    private Balance balance;
    private Currency currency;

    @JsonIgnore
    public boolean isClosed() {
        return status == Status.CLOSED;
    }

    public enum Status {
        OPEN, CLOSED
    }
}
