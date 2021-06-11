package mc2.probe.service;

import mc2.domain.Sample;
import mc2.domain.SampleRepository;
import org.cincheo.dlite.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SampleService {

    private static SampleThread sampleThread;

    private void initSampleThread() {

        if (sampleThread != null) {
            return;
        }

        sampleThread = new SampleThread();
        sampleThread.start();

    }

    /**
     * Starts the sample acquisition thread.
     */
    synchronized public void start() {
        SampleRepository.getInstance().clearSamples();
        initSampleThread();
    }

    /**
     * Stops the sample acquisition thread.
     */
    synchronized public void stop() {
        sampleThread.destroy();
        sampleThread = null;
    }


}

class SampleThread extends Thread {

    private Process sampleProcess;

    public void destroy() {
        this.sampleProcess.destroy();
    }

    @Override
    public void run() {
        try {
            ProcessBuilder ps = new ProcessBuilder("top");

            ps.redirectErrorStream(true);

            sampleProcess = ps.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(sampleProcess.getInputStream()));
            String line;
            Sample sample = null;

            while ((line = in.readLine()) != null) {
                if (line.matches("^CPU.*")) {
                    if (sample != null) {
                        SampleRepository.getInstance().addSample(sample);
                    }
                    sample = new Sample();
                    System.out.println(line);
                    String[] cpus = line.split(",");
                    sample.setCpuUser(Float.parseFloat(extract(cpus[0], "(1?[0-9]?[0-9]\\.[0-9][0-9]?)%")));
                    sample.setCpuSystem(Float.parseFloat(extract(cpus[1], "(1?[0-9]?[0-9]\\.[0-9][0-9]?)%")));
                    sample.setCpuIdle(Float.parseFloat(extract(cpus[2], "(1?[0-9]?[0-9]\\.[0-9][0-9]?)%")));
                    System.out.println(" ===> " + sample);
                }
                if (line.matches("^Phys.*")) {
                    System.out.println(line);
                    line = line.split(",")[1];
                    line = line.replace("K", "000");
                    line = line.replace("M", "000000");
                    line = line.replace("G", "000000000");
                    line = extract(line, "([0-9]+)");
                    sample.setAvailableMemory(Long.parseLong(line));
                }
                if (line.matches("^Disks:.*")) {
                    System.out.println(line);
                    String sin = line.split(",")[0];
                    String sout = line.split(",")[1];
                    sample.setDiskRead(Long.parseLong(extract(sin, "([0-9]+)/")));
                    sample.setDiskWritten(Long.parseLong(extract(sout, "([0-9]+)/")));
                }
                if (line.matches("^Networks:.*")) {
                    System.out.println(line);
                    String sin = line.split(",")[0];
                    String sout = line.split(",")[1];
                    sample.setNetworkIn(Long.parseLong(extract(sin, "([0-9]+)/")));
                    sample.setNetworkOut(Long.parseLong(extract(sout, "([0-9]+)/")));
                }
            }
            if (sample != null) {
                SampleRepository.getInstance().addSample(sample);
            }
            sampleProcess.waitFor();
            System.out.println("ok!");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static String extract(String s, String regexp) {
        System.out.println("extract " + s + " - " + regexp);
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            System.out.println(" => " + matcher.group(1));
            return matcher.group(1);
        } else {
            System.out.println("not found");
            return null;
        }
    }

}
