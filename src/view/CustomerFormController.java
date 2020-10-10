package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    @FXML
    public Label currentUserLabel;
    @FXML
    private BorderPane mainUIBorderPane;
    @FXML
    private VBox sideMenuVBox;
    @FXML
    private Button customerViewMenuButton;
    @FXML
    private Button appointmentViewMenuButton;
    @FXML
    private Button reportViewMenuButton;
    @FXML
    private Button logoutMenuButton;
    @FXML
    private Button menuControlButton;
    @FXML
    private GridPane customerFormGridPane;

    //Store the primary stage of the application
    public Stage primaryStage;

    //Store the current user
    public String userName = "";

    //Create confirmation alert
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * @param stage the stage to set
     */
    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEventListeners();
        currentUserLabel.setText(userName);
    }


    /**Create event handlers for UI events*/
    private void setEventListeners(){

        //Return the user to the login screen
        logoutMenuButton.setOnAction(actionEvent -> {
            //Confirm the user wants to logout
            confirmationAlert.setContentText("Are you sure you want to logout of the application?");
            confirmationAlert.showAndWait().ifPresent(response ->{
                if(response.equals(ButtonType.OK)){
                    //Get resources from the fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LoginFormView.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Get the login form controller and pass the stage to it
                    LoginFormController controller = loader.getController();
                    controller.setPrimaryStage(primaryStage);

                    //Create the scene and apply stylesheet
                    assert root != null;
                    Scene loginForm = new Scene(root, 600, 250);
                    URL stylesheetURL = getClass().getResource("../resources/AppointmentApp.css");
                    loginForm.getStylesheets().add(stylesheetURL.toExternalForm());

                    //Set the stage properties and display
                    primaryStage.setTitle("Software 2 Scheduling Application");
                    primaryStage.setScene(loginForm);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }
            });
        });

        //Show or Hide menu when menuControlButton is clicked
        menuControlButton.setOnAction(actionEvent -> {
            if (mainUIBorderPane.getLeft() != null){
                mainUIBorderPane.setLeft(null);
                menuControlButton.setText("Show Menu");
            } else {
                mainUIBorderPane.setLeft(sideMenuVBox);
                menuControlButton.setText("Hide Menu");
            }
        });

    }

}
