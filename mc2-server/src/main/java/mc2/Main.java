package mc2;

import org.cincheo.dlite.DLite;

public class Main {

    public static void main(String[] args) {
        int exitCode = DLite.run(MC2ServerApplication.class, args);
        System.exit(exitCode);
    }

}
