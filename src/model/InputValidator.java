package model;

import view.AppointmentFormController;
import view.CustomerFormController;
import view.LoginFormController;
import view.ReportViewController;
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
           controller.inputErrorLabel.setText(controller.resourceBundle.getString("emptyFieldError"));
           controller.inputErrorLabel.setVisible(true);

           //Set is valid to false
           isValid = false;

       //Check if username and password are valid credentials
       } else if (!Database.checkUserCredentials(userName, password)){

           //Display error
           controller.inputErrorLabel.setText(controller.resourceBundle.getString("invalidCredentialsError"));
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

        //Check for empty fields
       if (controller.customerNameTextField.getText().equals("")
               || controller.customerAddressTextField.getText().equals("")
               || controller.customerPostalCodeTextField.getText().equals("")
               || controller.customerPhoneTextField.getText().equals("")
               || controller.customerCountryComboBox.getValue().equals("")
               || controller.customerDivisionComboBox.getValue().equals("")){
            controller.errorLabel.setText(controller.resourceBundle.getString("customerEmptyFieldsError"));
            controller.errorLabel.setVisible(true);
            isValid = false;
       } else controller.errorLabel.setVisible(false);

       //Return the result of the validation
       return isValid;
   }

    /**
     * @param controller the controller to validate input from
     * @return the result of the input validation
     */
    public static boolean validateAppointmentForm(AppointmentFormController controller){

        //Store the result of the validation
        boolean isValid = false;

        //Continue checks while valid

            //Check if fields are empty
            if (controller.appointmentContactComboBox.getValue() == null
                    || controller.appointmentCustomerIDComboBox.getValue() == null
                    || controller.appointmentTitleTextField.getText().equals("")
                    || controller.appointmentLocationTextField.getText().equals("")
                    || controller.appointmentDescriptionTextArea.getText().equals("")
                    || controller.appointmentStartDatepicker.getValue() == null
                    || controller.appointmentEndDatepicker.getValue() == null
                    || controller.startTimeComboBox.getValue() == null
                    || controller.endTimeComboBox.getValue() == null) {
                controller.errorLabel.setText(controller.resourceBundle.getString("emptyFieldsError"));
                controller.errorLabel.setVisible(true);
            } else if (Database.customerAppointmentOverlaps(controller.appointmentCustomerIDComboBox.getValue(),
                                                        LocalDateTime.of(controller.appointmentStartDatepicker.getValue(), convertStringToLocalTime(controller.startTimeComboBox.getValue())),
                                                                LocalDateTime.of(controller.appointmentEndDatepicker.getValue(), convertStringToLocalTime(controller.endTimeComboBox.getValue())))) {

                controller.errorLabel.setText(controller.resourceBundle.getString("overlappingAppointmentError"));
                controller.errorLabel.setVisible(true);
            } else {
                isValid = true;
                controller.errorLabel.setVisible(false);
            }

        //Return the result of the validation
        return isValid;
    }

    /**
     * @param controller the controller to validate input from
     * @return the result of the input validation
     */
    public static boolean validateReportView(ReportViewController controller){

        //Store the result of the validation
        boolean isValid = true;

        //Check if required fields are filled
        if (controller.scheduleByCustomerRadio.isSelected() && controller.customerComboBox.getValue() == null){
            controller.errorLabel.setText(controller.resourceBundle.getString("noSelectionError"));
            controller.errorLabel.setVisible(true);
            isValid = false;
        }

        if (controller.scheduleByContactRadio.isSelected() && controller.contactComboBox.getValue() == null){
            controller.errorLabel.setText(controller.resourceBundle.getString("noSelectionError"));
            controller.errorLabel.setVisible(true);
            isValid = false;
        }

        if (controller.reportTypeMonthRadio.isSelected()){
            if (controller.typeComboBox.getValue() == null || controller.monthComboBox.getValue() == null){
                controller.errorLabel.setText(controller.resourceBundle.getString("noSelectionError"));
                controller.errorLabel.setVisible(true);
                isValid = false;
            }
        }

        //Return the result of the validation
        return isValid;
    }

    /**
     * Convert a string time value in HH:mm format to a LocalTime object
     * @param time the string time value to convert
     * @return the LocalTime object
     */
    public static LocalTime convertStringToLocalTime (String time){
        return LocalTime.of(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)));
    }

}
