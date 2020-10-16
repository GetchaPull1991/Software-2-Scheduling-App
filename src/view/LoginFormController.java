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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
        userLocationLabel.setText("Location: " + currentLocale.getDisplayCountry());

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
            User user = Database.getUser(userIDTextField.getText());

            //Open the Customer form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CustomerFormView.fxml"));
            Parent root = loader.load();

            //Get the customer form controller
            CustomerFormController controller = loader.getController();

            //Set the stage and user in the customer form controller
            CustomerFormController.primaryStage = primaryStage;
            CustomerFormController.currentUser = user;

            //Set the username label and timezone label
            controller.currentUserLabel.setText("User: \n" + user.getUserName());
            controller.currentTimeZoneLabel.setText("TimeZone: \n" + Calendar.getInstance().getTimeZone().getDisplayName());

            // DOES NOT CHANGE BASED ON LOCALE
            //Display the current time based on the users location and local time zone
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                LocalTime currentTime = LocalTime.now();

                //Format time if user is in the US
                if (currentTime.isAfter(LocalTime.of(12, 59)) && Locale.getDefault().getCountry().equals("US")) {
                    controller.currentLocalTimeLabel.setText("Local Time: \n" + currentTime.minusHours(12).format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " pm");
                } else if (Locale.getDefault().getCountry().equals("US")){
                    controller.currentLocalTimeLabel.setText("Local Time: \n" + currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " am");
                } else {
                    controller.currentLocalTimeLabel.setText("Local Time: \n" + currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }

            }),
                    new KeyFrame(Duration.seconds(1))
            );
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();


            //Create customer form scene and link stylesheet
            Scene customerForm = new Scene(root, 1000, 800);
            URL stylesheet = getClass().getResource("../resources/AppointmentApp.css");
            customerForm.getStylesheets().add(stylesheet.toExternalForm());

            //Set the scene and show
            primaryStage.setScene(customerForm);
            primaryStage.setResizable(true);
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
