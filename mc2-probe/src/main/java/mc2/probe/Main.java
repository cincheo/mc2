package mc2.probe;

import org.cincheo.dlite.DLite;

public class Main {

    public static void main(String[] args) {
        int exitCode = DLite.run(MC2ProbeApplication.class, args);
        System.exit(exitCode);
    }

}
