package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class VrpProblem {
    @Getter
    private List<Client> clients;
    @Getter
    private List<Vehicle> vehicles;
}
