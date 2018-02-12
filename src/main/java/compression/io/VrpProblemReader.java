package compression.io;

import compression.model.vrp.VrpProblem;
import compression.parsing.IParser;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
public class VrpProblemReader implements IProblemReader{

    private final IParser<VrpProblem> parser;

    @Override
    public VrpProblem readProblemInstance(String resourceName) {
        try(InputStream stream = VrpProblem.class.getResourceAsStream(resourceName)){
            return parser.parse(stream);
        }catch (Exception ex) {
            throw new LoadFileException(ex.getMessage(), ex);
        }
    }
}
