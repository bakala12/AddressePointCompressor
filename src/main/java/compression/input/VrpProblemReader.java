package compression.input;

import compression.model.vrp.VrpProblem;
import compression.input.parsing.IParser;
import lombok.RequiredArgsConstructor;

import java.io.FileInputStream;
import java.io.InputStream;

@RequiredArgsConstructor
public class VrpProblemReader<TVrpProblem> implements IProblemReader<TVrpProblem>{

    private final IParser<TVrpProblem> parser;

    /**
     * Reads VRP problem from resource file.
     * @param resourceName Name of resource.
     * @return VRP problem.
     */
    @Override
    public TVrpProblem readProblemInstanceFromResources(String resourceName) {
        try(InputStream stream = VrpProblem.class.getResourceAsStream(resourceName)){
            return parser.parse(stream);
        }catch (Exception ex) {
            throw new LoadFileException(ex.getMessage(), ex);
        }
    }

    /**
     * Reads VRP problem instance from a file.
     * @param path File path.
     * @return VRP problem.
     */
    @Override
    public TVrpProblem readProblemInstanceFromFile(String path){
        try(InputStream stream = new FileInputStream(path)) {
            return parser.parse(stream);
        } catch (Exception ex) {
            throw new LoadFileException(ex.getMessage(), ex);
        }
    }
}
