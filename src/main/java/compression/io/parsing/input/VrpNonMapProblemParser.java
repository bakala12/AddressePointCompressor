package compression.io.parsing.input;

import compression.io.parsing.ParsingException;
import compression.model.vrp.VrpProblem;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
        while((line = reader.readLine()) != null){
            ParseResult<String> nameRes = parseName(line);
            ParseResult<Double> optRes = parseOptimalSolution(line);
            if(nameRes.hasValue){
                name = nameRes.data;
            }
            if(optRes.hasValue){
                bestSolution = optRes.data;
            }

        }
        return null;
    }

    private class ParseResult<TData>{
        @Getter
        private TData data;
        @Getter
        private boolean hasValue;

        public ParseResult(){
            hasValue = false;
        }

        public ParseResult(TData data){
            hasValue = true;
            this.data = data;
        }
    }

    private ParseResult<String> parseName(String line){
        if(line.startsWith("NAME")){
            String name = line.split(":")[1];
            return new ParseResult(name);
        }
        return new ParseResult();
    }

    private ParseResult<Double> parseOptimalSolution(String line){
        if(line.startsWith("COMMENT")){
            String[] split = line.split(",");
            String optimalStr = split[2].replace(")", "");
            String bestSol = optimalStr.split(":")[1];
            return new ParseResult<Double>(Double.parseDouble(bestSol));
        }
        return new ParseResult<>();
    }


}
