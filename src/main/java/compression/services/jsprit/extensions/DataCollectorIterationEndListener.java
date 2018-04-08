package compression.services.jsprit.extensions;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.listener.AlgorithmEndsListener;
import com.graphhopper.jsprit.core.algorithm.listener.AlgorithmStartsListener;
import com.graphhopper.jsprit.core.algorithm.listener.IterationEndsListener;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.core.util.StopWatch;
import compression.model.vrp.VrpProblem;
import compression.output.datalogger.IDataLogger;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class DataCollectorIterationEndListener implements IterationEndsListener, AlgorithmStartsListener, AlgorithmEndsListener {

    private final VrpProblem problem;
    private final IDataLogger logger;
    private StopWatch stopWatch = new StopWatch();

    @Override
    public void informIterationEnds(int i, VehicleRoutingProblem vehicleRoutingProblem, Collection<VehicleRoutingProblemSolution> collection) {
        VehicleRoutingProblemSolution best = Solutions.bestOf(collection);
        Double time = stopWatch.getCurrTimeInSeconds();
        logger.saveData(problem.getProblemName(), i, time, best.getCost(), best.getRoutes().size(), problem.getBestKnownSolution());
    }

    @Override
    public void informAlgorithmEnds(VehicleRoutingProblem vehicleRoutingProblem, Collection<VehicleRoutingProblemSolution> collection) {
        logger.closeLogger();
        stopWatch.stop();
    }

    @Override
    public void informAlgorithmStarts(VehicleRoutingProblem vehicleRoutingProblem, VehicleRoutingAlgorithm vehicleRoutingAlgorithm, Collection<VehicleRoutingProblemSolution> collection) {
        logger.openLogger();
        stopWatch.reset();
        stopWatch.start();
    }
}
