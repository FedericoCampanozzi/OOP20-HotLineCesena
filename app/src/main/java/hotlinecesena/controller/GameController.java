package hotlinecesena.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameController extends Application {
    @Override
    public void start(Stage stage) {
        stage.setOnCloseRequest(
                event -> System.out.println("Close Requested")
        );

        Button handleClose = new Button("Handle Close Request");
        handleClose.setOnAction(
                event -> stage.getOnCloseRequest()
                        .handle(
                                new WindowEvent(
                                        stage,
                                        WindowEvent.WINDOW_CLOSE_REQUEST
                                )
                        )
        );
        handleClose.setMaxWidth(Double.MAX_VALUE);

        Button fireClose = new Button("Fire Close Request");
        fireClose.setOnAction(
                event -> stage.fireEvent(
                        new WindowEvent(
                                stage,
                                WindowEvent.WINDOW_CLOSE_REQUEST
                        )
                )
        );
        fireClose.setMaxWidth(Double.MAX_VALUE);

        stage.setScene(
                new Scene(
                        new VBox(
                                10,
                                handleClose,
                                fireClose    
                        )
                )
        );
        stage.show();
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Object getGreeting() {
        // TODO Auto-generated method stub
        return null;
    }
}