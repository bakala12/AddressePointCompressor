package compression.services.jsprit;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Job;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import compression.model.jsprit.SolutionRoute;
import compression.model.jsprit.VrpSolution;
import compression.model.vrp.Client;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import compression.services.jsprit.conversion.EuclideanMetricVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.ExplicitMetricVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.IVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.ProblemConversionException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JSpritService implements IJSpritService {
    private static class JSpritConvertersFactory{
        public static IVrpProblemToJSpritConverter getConverter(VrpProblem problem) {
            switch (problem.getProblemMetric()){
                case Euclidean:
                    return new EuclideanMetricVrpProblemToJSpritConverter();
                case Explicit:
                    return new ExplicitMetricVrpProblemToJSpritConverter();
                case GraphHopper:
                    throw new NotImplementedException();
                default:
                    throw new ProblemConversionException("Unsupported or unknown problem metrics");
            }
        }
    }

    @Override
    public Collection<VrpSolution> solve(VrpProblem problem){
        IVrpProblemToJSpritConverter converter = JSpritConvertersFactory.getConverter(problem);
        VehicleRoutingProblem vrp = converter.convertToJsprit(problem);

        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(vrp);
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        List<VrpSolution> solutionToReturn = new LinkedList<>();
        for(VehicleRoutingProblemSolution sol: solutions){
            List<SolutionRoute> solutionRoutes = new LinkedList<>();
            for(VehicleRoute route : sol.getRoutes()){
                List<Long> clientIds = new LinkedList<>();
                for(Job job : route.getTourActivities().getJobs()){
                    clientIds.add(Long.valueOf(job.getId()));
                }
                Long start = Long.valueOf(route.getStart().getLocation().getId());
                Long end = Long.valueOf(route.getEnd().getLocation().getId());
                solutionRoutes.add(new SolutionRoute(start, end, clientIds));
            }
            solutionToReturn.add(new VrpSolution(sol.getCost(), solutionRoutes));
        }
        return solutionToReturn;
    }
}
