package compression.input;

public interface IProblemReader<TVrpProblem> {
    TVrpProblem readProblemInstanceFromResources(String resourceName);
    TVrpProblem readProblemInstanceFromFile(String path);
}
