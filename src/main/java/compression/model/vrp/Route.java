package compression.model.vrp;

import lombok.Getter;

import java.util.List;

public class Route extends SimpleRoute{

    public Route(Location from, Location to, double distance, double time, int hops, List<Instruction> instructions){
        super(from, to, distance, time, hops);
        this.instructions = instructions;
    }

    @Getter
    private List<Instruction> instructions;
}
