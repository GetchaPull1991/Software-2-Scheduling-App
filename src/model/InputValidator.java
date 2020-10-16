package model;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.AppointmentFormController;
import view.CustomerFormController;
import view.LoginFormController;
import view.ReportViewController;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**Class for validating input*/
public class InputValidator {

    /**
     * @param controller the controller to validate input from
     * @return the result of the input validation
     */
   public static boolean validateLoginForm(LoginFormController controller){

       //Store the result of the validation
       boolean isValid = true;

       //Get the username and password
       String userName = controller.userIDTextField.getText();
       String password = controller.userPasswordField.getText();

       //Check if username or password is blank
       if (userName.equals("") || password.equals("")){

           //Display error
           controller.inputErrorLabel.setText("* Please enter Username and Password");
           controller.inputErrorLabel.setVisible(true);

           //Set is valid to false
           isValid = false;

       //Check if username and password are valid credentials
       } else if (!Database.checkUserCredentials(userName, password)){

           //Display error
           controller.inputErrorLabel.setText("* The Username and/or Password you entered does not match our records");
           controller.inputErrorLabel.setVisible(true);

           //Set is valid to false
           isValid = false;

           //Log the login attempt
           controller.recordLoginAttempt(userName, false);

       //If tests pass
       } else {
           //Remove any error messages
           controller.inputErrorLabel.setText("");
           controller.inputErrorLabel.setVisible(false);

           //Log the login attempt
           controller.recordLoginAttempt(userName, true);
       }

       //Return the result
       return isValid;
   }

    /**
     * @param controller the controller to validate input from
     * @return the result of the input validation
     */
   public static boolean validateCustomerForm(CustomerFormController controller){

       //Store the result of the validation
       boolean isValid = true;

       //Get Customer information
       //Get user input
       String name = controller.customerNameTextField.getText();
       String address = controller.customerAddressTextField.getText();
       String postalCode = controller.customerPostalCodeTextField.getText();
       String phone = controller.customerPhoneTextField.getText();
       String country = controller.customerCountryComboBox.getValue();
       String division = controller.customerDivisionComboBox.getValue();

       if (name.equals("") || address.equals("") || postalCode.equals("") || phone.equals("") || country.equals("") || division.equals("")){
            controller.errorLabel.setText("* All Fields Are Required");
            controller.errorLabel.setVisible(true);
            isValid = false;
       } else controller.errorLabel.setVisible(false);

       //Returnt he result of the validation
       return isValid;
   }

    /**
     * @param controller the controller to validate input from
     * @return the result of the input validation
     */
    public static boolean validateAppointmentForm(AppointmentFormController controller){

        //Store the result of the validation
        boolean isValid = true;

        //Get Customer information
        //Get user input
        String contact = controller.appointmentContactComboBox.getValue();
        String customerID = controller.appointmentCustomerIDComboBox.getValue();
        String title = controller.appointmentTitleTextField.getText();
        String location = controller.appointmentLocationTextField.getText();
        String description = controller.appointmentDescriptionTextArea.getText();
        LocalDate startDate = controller.appointmentStartDatepicker.getValue();
        LocalDate endDate = controller.appointmentEndDatepicker.getValue();

        //Select hours and minutes from string and strip formatting to get start time
        LocalTime startTime = LocalTime.of(Integer.parseInt(controller.startTimeComboBox.getValue().substring(0,2)),
                Integer.parseInt(controller.startTimeComboBox.getValue().substring(3,5)));

        //Select hours and minutes from string and strip formatting to get end time
        LocalTime endTime = LocalTime.of(Integer.parseInt(controller.endTimeComboBox.getValue().substring(0, 2)),
                Integer.parseInt(controller.endTimeComboBox.getValue().substring(3, 5)));

        //Check if the start date and time is before the end date and time
        if (LocalDateTime.of(startDate, startTime).compareTo(LocalDateTime.of(endDate, endTime)) >= 0){
            controller.errorLabel.setText("* The Start Date and Time of the Appointment must be before the End Date and Time");
            controller.errorLabel.setVisible(true);
            isValid = false;
        }

        /*
        NEED INPUT VALIDATION FOR ENDTIME AND STARTTIME
         */



        if (contact == null || customerID.equals("") || title.equals("")
                || location.equals("") || description.equals("") || startDate == null || endDate == null || startTime == null || endTime == null){
            controller.errorLabel.setText("* All Fields Are Required");
            controller.errorLabel.setVisible(true);
            isValid = false;
        } else if(endDate.isBefore(startDate) || endTime.isBefore(startTime)) {

        }

        //controller.errorLabel.setVisible(false);


        /*
        //GET THE FIELDS FROM VBOX/HBOX NOT GRIDPANE//
        //VBOX/HBOX CHILDREN OF GRID PANE, HBOX CHILD OF VBOX, FIELDS CHILD OF HBOX
        //Get all the fields
        for (Node child : controller.appointmentFormGridPane.getChildren()){

            if (child instanceof VBox){
                for ()
                if (((TextField) child).getText().trim().isEmpty()){
                    isValid = false;
                }
            } else if (child instanceof ComboBox){
                if (((ComboBox) child).getSelectionModel().isEmpty()){
                    isValid = false;
                }
            } else if (child instanceof DatePicker){
                if (((DatePicker) child).getValue() == null){
                    isValid = false;
                }
            }
        }

         */

        //Returnt he result of the validation
        return isValid;
    }

    /**
     * @param controller the controller to validate input from
     * @return the result of the input validation
     */
    public static boolean validateReportView(ReportViewController controller){

        //Store the result of the validation
        boolean isValid = true;

        //Returnt he result of the validation
        return isValid;
    }

}
