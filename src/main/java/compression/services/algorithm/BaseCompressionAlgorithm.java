package compression.services.algorithm;

import compression.model.vrp.Location;
import compression.model.vrp.VrpProblem;

public abstract class BaseCompressionAlgorithm {
    protected void prepareProblemGraph(VrpProblem problem){

    }

    protected void findMinimalSpanningTree(){

    }

    protected abstract double getDistanceBetween(Location from, Location to);
}
