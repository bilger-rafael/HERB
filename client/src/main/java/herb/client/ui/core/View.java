package herb.client.ui.core;

import herb.client.utils.ServiceLocator;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public abstract class View<M extends Model> {
    protected Stage stage;
    protected Scene scene;
    protected M model;
    
    /**
     * Set any options for the stage in the subclass constructor
     * 
     * @param stage
     * @param model
     */
    protected View(Stage stage, M model) {
        this.stage = stage;
        this.model = model;
        stage.setTitle("Chat Bilger-Etter");
        scene = create_GUI(); // Create all controls within "root"
        scene.getStylesheets().addAll(ServiceLocator.getInstance().getAPP_CLASS().getResource("Main.css").toExternalForm());
        stage.setScene(scene);
    }

    protected abstract Scene create_GUI();

    /**
     * Display the view
     */
    public void start() {
        stage.show();
    }
    
    /**
     * Hide the view
     */
    public void stop() {
        stage.hide();
    }
    
    /**
     * Getter for the stage, so that the controller can access window events
     */
    public Stage getStage() {
        return stage;
    }
}
