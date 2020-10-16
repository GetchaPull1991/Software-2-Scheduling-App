package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    @FXML
    public  Label currentUserLabel;
    @FXML
    public ComboBox<String> customerCountryComboBox;
    @FXML
    public ComboBox<String> customerDivisionComboBox;
    @FXML
    public BorderPane contentBorderPane;
    @FXML
    public Label formLabel;
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
    @FXML
    public Label currentTimeZoneLabel;
    @FXML
    public Label currentLocalTimeLabel;
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

    //Appointment view
    GridPane appointmentFormView;

    //Store the selected customer for updating and deleting
    private  Customer selectedCustomer;

    //Store the primary stage of the application
    public static Stage primaryStage;

    //Create confirmation alert
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    //Create error alert
    public static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    //Store Current User and allow access in all controllers
    public static User currentUser;

    //Retrieve customers and divisions from database to provide a more responsive UI
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Database.getAllCustomers());
    private ObservableList<String> usDivisions = FXCollections.observableArrayList(Database.getCountryDivisions("U.S"));
    private ObservableList<String> ukDivisions = FXCollections.observableArrayList(Database.getCountryDivisions("UK"));
    private ObservableList<String> canadaDivisions = FXCollections.observableArrayList(Database.getCountryDivisions("Canada"));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellFactories();
        setEventListeners();
        customerCountryComboBox.setItems(Database.getAllCountries());
        customerDataTable.setItems(allCustomers);
    }

    /**
     * Add a new customer to the table and the database
     * @throws ParseException if date cannot be parsed
     */
    private void addCustomer() throws ParseException {

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

            //Add new customer to database
            Database.addCustomer(customer, currentUser);

            //Add customer to table
            allCustomers.add(customer);
            customerDataTable.setItems(allCustomers);
        }
    }

    /**Delete customer from table and database*/
    private void deleteCustomer(){
        //Get selected customer
        Customer selectedCustomer = customerDataTable.getSelectionModel().getSelectedItem();

        //Remove customer from table
        allCustomers.remove(selectedCustomer);
        //customerDataTable.setItems(allCustomers);

        //Remove customer from database
        Database.removeCustomer(selectedCustomer);
    }

    /**Populate form with customer data for updating*/
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
     * @throws ParseException if date cannot be parsed
     */
    private void updateCustomer() throws ParseException {

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
        Database.updateCustomer(selectedCustomer, currentUser);

        //Reset the customer form to default values
        resetCustomerForm();
    }



    /**Create event handlers for UI events*/
    private void setEventListeners(){

        //Open customer view
        customerViewMenuButton.setOnAction(actionEvent -> {
            formLabel.setText("Manage Customers");
           contentBorderPane.setCenter(customerFormGridPane);
        });

        //Open appointment view
        appointmentViewMenuButton.setOnAction(actionEvent -> {
            formLabel.setText("Manage Appointments");
            try {
                appointmentFormView = FXMLLoader.load(getClass().getResource("../view/AppointmentFormView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            contentBorderPane.setCenter(appointmentFormView);
        });

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

        //Filter the and enable the dvision combo box when the user selects a country
        customerCountryComboBox.setOnAction(actionEvent -> {

            //Check for country selection and set divisions accordingly
            if (customerCountryComboBox.getValue().equals("U.S")){
                customerDivisionComboBox.setItems(usDivisions);
            } else if (customerCountryComboBox.getValue().equals("UK")){
                customerDivisionComboBox.setItems(ukDivisions);
            } else if (customerCountryComboBox.getValue().equals("Canada")){
                customerDivisionComboBox.setItems(canadaDivisions);
            }

            //Enable the customer division combo box
            customerDivisionComboBox.disableProperty().setValue(false);
        });

        //Add Customer
        customerSaveButton.setOnAction(actionEvent -> {
            try {
                addCustomer();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        //Update customer
        customerUpdateButton.setOnAction(actionEvent -> {

            //Display error if no customer is selected
            if (customerDataTable.getSelectionModel().getSelectedItem() == null){
                errorLabel.setText("* Please select a Customer to update");
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);

                //Populate the form with selected Customer information
                populateFormForUpdate();

                //Set the text and event for the customer save button
                customerSaveButton.setText("Save Changes");
                customerSaveButton.setOnAction(actionEvent1 -> {
                    try {
                        updateCustomer();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        //Delete customer
        customerDeleteButton.setOnAction(actionEvent -> {
            selectedCustomer = customerDataTable.getSelectionModel().getSelectedItem();
            //Display error if no Customer is selected
            if (selectedCustomer == null){
                errorLabel.setText("* Please select a Customer to delete");
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);

                //Confirm user wants to delete selected customer
                confirmationAlert.setContentText("Are you sure you want to delete this customer?\n" +
                                                "Customer ID: " + selectedCustomer.getCustomerID() +
                                                "\nCustomer Name: " + selectedCustomer.getName());
                confirmationAlert.showAndWait().ifPresent(deleteCustomerResponse -> {
                    if (deleteCustomerResponse.equals(ButtonType.OK)) {

                        //Check if the customer has any associated appointments
                        ObservableList<Appointment> customerAppointments = Database.getCustomerAppointments(selectedCustomer.getCustomerID());
                        if (customerAppointments.size() > 0){

                            //Display the appointment information to the user and confirm they want to delete all the appointments with the customer
                            StringBuilder confirmationContent = new StringBuilder("This customer has the following appointments:\n");
                            for (Appointment appointment : customerAppointments){
                                confirmationContent.append("\nAppointment ID: ");
                                confirmationContent.append(appointment.getAppointmentID());
                                confirmationContent.append("\nAppointment Title: ");
                                confirmationContent.append(appointment.getTitle());
                                confirmationContent.append("\nTimeslot: ");
                                confirmationContent.append(LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime()).format(DateTimeFormatter.ofPattern("MM-dd-yyyy 'at' HH:mm")));
                                confirmationContent.append(" to ");
                                confirmationContent.append(LocalDateTime.of(appointment.getEndDate(), appointment.getEndTime()).format(DateTimeFormatter.ofPattern("MM-dd-yyyy 'at' HH:mm")));
                                confirmationContent.append("\n");
                            }
                            confirmationContent.append("\nDeleting this customer will delete all associated appointments.\nAre you sure you want to continue?");
                            confirmationAlert.setContentText(String.valueOf(confirmationContent));
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
                        }
                    } else {
                        deleteCustomer();
                    }
                });
            }
        });
    }

    /**Set the cell factories for the customer table*/
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


    /**Reset the customer form to default values*/
    private void resetCustomerForm(){
        customerSaveButton.setText("Add Customer");
        customerIDTextField.setText("ID is Auto-Generated");
        customerNameTextField.clear();
        customerAddressTextField.clear();
        customerPostalCodeTextField.clear();
        customerPhoneTextField.clear();
        customerCountryComboBox.getSelectionModel().clearSelection();
        customerDivisionComboBox.getSelectionModel().clearSelection();

        //Change save button action event to add customer
        customerSaveButton.setOnAction(actionEvent -> {
            try {
                addCustomer();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }


}
