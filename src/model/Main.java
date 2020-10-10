package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoginFormController;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Get resources from the fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LoginFormView.fxml"));
        Parent root = loader.load();

        //Get the login form controller and pass the stage to it
        LoginFormController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);


        //Create the scene and apply stylesheet
        Scene loginForm = new Scene(root, 600, 250);
        URL stylesheetURL = getClass().getResource("../resources/AppointmentApp.css");
        loginForm.getStylesheets().add(stylesheetURL.toExternalForm());

        //Set the stage properties and display
        primaryStage.setTitle("Software 2 Scheduling Application");
        primaryStage.setScene(loginForm);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
