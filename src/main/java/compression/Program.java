package compression;

import compression.model.jsprit.DecompressionMethod;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Program {

    public static void main(String[] args){
        CompressionApplication app = new CompressionApplication();
        //default arguments to debug
        if(true){
                app.run("./AnotherBenchmarks/tarjanexample.vrp", "./decsolution1.sol", "./decsolution1.result", true,
                        "./decdata1.csv", "./Results/Plots", 2000, "./decsolution1.route", DecompressionMethod.GREEDY, 4712L,5,"./decinfo.info");
            return;
        }
        System.out.println("Start");
        Options options = new Options();
        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);
        Option output = new Option("o", "output", true, "output file");
        output.setRequired(true);
        options.addOption(output);
        Option result = new Option("r", "result", true, "result file");
        result.setRequired(true);
        options.addOption(result);
        Option useCompression = new Option("c", "compression", false, "use compression algorithm");
        useCompression.setRequired(false);
        options.addOption(useCompression);
        Option collectedDataPath = new Option("d", "datapath", true, "path to file with collected data");
        collectedDataPath.setRequired(true);
        options.addOption(collectedDataPath);
        Option plotDir = new Option("p", "plotpath", true, "path to directory where plots will be generated");
        plotDir.setRequired(true);
        options.addOption(plotDir);
        Option iterations = new Option("iter", "iterations", true, "number of JSprit iterations");
        iterations.setRequired(false);
        options.addOption(iterations);
        Option solutionRoute = new Option("s", "solutionRoute", true, "path to file with solution route");
        solutionRoute.setRequired(false);
        options.addOption(solutionRoute);
        Option decompressionMethod = new Option("dc", "decompression", true, "decompression method: simple (default) or greedy");
        decompressionMethod.setRequired(false);
        options.addOption(decompressionMethod);
        Option numberOfRuns = new Option("n", "numberofruns", true, "number of runs for the given benchmark");
        numberOfRuns.setRequired(false);
        options.addOption(numberOfRuns);
        Option generalInfoOption = new Option("g", "generalinfo", true, "path to file in which general information about solutions will be stored");
        generalInfoOption.setRequired(false);
        options.addOption(generalInfoOption);
        Option randomSeedOption = new Option("rand", "randomSeed", true, "random seed");
        randomSeedOption.setRequired(false);
        options.addOption(randomSeedOption);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");
        Boolean useCompressionValue = cmd.hasOption("c");
        String dataPath = null;
        String plotPath = null;
        Integer iterNum = 2000;
        String resultFilePath = cmd.getOptionValue("r");
        String solutionRoutePath = cmd.getOptionValue("s");
        DecompressionMethod decompression = DecompressionMethod.SIMPLE;
        Integer numberOfRunsValue = 1;
        if(cmd.hasOption("d")){
            dataPath = cmd.getOptionValue("d");
        }
        if(cmd.hasOption("p")){
            plotPath = cmd.getOptionValue("p");
        }
        if(cmd.hasOption("iter")){
            String iterNumStr = cmd.getOptionValue("iter");
            try{
                iterNum = Integer.parseInt(iterNumStr);
                if(iterNum < 0)
                    throw new Exception();
            } catch (Exception ex){
                System.out.println("Invalid iterations parameter");
                iterNum = 2000;
            }
        }
        if(cmd.hasOption("dc")){
            String dc = cmd.getOptionValue("dc");
            if(dc.equals("greedy")) {
                decompression = DecompressionMethod.GREEDY;
            }
        }
        if(cmd.hasOption("n")){
            String n = cmd.getOptionValue("n");
            try{
                numberOfRunsValue = Integer.parseInt(n);
                if(numberOfRunsValue < 0)
                    throw new Exception();
            } catch (Exception ex){
                System.out.println("Invalid numberofruns parameter");
                numberOfRunsValue = 1;
            }
        }
        Long seed = 4711L;
        if(cmd.hasOption("rand")){
            String seedStr = cmd.getOptionValue("rand");
            try{
                seed = Long.parseLong(seedStr);
                if(seed < 0)
                    throw new Exception();
            } catch (Exception ex){
                System.out.println("Invalid randomSeed parameter");
                seed = 4711L;
            }
        }
        String generalInfoPath = null;
        if(cmd.hasOption("g")){
            generalInfoPath = cmd.getOptionValue("g");
        }
        if(numberOfRunsValue > 1){
            app.run(inputFilePath, outputFilePath, resultFilePath, useCompressionValue, dataPath, plotPath, iterNum, solutionRoutePath, decompression, seed, numberOfRunsValue, generalInfoPath);
        } else{
            app.run(inputFilePath, outputFilePath, resultFilePath, useCompressionValue, dataPath, plotPath, iterNum, solutionRoutePath, decompression, seed);
        }
    }
}
