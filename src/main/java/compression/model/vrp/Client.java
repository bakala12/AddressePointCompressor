package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Client {
    @Getter
    private long id;
    @Getter
    public double amount;
    @Getter
    public double time;
    @Getter
    private Location loaction;
}
