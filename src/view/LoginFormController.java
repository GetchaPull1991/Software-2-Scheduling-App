package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Database;
import model.InputValidator;
import model.User;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** Class to manage all Login Form UI Functionality*/
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
    @FXML
    public Label usernameLabel;
    @FXML
    public Label passwordLabel;

    //Store the primary stage of the application
    Stage primaryStage;

    //Display confirmation to exit application
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    //Resource Bundle for Language Resources
    public ResourceBundle resourceBundle;

    /**
     * Set the primary stage of the application
     * @param stage the stage to set
     */
    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserLanguage();
        getUserLocation();
        setEventHandlers();
    }

    /** Set the application language according to users locale*/
    public void setUserLanguage(){

        //Get the resource for the users Locale
        resourceBundle = ResourceBundle.getBundle("resources.LoginForm", Locale.getDefault());

        //Set display text to resource values
        usernameLabel.setText(resourceBundle.getString("usernameFieldLabel"));
        passwordLabel.setText(resourceBundle.getString("passwordFieldLabel"));
        loginButton.setText(resourceBundle.getString("loginButtonLabel"));
        cancelButton.setText(resourceBundle.getString("cancelButtonLabel"));
    }

    /** Get the users location from the locale and display in a form label*/
    private void getUserLocation(){

        //Get the locale of the user machine
        Locale currentLocale = Locale.getDefault();

        //Set the Location label
        userLocationLabel.setText(resourceBundle.getString("locationLabel") + "\n" + currentLocale.getDisplayCountry());

    }

    /**Attempt to login when the login button pressed
     * @throws IOException if resource cannot be located
     */
    private void attemptLogin() throws IOException {

        //Check if input is valid
        if (InputValidator.validateLoginForm(this)){

            //Store User
            User user = Database.getUser(userIDTextField.getText());

            //Get the customer form resource
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AppointmentFormView.fxml"));
            Parent root = loader.load();

            //Get the customer form controller
            AppointmentFormController controller = loader.getController();

            //Set the stage and user in the customer form controller
            AppointmentFormController.primaryStage = primaryStage;
            AppointmentFormController.currentUser = user;

            //Set the username label and timezone label
            controller.currentUserLabel.setText(resourceBundle.getString("userLabel") + "\n" + user.getUserName());
            controller.appointmentUserTextField.setText(user.getUserName());
            controller.currentTimeZoneLabel.setText(resourceBundle.getString("timezoneLabel") + "\n" + Calendar.getInstance().getTimeZone().getDisplayName());

            //Display the current time based on the users location and local time zone
            Timeline localClock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                LocalTime currentTime = LocalTime.now();

                //Format time if user is in the US
                if (currentTime.isAfter(LocalTime.of(12, 59)) && Locale.getDefault().getCountry().equals("US")) {
                    controller.currentLocalTimeLabel.setText(resourceBundle.getString("localTimeLabel") + "\n" + currentTime.minusHours(12).format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " pm");
                } else if (Locale.getDefault().getCountry().equals("US")){
                    controller.currentLocalTimeLabel.setText(resourceBundle.getString("localTimeLabel") + "\n" + currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " am");
                } else {
                    controller.currentLocalTimeLabel.setText(resourceBundle.getString("localTimeLabel") + "\n" + currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }

            }),
                    new KeyFrame(Duration.seconds(1))
            );
            localClock.setCycleCount(Animation.INDEFINITE);
            localClock.play();

            //Display the current time based on EST/Business time
            Timeline businessClock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                LocalTime currentTime = LocalTime.now(ZoneId.of("US/Eastern"));
                //Format time if user is in the US
                if (currentTime.isAfter(LocalTime.of(12, 59)) && Locale.getDefault().getCountry().equals("US")) {
                    controller.currentESTLabel.setText(resourceBundle.getString("businessTimeLabel") + "\n" + currentTime.minusHours(12).format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " pm");
                } else if (Locale.getDefault().getCountry().equals("US")){
                    controller.currentESTLabel.setText(resourceBundle.getString("businessTimeLabel") + "\n" + currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " am");
                } else {
                    controller.currentESTLabel.setText(resourceBundle.getString("businessTimeLabel") + "\n" + currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }

            }),
                    new KeyFrame(Duration.seconds(1))
            );
            businessClock.setCycleCount(Animation.INDEFINITE);
            businessClock.play();

            //Create customer form scene and link stylesheet
            Scene appointmentForm = new Scene(root, 1200, 750);
            URL stylesheet = getClass().getResource("../resources/AppointmentApp.css");
            appointmentForm.getStylesheets().add(stylesheet.toExternalForm());

             //Set the scene and show
            Stage stage = new Stage();
            stage.setScene(appointmentForm);
            stage.setResizable(true);
            stage.setTitle(resourceBundle.getString("windowTitle"));
            stage.show();
            primaryStage.close();
        }
    }

    /**
     * Record the login attempt of the user
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

    /** Set the event handlers for the LoginFormView
     *  Lambdas used for action event handlers to avoid instantiating Action Event Objects
     *  Lambdas used for key event handlers to avoid instantiating Key Event Objects
     * */
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
            confirmationAlert.setContentText(resourceBundle.getString("exitConfirmationMessage"));
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
