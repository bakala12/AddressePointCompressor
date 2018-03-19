package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Depot {
    @Getter
    private long id;
    @Getter
    private Location loaction;
}
