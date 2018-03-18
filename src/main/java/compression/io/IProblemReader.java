package compression.io;

public interface IProblemReader<TVrpProblem> {
    TVrpProblem readProblemInstanceFromResources(String resourceName);
    TVrpProblem readProblemInstanceFromFile(String path);
}
