package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.InputValidator;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML
    public TextField userIDTextField;
    @FXML
    public PasswordField userPasswordField;
    @FXML
    public Label userLocationLabel;
    @FXML
    public Label inputErrorLabel;
    @FXML
    public Button loginButton;
    @FXML
    public Button cancelButton;

    //Store the primary stage of the application
    Stage primaryStage;

    //Display confirmation to exit application
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * @param stage the stage to set
     */
    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserInfo();
        setEventHandlers();
    }

    /**Get the users location and language and set the language of the application*/
    private void getUserInfo(){

        //Get the locale of the user machine
        Locale currentLocale = Locale.getDefault();

        //Set the Location label
        userLocationLabel.setText("Location: " + currentLocale.getCountry());

        //Get the language of the user machine
        String userLanguage = currentLocale.getLanguage();

    }

    /**Attempt to login when the login button is pressed
     * @throws IOException if resource cannot be located
     */
    private void attemptLogin() throws IOException {

        //Check if input is valid
        if (InputValidator.validateLoginForm(this)){
            //store username
            String username = userIDTextField.getText();

            //Open the Customer form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CustomerFormView.fxml"));
            Parent root = loader.load();

            //Get the customer form controller and pass the stage to it
            CustomerFormController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.currentUserLabel.setText(username);

            Scene customerForm = new Scene(root, 1000, 700);
            URL stylesheet = getClass().getResource("../resources/AppointmentApp.css");
            customerForm.getStylesheets().add(stylesheet.toExternalForm());

            //Set the scene and show
            primaryStage.setScene(customerForm);
            primaryStage.show();
        }

    }

    /**
     * @param userID the userID to record who attempted to login
     * @param loginIsValid the result of the login to record if it was successful
     */
    public void recordLoginAttempt(String userID, boolean loginIsValid){

        //Get the current time and date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        String currentDate = formatter.format(new Date(System.currentTimeMillis()));

        //Create string to append
        String newEntry = "Username: " + userID + "\t Time Attempted: " + currentDate + "\t Login Successful: " + loginIsValid + "\n";

        //Create file writer and append string
        try {
            FileWriter writer = new FileWriter("login_activity.txt", true);
            writer.append(newEntry);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**Set the event handlers for the LoginFormView*/
    private void setEventHandlers(){

        //Attempt to login when the login button is pressed
        loginButton.setOnAction(actionEvent -> {
            try {
                attemptLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Close the application when the cancel button is pressed
        cancelButton.setOnAction(actionEvent -> {
            //Confirm the user wants to close the application
            confirmationAlert.setContentText("Are you sure you want to exit the application?");
            confirmationAlert.showAndWait().ifPresent(response ->{
                if (response.equals(ButtonType.OK)){
                    primaryStage.close();
                }
            });

        });

        //Attempt to login when enter is pressed while focused on the password field
        userPasswordField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                try {
                    attemptLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
