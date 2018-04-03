package compression.services.jsprit.extensions;

import com.graphhopper.jsprit.core.algorithm.state.StateManager;
import com.graphhopper.jsprit.core.algorithm.state.StateUpdater;
import com.graphhopper.jsprit.core.problem.Capacity;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.ActivityVisitor;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;

public class MyShipmentStateUpdater implements StateUpdater, ActivityVisitor {

    private final StateManager stateManager;
    private Capacity currentCapacity;
    private Capacity maxCapacity;

    public MyShipmentStateUpdater(StateManager stateManager){
        this.stateManager = stateManager;
    }

    @Override
    public void begin(VehicleRoute vehicleRoute) {

    }

    @Override
    public void visit(TourActivity tourActivity) {

    }

    @Override
    public void finish() {

    }
}
