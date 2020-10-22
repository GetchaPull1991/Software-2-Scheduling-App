package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Appointment;
import model.Customer;
import model.Database;
import model.InputValidator;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

/** Class to manage all Customer Form UI Functionality*/
public class CustomerFormController implements Initializable {
    @FXML
    public Label customerTablePlaceholder;
    @FXML
    public Label customerDivisionLabel;
    @FXML
    public Label customerCountryLabel;
    @FXML
    public Label customerPhoneLabel;
    @FXML
    public Label customerPostalCodeLabel;
    @FXML
    public Label customerAddressLabel;
    @FXML
    public Label customerNameLabel;
    @FXML
    public Label customerIDLabel;
    @FXML
    public ComboBox<String> customerCountryComboBox;
    @FXML
    public ComboBox<String> customerDivisionComboBox;
    @FXML
    public TextField customerIDTextField;
    @FXML
    public TextField customerNameTextField;
    @FXML
    public TextField customerAddressTextField;
    @FXML
    public TextField customerPostalCodeTextField;
    @FXML
    public TextField customerPhoneTextField;
    @FXML
    public Button customerSaveButton;
    @FXML
    public TableView<Customer> customerDataTable;
    @FXML
    public TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    public TableColumn<Customer, String> customerNameColumn;
    @FXML
    public TableColumn<Customer, String> customerAddressColumn;
    @FXML
    public TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML
    public TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    public TableColumn<Customer, String> customerCountryColumn;
    @FXML
    public TableColumn<Customer, String> customerDivisionColumn;
    @FXML
    public Button customerDeleteButton;
    @FXML
    public Button customerUpdateButton;
    @FXML
    public Label errorLabel;
    @FXML
    public GridPane customerFormGridPane;

    //Get the users country
    String country = Locale.getDefault().getCountry();

    //Resource bundle for language
    public ResourceBundle resourceBundle;

    //Store the selected customer for updating and deleting
    private  Customer selectedCustomer;

    //Create confirmation alert
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    //Create date time formatter
    DateTimeFormatter usCanadaFormatter;
    DateTimeFormatter franceUkFormatter;

    //Retrieve customers and divisions from database to provide a more responsive UI
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Database.getAllCustomers());
    private ObservableList<String> usDivisions = FXCollections.observableArrayList(Database.getCountryDivisions("U.S"));
    private ObservableList<String> ukDivisions = FXCollections.observableArrayList(Database.getCountryDivisions("UK"));
    private ObservableList<String> canadaDivisions = FXCollections.observableArrayList(Database.getCountryDivisions("Canada"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserLanguage();
        setCellFactories();
        setEventListeners();
        customerCountryComboBox.setItems(Database.getAllCountries());
        customerDataTable.setItems(allCustomers);
    }

    /** Set the application language according to the users locale*/
    public void setUserLanguage(){

        //Get resource bundle
        resourceBundle = ResourceBundle.getBundle("resources.CustomerForm", Locale.getDefault());

        //Set form labels
        customerIDLabel.setText(resourceBundle.getString("customerIDFieldLabel"));
        customerNameLabel.setText(resourceBundle.getString("nameFieldLabel"));
        customerAddressLabel.setText(resourceBundle.getString("addressFieldLabel"));
        customerPostalCodeLabel.setText(resourceBundle.getString("postalCodeFieldLabel"));
        customerPhoneLabel.setText(resourceBundle.getString("phoneFieldLabel"));
        customerCountryLabel.setText(resourceBundle.getString("countryComboLabel"));
        customerDivisionLabel.setText(resourceBundle.getString("firstLevelDivisionComboLabel"));

        //Set form values
        customerIDTextField.setText(resourceBundle.getString("customerIDDefault"));
        customerCountryComboBox.setPromptText(resourceBundle.getString("countryComboPrompt"));

        //Set button labels
        customerSaveButton.setText(resourceBundle.getString("addCustomerButtonLabel"));
        customerDeleteButton.setText(resourceBundle.getString("deleteCustomerButtonLabel"));
        customerUpdateButton.setText(resourceBundle.getString("updateCustomerButtonLabel"));

        //Set table columns
        customerIDColumn.setText(resourceBundle.getString("customerIDFieldLabel"));
        customerNameColumn.setText(resourceBundle.getString("nameFieldLabel"));
        customerAddressColumn.setText(resourceBundle.getString("addressFieldLabel"));
        customerPostalCodeColumn.setText(resourceBundle.getString("postalCodeFieldLabel"));
        customerPhoneColumn.setText(resourceBundle.getString("phoneFieldLabel"));
        customerCountryColumn.setText(resourceBundle.getString("countryComboLabel"));
        customerDivisionColumn.setText(resourceBundle.getString("customerTableDivisionColumn"));

        //Set table placeholder
        customerTablePlaceholder.setText(resourceBundle.getString("customerTablePlaceholder"));

        //Get Date
        usCanadaFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy'" + resourceBundle.getString("atLabel") + "' HH:mm");
        franceUkFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'" + resourceBundle.getString("atLabel") + "' HH:mm");
    }

    /**
     * Add a new customer to the table and the database
     */
    private void addCustomer() {

        //Check if user input is valid
        if (InputValidator.validateCustomerForm(this)){

            //Generate unique customer id
            Random random = new Random();
            int id = random.nextInt(1000);
            while (Database.getCustomerByID(id) != null) {
                id = random.nextInt();
            }

            //Create a new customer
            Customer customer = new Customer(customerNameTextField.getText(),
                                            customerAddressTextField.getText(),
                                            customerPostalCodeTextField.getText(),
                                            customerPhoneTextField.getText(),
                                            customerDivisionComboBox.getValue(),
                                            customerCountryComboBox.getValue(), id);

            //Add the customer to database
            Database.addCustomer(customer, AppointmentFormController.currentUser);

            //Add the customer to table
            allCustomers.add(customer);
            customerDataTable.setItems(allCustomers);
        }
    }

    /** Delete customer from table and database*/
    private void deleteCustomer(){
        //Get selected customer
        Customer selectedCustomer = customerDataTable.getSelectionModel().getSelectedItem();

        //Remove customer from table
        allCustomers.remove(selectedCustomer);

        //Remove customer from database
        Database.removeCustomer(selectedCustomer);
    }

    /** Populate form with customer data for updating*/
    private void populateFormForUpdate(){

        //Get the selected customer from the table
        selectedCustomer = customerDataTable.getSelectionModel().getSelectedItem();

        //Populate the text fields with the selected customers information
        customerIDTextField.setText(Integer.toString(selectedCustomer.getCustomerID()));
        customerNameTextField.setText(selectedCustomer.getName());
        customerAddressTextField.setText(selectedCustomer.getAddress());
        customerPostalCodeTextField.setText(selectedCustomer.getPostalCode());
        customerPhoneTextField.setText(selectedCustomer.getPhoneNumber());
        customerCountryComboBox.getSelectionModel().select(selectedCustomer.getCountry());
        customerDivisionComboBox.getSelectionModel().select(selectedCustomer.getDivision());

    }

    /**
     * Update the customer in the table and the database
     */
    private void updateCustomer() {

        //Update the selected customer values
        selectedCustomer.setName(customerNameTextField.getText());
        selectedCustomer.setAddress(customerAddressTextField.getText());
        selectedCustomer.setPostalCode(customerPostalCodeTextField.getText());
        selectedCustomer.setPhoneNumber(customerPhoneTextField.getText());
        selectedCustomer.setCountry(customerCountryComboBox.getValue());
        selectedCustomer.setDivision(customerDivisionComboBox.getValue());

        //Update customer in the customer data table
        allCustomers.set(customerDataTable.getSelectionModel().getSelectedIndex(), selectedCustomer);

        //Update the customer in the database
        Database.updateCustomer(selectedCustomer, AppointmentFormController.currentUser);

        //Reset the customer form to default values
        resetCustomerForm();
    }

    /** Create event handlers for UI events
     * Lambdas used for action event handlers to avoid instantiating Action Event Object
     * Lambdas used for button response handles to avoid instantiating Button Type Object
     */
    private void setEventListeners(){

        //Filter the and enable the division combo box when the user selects a country
        customerCountryComboBox.setOnAction(actionEvent -> {

            if (customerCountryComboBox.getValue() != null) {
                //Check for country selection and set divisions accordingly
                switch (customerCountryComboBox.getValue()) {
                    case "U.S":
                        customerDivisionComboBox.setItems(usDivisions);
                        break;
                    case "UK":
                        customerDivisionComboBox.setItems(ukDivisions);
                        break;
                    case "Canada":
                        customerDivisionComboBox.setItems(canadaDivisions);
                        break;
                }
            }

            //Set customer division combo box prompt
            customerDivisionComboBox.setPromptText(resourceBundle.getString("firstLevelDivisionComboPrompt"));

            //Enable the customer division combo box
            customerDivisionComboBox.disableProperty().setValue(false);
        });

        //Add Customer when
        customerSaveButton.setOnAction(actionEvent -> addCustomer());

        //Update customer
        customerUpdateButton.setOnAction(actionEvent -> {

            //Display error if no customer selected
            if (customerDataTable.getSelectionModel().getSelectedItem() == null){
                errorLabel.setText(resourceBundle.getString("noCustomerSelectedToUpdate"));
                errorLabel.setVisible(true);
            } else {
                //Hide error label
                errorLabel.setVisible(false);

                //Populate the form with selected Customer information
                populateFormForUpdate();

                //Set the text and event for the customer save button
                customerSaveButton.setText(resourceBundle.getString("saveCustomerChangesButtonLabel"));
                customerSaveButton.setOnAction(actionEvent1 -> updateCustomer());
            }
        });

        //Delete customer
        customerDeleteButton.setOnAction(actionEvent -> {

            //Get the selected customer from the table
            selectedCustomer = customerDataTable.getSelectionModel().getSelectedItem();

            //Display error if no Customer selected
            if (selectedCustomer == null){
                errorLabel.setText(resourceBundle.getString("noCustomerSelectedToDelete"));
                errorLabel.setVisible(true);
            } else {
                //Hide error label
                errorLabel.setVisible(false);

                //Set confirmation title and header
                confirmationAlert.setHeaderText(resourceBundle.getString("deleteCustomerConfirmationHeader"));
                confirmationAlert.setTitle(resourceBundle.getString("deleteCustomerConfirmationHeader"));

                //Confirm user wants to delete selected customer
                confirmationAlert.setContentText(resourceBundle.getString("deleteCustomerConfirmation") + "\n" +
                                                resourceBundle.getString("customerIDFieldLabel") + ": " + selectedCustomer.getCustomerID() +
                                                "\n" + resourceBundle.getString("customerNameConfirmationLabel") + ": " + selectedCustomer.getName());

                //Wait on response from user
                confirmationAlert.showAndWait().ifPresent(deleteCustomerResponse -> {
                    if (deleteCustomerResponse.equals(ButtonType.OK)) {

                        //Check if the customer has any associated appointments
                        ObservableList<Appointment> customerAppointments = Database.getCustomerAppointments(selectedCustomer.getCustomerID());
                        if (customerAppointments.size() > 0){

                            //Display the appointment information to the user and confirm they want to delete all the appointments associated with the customer
                            StringBuilder confirmationContent = new StringBuilder(resourceBundle.getString("customerHasAppointmentsConfirmationHeader"));
                            confirmationContent.append(":\n");

                            //Append data for each appointment
                            for (Appointment appointment : customerAppointments){
                                confirmationContent.append("\n");
                                confirmationContent.append(resourceBundle.getString("customerIDFieldLabel"));
                                confirmationContent.append(": ");
                                confirmationContent.append(appointment.getAppointmentID());
                                confirmationContent.append("\n");
                                confirmationContent.append(resourceBundle.getString("appointmentTitleConfirmationLabel"));
                                confirmationContent.append(": ");
                                confirmationContent.append(appointment.getTitle());
                                confirmationContent.append("\n");
                                confirmationContent.append(resourceBundle.getString("timeslotConfirmationLabel"));
                                confirmationContent.append(": ");

                                //Format date based on country
                                if (country.equals("US") || country.equals("CA")) {
                                    confirmationContent.append(LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime()).format(usCanadaFormatter));
                                    confirmationContent.append(resourceBundle.getString("toConfirmationLabel"));
                                    confirmationContent.append(LocalDateTime.of(appointment.getEndDate(), appointment.getEndTime()).format(usCanadaFormatter));
                                } else {
                                    confirmationContent.append(LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime()).format(franceUkFormatter));
                                    confirmationContent.append(resourceBundle.getString("toConfirmationLabel"));
                                    confirmationContent.append(LocalDateTime.of(appointment.getEndDate(), appointment.getEndTime()).format(franceUkFormatter));
                                }
                                confirmationContent.append("\n");
                            }

                            //Append footer
                            confirmationContent.append("\n");
                            confirmationContent.append(resourceBundle.getString("customerHasAppointmentsConfirmationFooter"));

                            //Set content
                            confirmationAlert.setContentText(String.valueOf(confirmationContent));

                            //Set title and header
                            confirmationAlert.setTitle(resourceBundle.getString("deleteCustomerConfirmationHeader"));
                            confirmationAlert.setHeaderText(resourceBundle.getString("deleteCustomerConfirmationHeader"));
                            confirmationAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

                            //Wait for user response
                            confirmationAlert.showAndWait().ifPresent(deleteCustomerAppointmentsResponse ->{
                                if (deleteCustomerAppointmentsResponse.equals(ButtonType.OK)){

                                    //Remove appointments associated with customer from database and appointment list
                                    for (Appointment appointment : customerAppointments){
                                        Database.removeAppointment(appointment);
                                    }
                                    //Delete customer
                                    deleteCustomer();
                                }
                            });

                        } else {

                            //If there are no appointments associated with the customer the user confirms, delete the customer
                            deleteCustomer();
                        }
                    }
                });
            }
        });
    }

    /** Set the cell factories for the customer table*/
    private void setCellFactories(){

        //Set cell factories for Customer table
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    /** Reset the customer form to default values
     * Lambda used for action event handler to avoid instantiating Action Event Object
     */
    private void resetCustomerForm(){
        customerSaveButton.setText(resourceBundle.getString("addCustomerButtonLabel"));
        customerIDTextField.setText(resourceBundle.getString("customerIDDefault"));
        customerNameTextField.clear();
        customerAddressTextField.clear();
        customerPostalCodeTextField.clear();
        customerPhoneTextField.clear();
        customerCountryComboBox.getSelectionModel().clearSelection();
        customerDivisionComboBox.getSelectionModel().clearSelection();

        //Change save button action event to add customer
        customerSaveButton.setOnAction(actionEvent -> addCustomer());
    }


}
