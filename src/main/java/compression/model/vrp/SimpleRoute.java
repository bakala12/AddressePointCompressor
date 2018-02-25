package compression.model.vrp;

import lombok.Getter;

public class SimpleRoute extends Distance{

    public SimpleRoute(Location from, Location to, double distance, double time, int hops){
        super(from, to, distance);
        this.time = time;
        this.hops = hops;
    }

    @Getter
    private double time;

    @Getter
    private int hops;
}
