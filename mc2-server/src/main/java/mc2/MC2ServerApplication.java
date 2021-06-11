package mc2;

import mc2.domain.Sample;
import mc2.domain.SampleRepository;
import org.cincheo.dlite.Application;
import org.cincheo.dlite.extension.logging.Logging;
import org.cincheo.dlite.extension.logging.LoggingExtension;
import org.cincheo.dlite.extension.logging.model.ConsoleHandlerModel;
import org.cincheo.dlite.extension.model.ModelExtension;
import org.cincheo.dlite.extension.persistence.PersistenceExtension;
import org.cincheo.dlite.extension.ui.UserInterfaceExtension;

import java.util.logging.Level;

/**
 * A server application that stores the MC2 samples within a persistent repository.
 */
public class MC2ServerApplication extends Application {

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

        new PersistenceExtension(this) {
            {
                configureConnexion("jdbc:mysql://localhost/mc2", "dlite", "dlite");

//                registerDriver("org.sqlite.JDBC");
//                configureConnexion("jdbc:sqlite:" + getCurrentDirectory() + "/mc2.db");
                fieldScope(SampleRepository.class, "samples")
                        .repositoryFor(Sample.class);

                executableScope(SampleRepository.class, "clearSamples()").updateOrDelete(arguments ->
                        getController().deleteInstances(getClassItem(Sample.class))
                );

            }
        };

        new ModelExtension(this) {
            {
            }
        };

        new UserInterfaceExtension(this) {
            {
                startHttpServer("localhost", 8090, true);
            }
        };

    }

    @Override
    public void run() {
        Logging logging = getExtension(LoggingExtension.class).logging;
        SampleRepository sampleRepository = new SampleRepository();

        logging.info("MC2 server started");

    }

}
