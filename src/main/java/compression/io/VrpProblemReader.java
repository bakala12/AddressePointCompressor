package compression.io;

import compression.model.vrp.VrpProblem;
import compression.io.parsing.IParser;
import lombok.RequiredArgsConstructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class VrpProblemReader<TVrpProblem> implements IProblemReader<TVrpProblem>{

    private final IParser<TVrpProblem> parser;

    @Override
    public TVrpProblem readProblemInstanceFromResources(String resourceName) {
        try(InputStream stream = VrpProblem.class.getResourceAsStream(resourceName)){
            return parser.parse(stream);
        }catch (Exception ex) {
            throw new LoadFileException(ex.getMessage(), ex);
        }
    }

    @Override
    public TVrpProblem readProblemInstanceFromFile(String path){
        try(InputStream stream = new FileInputStream(path)) {
            return parser.parse(stream);
        } catch (Exception ex) {
            throw new LoadFileException(ex.getMessage(), ex);
        }
    }
}
