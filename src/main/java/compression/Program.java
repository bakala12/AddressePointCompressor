package compression;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Program {
    public static Boolean useDefault(){
        try{
            Properties p = new Properties();
            InputStream stream = Program.class.getResourceAsStream("/Config.config");
            p.load(stream);
            String def = p.getProperty("USEDEFAULT");
            int r = def.compareTo("true");
            return r == 0;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args){
        CompressionApplication app = new CompressionApplication();
        //default arguments to debug
//        if(true){
//            app.run("./Benchmarks/mybenchmark.vrp", "./solutions/solution.sol", true,
//                    "./solutions/data/data.csv", "./solutions/plots/");
//            return;
//        }
        System.out.println("Start");
        Options options = new Options();
        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);
        Option output = new Option("o", "output", true, "output file");
        output.setRequired(true);
        options.addOption(output);
        Option useCompression = new Option("c", "compression", false, "use compression algorithm");
        useCompression.setRequired(false);
        options.addOption(useCompression);
        Option collectedDataPath = new Option("d", "datapath", true, "path to file with collected data");
        collectedDataPath.setRequired(true);
        options.addOption(collectedDataPath);
        Option plotDir = new Option("p", "plotpath", true, "path to directory where plots will be generated");
        plotDir.setRequired(true);
        options.addOption(plotDir);

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
        if(cmd.hasOption("d")){
            dataPath = cmd.getOptionValue("d");
        }
        if(cmd.hasOption("p")){
            plotPath = cmd.getOptionValue("p");
        }
        app.run(inputFilePath, outputFilePath, useCompressionValue, dataPath, plotPath);
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
