package herb.client.ui.splash;

import herb.client.Main;
import herb.client.ui.base.Controller;
import javafx.concurrent.Worker;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class SplashController extends Controller<SplashModel, SplashView> {

    public SplashController(SplashModel model, SplashView view) {
        super(model, view);
        
        // We could monitor the progress property and pass it on to the progress bar
        // However, JavaFX can also do this for us: We just bind the progressProperty of the
        // progress bar to the progressProperty of the task.
        view.progress.progressProperty().bind(model.initializer.progressProperty());
        
        // Using a lambda expression
        model.initializer.stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED)
                    	Main.getMainProgram().startMain();
                });
    }
}
