package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Vehicle {
    @Getter
    private Long id;
    @Getter
    private Double capacity;

    @Override
    public String toString(){
        return "ID: "+id+" CAPACITY: "+capacity;
    }
}
