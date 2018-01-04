package compression;

import compression.io.AddressFileReader;

public class Program {
    public static void main(String[] args){
        CompressionApplication app = new CompressionApplication(new AddressFileReader());
        app.run();
    }
}
