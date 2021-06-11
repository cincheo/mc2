package mc2.probe;

import mc2.domain.SampleRepository;
import mc2.probe.service.SampleService;
import org.cincheo.dlite.Application;
import org.cincheo.dlite.extension.logging.Logging;
import org.cincheo.dlite.extension.logging.LoggingExtension;
import org.cincheo.dlite.extension.logging.model.ConsoleHandlerModel;
import org.cincheo.dlite.extension.model.ModelExtension;
import org.cincheo.dlite.extension.ui.UserInterfaceExtension;

import java.util.logging.Level;

/**
 * An application that runs a probe on the current host.
 * <p>
 * The probe can be started with {@link SampleService#start()} and will notify Web Socket clients for new samples.
 */
public class MC2ProbeApplication extends Application {

    @Override
    public void initialize(String[] arguments) {
        new LoggingExtension(this) {
            {

                logger("simple")
                        .setLevel(Level.ALL)
                        .add(new ConsoleHandlerModel()
                                .setFormat("[%1$tF %1$tT] [%4$-4s] %5$s %n")
                        );

                setDefaultLoggerName("simple");
            }
        };

        new UserInterfaceExtension(this) {
            {
                setRootDirectory("mc2-client");
                setDefaultName("mc2");
                startHttpServer("localhost", 8091, true);
                executableScope(SampleRepository.class, "addSample(*)").notifyClient("addSample");
            }
        };

    }

    @Override
    public void run() {
        Logging logging = getExtension(LoggingExtension.class).logging;
        SampleRepository sampleRepository = new SampleRepository();
        logging.info("MC2 probe started");
    }

}
