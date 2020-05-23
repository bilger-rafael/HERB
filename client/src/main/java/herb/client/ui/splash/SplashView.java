package herb.client.ui.splash;

import herb.client.ui.core.View;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Pos;


/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class SplashView extends View<SplashModel> {
    ProgressBar progress;

    public SplashView(Stage stage, SplashModel model) {
        super(stage, model);
        stage.initStyle(StageStyle.TRANSPARENT); // also undecorated
    }

    @Override
    protected Scene create_GUI() {
        BorderPane root = new BorderPane();
        root.setId("splash");
        
        Label hello = new Label("HERB-Jass - 2020");
        VBox topBox = new VBox( );
        topBox.getChildren().add(hello);
        topBox.setAlignment(Pos.TOP_CENTER);
        VBox centerBox = new VBox( );
        centerBox.setId("center");
        root.setCenter(centerBox);
        root.setTop(topBox);

        progress = new ProgressBar();
        progress.setPrefWidth(200);
        HBox bottomBox = new HBox();
        bottomBox.setId("progressbox");
        bottomBox.getChildren().add(progress);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 600, 350);
        
        return scene;
    }
}
