package compression.model.jsprit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class SolutionRoute {
    @Getter
    private Long startId;
    @Getter
    private Long endId;
    @Getter
    private List<Long> clientIds;
}
