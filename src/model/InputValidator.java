package model;

import view.AppointmentFormController;
import view.CustomerFormController;
import view.LoginFormController;
import view.ReportViewController;

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
