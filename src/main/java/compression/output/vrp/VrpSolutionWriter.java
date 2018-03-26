package compression.output.vrp;

import compression.model.jsprit.SolutionRoute;
import compression.model.jsprit.VrpSolution;
import compression.model.vrp.VrpProblem;

import java.util.Collection;
import java.util.List;

public class VrpSolutionWriter {
    public void writeSolution(VrpProblem problem, Collection<VrpSolution> solutions){
        System.out.println("SOLUTIONS of problem: "+problem.getProblemName());
        System.out.println("BEST KNOWN SOLUTION: "+problem.getBestKnownSolution());
        Integer num = 1;
        for(VrpSolution sol : solutions){
            System.out.println("SOLUTION " + num + " COST: "+ sol.getCost());
            num++;
            System.out.println();
        }
    }

    public void writeRoutes(List<SolutionRoute> solutionRoutes){
        int routeNum = 1;
        for(SolutionRoute route : solutionRoutes){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("ROUTE"+routeNum+": ");
            stringBuffer.append(route.getStartId()+" -> ");
            for(Long cl : route.getClientIds()){
                stringBuffer.append(cl);
                stringBuffer.append(" -> ");
            }
            stringBuffer.append(route.getEndId());
            System.out.println(stringBuffer.toString());
            routeNum++;
        }
    }
}
