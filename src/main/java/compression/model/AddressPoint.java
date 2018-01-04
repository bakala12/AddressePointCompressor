package compression.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddressPoint {
    @Getter
    private String name;
    @Getter
    private Double latitude;
    @Getter
    private Double longitude;
}
