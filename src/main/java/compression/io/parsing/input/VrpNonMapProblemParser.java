package compression.io.parsing.input;

import compression.io.parsing.ParsingException;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VrpNonMapProblemParser implements IVrpProblemParser{

    @Override
    public VrpProblem parse(InputStream stream) {
        try(InputStreamReader reader = new InputStreamReader(stream)) {
            try(BufferedReader bufferedReader = new BufferedReader(reader)){
                return parseReader(bufferedReader);
            }
        } catch (IOException e) {
            throw new ParsingException(e.getMessage(), e);
        }
    }

    private VrpProblem parseReader(BufferedReader reader) throws IOException {
        String line;
        String name = null;
        Double bestSolution = 0.0;
        Double capacity = 0.0;
        Integer dimensions = 0;
        VrpProblemMetric metric = VrpProblemMetric.Unknown;
        while((line = reader.readLine()) != null){
            if(line.startsWith("NAME")){
                name = parseName(line);
            }
            else if(line.startsWith("COMMENT")){
                bestSolution = parseBestKnownSolution(line);
            }
            else if(line.startsWith("DIMENSION")){
                dimensions = parseDimensions(line);
            }
            else if(line.startsWith("CAPACITY")){
                capacity = parseCapacity(line);
            }
            else if(line.startsWith("EDGE_WEIGHT_TYPE")){
                metric = parseMetrics(line);
            }
            else if(line.startsWith("EDGE_WEIGHT_SECTION")){
                if(metric != VrpProblemMetric.Explicit)
                    throw new ParsingException("EDGE_WEIGHT_SECTION is supported only when EDGE_WEIGHT_TYPE is set to EXPLICIT");

            }
            else if(line.startsWith("NODE_COORD_SECTION")){

            }
            else if(line.startsWith("DEMAND_SECTION")){

            }
            else if(line.startsWith("DEPOT_SECTION")){

            }
        }
        return null;
    }

    private String parseName(String line){
        return line.split(" : ")[1];
    }

    private Double parseBestKnownSolution(String line){
        String[] split = line.split(":");
        String bestKnown = split[split.length-1].replace(")", "");
        return Double.parseDouble(bestKnown);
    }

    private Double parseCapacity(String line){
        return Double.parseDouble(line.split(" : ")[1]);
    }

    private Integer parseDimensions(String line){
        return Integer.parseInt(line.split(" : ")[1]);
    }

    private VrpProblemMetric parseMetrics(String line){
        String metric = line.split(" : ")[1];
        if(metric.startsWith("EXPLICIT")){
            return VrpProblemMetric.Explicit;
        }
        if(metric.startsWith("EUC2D")){
            return VrpProblemMetric.Euclidean;
        }
        throw new ParsingException("Not supported problem metrics");
    }
}
