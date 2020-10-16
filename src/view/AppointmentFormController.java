package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.*;
import java.net.URL;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class AppointmentFormController implements Initializable {


    @FXML
    public TextField appointmentIDTextField;
    @FXML
    public ComboBox<String> appointmentContactComboBox;
    @FXML
    public TextField appointmentTitleTextField;
    @FXML
    public static TextField appointmentUserTextField;
    @FXML
    public TextField appointmentLocationTextField;
    @FXML
    public DatePicker appointmentStartDatepicker;
    @FXML
    public DatePicker appointmentEndDatepicker;
    @FXML
    public TextArea appointmentDescriptionTextArea;
    @FXML
    public TableView<Appointment> appointmentDataTable;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    public TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML
    public TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML
    public TableColumn<Appointment, String> appointmentLocationColumn;
    @FXML
    public TableColumn<Appointment, String> appointmentContactColumn;
    @FXML
    public TableColumn<Appointment, LocalDate> appointmentStartDateColumn;
    @FXML
    public TableColumn<Appointment, LocalTime> appointmentStartTimeColumn;
    @FXML
    public TableColumn<Appointment, LocalDate> appointmentEndDateColumn;
    @FXML
    public TableColumn<Appointment, LocalTime> appointmentEndTimeColumn;
    @FXML
    public TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML
    public TableColumn<Appointment, String> appointmentCustomerIDColumn;
    @FXML
    public Label errorLabel;
    @FXML
    public Button appointmentDeleteButton;
    @FXML
    public Button appointmentUpdateButton;
    @FXML
    public Button addAppointmentButton;
    @FXML
    public TextField appointmentTypeTextField;
    @FXML
    public ComboBox<String> appointmentCustomerIDComboBox;
    @FXML
    public ComboBox<String> startTimeComboBox;
    @FXML
    public ComboBox<String> endTimeComboBox;
    @FXML
    public GridPane appointmentFormGridPane;


    //Retrieve the appointments from the database
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Database.getAllAppointments());

    //Confirmation alert
    Alert confirmationAlert = new Alert (Alert.AlertType.CONFIRMATION);

    //Store selected appointments for updating and deleting
    Appointment selectedAppointment;

    //Create date and time formatters for display
    DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter dateHourMinuteformatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm");
    DateTimeFormatter usCanadaDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    //Get the users country
    String country = Locale.getDefault().getCountry();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBoxes();
        setEventListeners();
        setCellFactories();
        allAppointments = Database.getAllAppointments();
        appointmentDataTable.setItems(allAppointments);
    }

    /**
     * Add appointment to table and database
     * @throws ParseException if date cannot be parsed
     */
    private void addAppointment() throws ParseException {
        //Check if user input is valid
        if (InputValidator.validateAppointmentForm(this)){

            //Generate unique appointment id
            Random random = new Random();
            int id = random.nextInt(1000);
            while (Database.getAppointmentByID(id) != null) {
                id = random.nextInt();
            }


            //Select hours and minutes from string and strip formatting to get start time
            LocalTime startTime = LocalTime.of(Integer.parseInt(startTimeComboBox.getValue().substring(0, 2)),
                                    Integer.parseInt(startTimeComboBox.getValue().substring(3, 5)));


            //Select hours and minutes from string and strip formatting to get end time
            LocalTime endTime = LocalTime.of(Integer.parseInt(endTimeComboBox.getValue().substring(0, 2)),
                                    Integer.parseInt(endTimeComboBox.getValue().substring(3, 5)));



            //Create new appointment
            Appointment appointment = new Appointment(appointmentContactComboBox.getValue(),
                                                    id,
                                                    appointmentTitleTextField.getText(),
                                                    appointmentDescriptionTextArea.getText(),
                                                    appointmentLocationTextField.getText(),
                                                    appointmentTypeTextField.getText(),
                                                    appointmentStartDatepicker.getValue(),
                                                    startTime,
                                                    appointmentEndDatepicker.getValue(),
                                                    endTime,
                                                    Integer.parseInt(appointmentCustomerIDComboBox.getValue()));

            //Add new appointment to table
            allAppointments.add(appointment);

            resetAppointmentForm();

            //Add new appointment to database
            Database.addAppointment(appointment, CustomerFormController.currentUser);

        }
    }

    /**Populate the appointment form with appointment information for updating*/
    private void populateFormForUpdate() {

        //Disable table
        appointmentDataTable.disableProperty().setValue(true);

        //Get selected appointment from appointment table
        selectedAppointment = appointmentDataTable.getSelectionModel().getSelectedItem();


        //C
        if (selectedAppointment != null) {
            appointmentIDTextField.setText(Integer.toString(selectedAppointment.getAppointmentID()));
            appointmentContactComboBox.getSelectionModel().select(selectedAppointment.getContactName());
            appointmentCustomerIDComboBox.getSelectionModel().select(Integer.toString(selectedAppointment.getCustomerID()));
            appointmentTitleTextField.setText(selectedAppointment.getTitle());
            appointmentLocationTextField.setText(selectedAppointment.getLocation());
            appointmentTypeTextField.setText(selectedAppointment.getType());
            appointmentStartDatepicker.setValue(selectedAppointment.getStartDate());
            startTimeComboBox.getSelectionModel().select(selectedAppointment.getStartTime().format(hourMinuteFormatter));
            appointmentEndDatepicker.setValue(selectedAppointment.getEndDate());
            endTimeComboBox.getSelectionModel().select(selectedAppointment.getEndTime().format(hourMinuteFormatter));
            appointmentDescriptionTextArea.setText(selectedAppointment.getDescription());

        }

    }

    /**Update a selected appointment*/
    private void updateAppointment() throws ParseException {

        //Update appointment information
        selectedAppointment.setContactName(appointmentContactComboBox.getValue());
        selectedAppointment.setCustomerID(Integer.parseInt(appointmentCustomerIDComboBox.getValue()));
        selectedAppointment.setTitle(appointmentTitleTextField.getText());
        selectedAppointment.setLocation(appointmentLocationTextField.getText());
        selectedAppointment.setType(appointmentTypeTextField.getText());
        selectedAppointment.setDescription(appointmentDescriptionTextArea.getText());

        //Update appointment time information
        selectedAppointment.setStartDate(appointmentStartDatepicker.getValue());
        selectedAppointment.setStartTime(LocalDateTime.parse(startTimeComboBox.getValue(), hourMinuteFormatter).toLocalTime());
        selectedAppointment.setEndDate(appointmentEndDatepicker.getValue());
        selectedAppointment.setEndTime(LocalDateTime.parse(endTimeComboBox.getValue(), hourMinuteFormatter).toLocalTime());

        //Update appointment in table
        allAppointments.set(appointmentDataTable.getSelectionModel().getSelectedIndex(), selectedAppointment);

        //Update appointment in database
        Database.updateAppontment(selectedAppointment, CustomerFormController.currentUser);

        //Reset form
        resetAppointmentForm();
    }

    /**Delete appointment from table and database*/
    private void deleteAppointment(){

        //Get the selected appointment
        Appointment selectedAppointment = appointmentDataTable.getSelectionModel().getSelectedItem();


        //Check if a selection was made
        if (selectedAppointment != null) {
            //Set custom confirmation alert with Appointment ID and Appointment Type
            confirmationAlert.setContentText("Are you sure you want to cancel this appointment?" +
                                            "\nAppointment ID: " + selectedAppointment.getAppointmentID() +
                                            "\nType: " + selectedAppointment.getType());
            //Display confirmation alert
            confirmationAlert.showAndWait().ifPresent(response ->{
                if (response.equals(ButtonType.OK)){
                    //If user selects OK, remove appointment from table
                    allAppointments.remove(selectedAppointment);
                    //Remove appointment from database
                    Database.removeAppointment(selectedAppointment);
                }
            });
        } else {
            //If no selection is made, display error in UI
            errorLabel.setText("* Please select an appointment to delete");
        }

    }

    /**Reset the appointment form to default values*/
    private void resetAppointmentForm(){

        //Reset fields
        appointmentIDTextField.setText("ID is Auto-Generated");
        appointmentContactComboBox.getSelectionModel().clearSelection();
        appointmentCustomerIDComboBox.getSelectionModel().clearSelection();
        appointmentTitleTextField.clear();
        //appointmentUserTextField.setText("Current User");
        appointmentLocationTextField.clear();
        appointmentTypeTextField.clear();
        appointmentStartDatepicker.setValue(null);
        startTimeComboBox.getSelectionModel().clearSelection();
        appointmentEndDatepicker.setValue(null);
        endTimeComboBox.getSelectionModel().clearSelection();
        appointmentDescriptionTextArea.clear();

        //Reset add appointment button
        addAppointmentButton.setText("Add Appointment");
        addAppointmentButton.setOnAction(actionEvent -> {
            try {
                addAppointment();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        appointmentDataTable.disableProperty().setValue(false);
    }

    /**Set cell factories for appointment data table*/
    private void setCellFactories(){

        //Set cell factories for appointment data table
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //Set start date factory and format date for display
        appointmentStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        appointmentStartDateColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty){
                    setText(null);
                } else {
                    if (country.equals("US") || country.equals("CA")) {
                        setText(date.format(usCanadaDateFormat));
                    }
                }
            }
        });

        //Set end date factory and format date for display
        appointmentEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        appointmentEndDateColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty){
                    setText(null);
                } else {
                    if (country.equals("US") || country.equals("CA")) {
                        setText(date.format(usCanadaDateFormat));
                    }
                }
            }
        });

        //Set start time factory and format time for display
        appointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentStartTimeColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalTime time, boolean empty){
                super.updateItem(time, empty);
                if (empty){
                    setText(null);
                } else if (time.compareTo(LocalTime.of(12, 59)) > 0 && country.equals("US")) {
                    setText(time.minusHours(12).format(hourMinuteFormatter) + " pm");
                } else if (country.equals("US")) {
                    setText(time.format(hourMinuteFormatter) + " am");
                } else {
                    setText(time.format(hourMinuteFormatter));
                }
            }
        });

        //Set end time factory and format time for display
        appointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentEndTimeColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalTime time, boolean empty){
                super.updateItem(time, empty);
                if (empty){
                    setText(null);
                } else if (time.compareTo(LocalTime.of(12, 59)) > 0 && country.equals("US")) {
                    setText(time.minusHours(12).format(hourMinuteFormatter) + " pm");
                } else  if (country.equals("US")){
                    setText(time.format(hourMinuteFormatter) + " am");
                } else {
                    setText(time.format(hourMinuteFormatter));
                }
            }
        });


        //Disable Cells in datepicker that are before the current day or weekend days
        appointmentEndDatepicker.setDayCellFactory(cell -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 || date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY));
            }
        });

        //Disable Cells in the datepicker that are before the current day or weekend days
        appointmentStartDatepicker.setDayCellFactory(cell -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 || date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY));
            }
        });

        //Display table row of past appointments in red

    }

    /**Set the event listeners*/
    private void setEventListeners(){

        //Add new appointment
        addAppointmentButton.setOnAction(actionEvent -> {
            try {
                addAppointment();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        //Cancel appointment
        appointmentDeleteButton.setOnAction(actionEvent -> {
            deleteAppointment();
        });

        //Update appointment
        appointmentUpdateButton.setOnAction(actionEvent -> {

            //Get selected appointment
            selectedAppointment = appointmentDataTable.getSelectionModel().getSelectedItem();

            //Display an error if no appointment is selected
            if (selectedAppointment == null){
                errorLabel.setText("* Please select an Appointment to update");
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);

                //Populate the form with the selected Appointment information
                populateFormForUpdate();

                //Set the text and event for the add appointment button
                addAppointmentButton.setText("Save Changes");
                addAppointmentButton.setOnAction(actionEvent1 -> {
                    try {
                        updateAppointment();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

    }

    /**Populate all the combo boxes in the view*/
    private void populateComboBoxes(){

        //Populate contacts combo box
        ObservableList<String> contacts = FXCollections.observableArrayList();
        for (Contact contact : Database.getContacts()){
            contacts.add(contact.getName());
        }
        appointmentContactComboBox.setItems(contacts);

        /*
        Populate Customer_ID combo box
        I used a combo box instead of a text field because an appointment
        cannot exist without a customer being tied to it.
        A customer must be made before an appointment
         */
        ObservableList<String> availableCustomerIDs = FXCollections.observableArrayList();
        for (Customer customer : Database.getAllCustomers()){
            availableCustomerIDs.add(Integer.toString(customer.getCustomerID()));
        }
        appointmentCustomerIDComboBox.setItems(availableCustomerIDs);


        //Create lists to store the start and end times for the appointment
        ObservableList<String> startTimes = FXCollections.observableArrayList();
        ObservableList<String> endTimes = FXCollections.observableArrayList();

        //Start the time at 8 am local time
        LocalTime localTime = LocalTime.of(8, 0);


        //Format the time : 8:00
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            //Add am time slots to lists : between 8am and 11:45am
            while (!localTime.equals(LocalTime.of(12, 0))) {
                startTimes.add(localTime.format(timeFormatter));
                endTimes.add(localTime.format(timeFormatter));
                localTime = localTime.plusMinutes(15);
            }

            //Add pm time slots to lists : between 12pm and 5pm
            //Formatted from military to standard time
            while (!localTime.equals(LocalTime.of(17, 15))) {
                startTimes.add(localTime.format(timeFormatter));
                endTimes.add(localTime.format(timeFormatter));
                localTime = localTime.plusMinutes(15);
            }

        //Set the combo box values
        startTimeComboBox.setItems(startTimes);
        endTimeComboBox.setItems(endTimes);

        /* DISPLAY US TIMES, POPULATES COMBO BOX CORRECTLY BUT DOES NOT SHOW SELECTED **SET BUTTON CELL NEEDED**
        startTimeComboBox.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty){
                if (empty){
                    setText(null);
                } else {
                    LocalTime time = LocalTime.of(Integer.parseInt(item.substring(0,2)), Integer.parseInt(item.substring(3,5)));
                    if (time.isAfter(LocalTime.of(12, 59)) && country.equals("US")){
                        setText(time.minusHours(12).format(timeFormatter) + " pm");
                    } else if (country.equals("US")){
                        setText(time.format(timeFormatter) + " am");
                    } else {
                        setText(time.format(timeFormatter));
                    }
                }
            }
        });

        endTimeComboBox.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty){
                if (empty){
                    setText(null);
                } else {
                    LocalTime time = LocalTime.of(Integer.parseInt(item.substring(0,2)), Integer.parseInt(item.substring(3,5)));
                    if (time.isAfter(LocalTime.of(12, 59)) && country.equals("US")){
                        setText(time.minusHours(12).format(timeFormatter) + " pm");
                    } else if (country.equals("US")){
                        setText(time.format(timeFormatter) + " am");
                    } else {
                        setText(time.format(timeFormatter));
                    }
                }
            }
        });

         */
    }

}

