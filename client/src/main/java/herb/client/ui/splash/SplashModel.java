package herb.client.ui.splash;

import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import herb.client.ui.core.Model;
import herb.client.utils.Configuration;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;
import javafx.concurrent.Task;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class SplashModel extends Model {
    ServiceLocator serviceLocator;

    public SplashModel() {
        super();
    }

    // A task is a JavaFX class that implements Runnable. Tasks are designed to
    // have attached listeners, which we can use to monitor their progress.
    final Task<Void> initializer = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            this.updateProgress(1,  6);
            
            TimeUnit.MILLISECONDS.sleep(250);
            
            // Create the service locator to hold our resources
            serviceLocator = ServiceLocator.getInstance();
            this.updateProgress(2,  6);
            
            TimeUnit.MILLISECONDS.sleep(250);

            // Initialize the resources in the service locator
            serviceLocator.setLogger(configureLogging());
            this.updateProgress(3,  6);
            
            TimeUnit.MILLISECONDS.sleep(250);

            serviceLocator.setConfiguration(new Configuration());
            this.updateProgress(4,  6);
            
            TimeUnit.MILLISECONDS.sleep(250);

            String language = serviceLocator.getConfiguration().getOption("Language");
            serviceLocator.setTranslator(new Translator(language));
            this.updateProgress(5,  6);
            
            TimeUnit.MILLISECONDS.sleep(250);
            
            // ... more resources would go here...
            this.updateProgress(6,  6);
            
            return null;
        }
    };

    public void initialize() {
        new Thread(initializer).start();
    }

    /**
     * We create a logger with the name of the application, and attach a file
     * handler to it. All logging should be done using this logger. Messages to
     * this logger will also flow up to the root logger, and from there to the
     * console-handler.
     * 
     * We set the level of the console-handler to "INFO", so that the console
     * only receives the more important messages. The levels of the loggers and
     * the file-handler are set to "FINEST".
     */
    private Logger configureLogging() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.FINEST);

        // By default there is one handler: the console
        Handler[] defaultHandlers = Logger.getLogger("").getHandlers();
        defaultHandlers[0].setLevel(Level.INFO);

        // Add our logger
        Logger ourLogger = Logger.getLogger(serviceLocator.getAPP_NAME());
        ourLogger.setLevel(Level.FINEST);
        
        // Add a file handler, putting the rotating files in the tmp directory
        try {
            Handler logHandler = new FileHandler("%t/"
                    + serviceLocator.getAPP_NAME() + "_%u" + "_%g" + ".log",
                    1000000, 9);
            logHandler.setLevel(Level.FINEST);
            ourLogger.addHandler(logHandler);
        } catch (Exception e) { // If we are unable to create log files
            throw new RuntimeException("Unable to initialize log files: "
                    + e.toString());
        }

        return ourLogger;
    }
}
