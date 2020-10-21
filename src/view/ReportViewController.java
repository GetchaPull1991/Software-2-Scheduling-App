package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.*;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportViewController implements Initializable {
    @FXML
    public VBox reportFormVbox;
    @FXML
    public RadioButton reportTypeMonthRadio;
    @FXML
    public Label reportTypeMonthLabel;
    @FXML
    public Label typeComboBoxLabel;
    @FXML
    public ComboBox<String> typeComboBox;
    @FXML
    public Label monthComboBoxLabel;
    @FXML
    public ComboBox<String> monthComboBox;
    @FXML
    public RadioButton scheduleByContactRadio;
    @FXML
    public Label scheduleByContactLabel;
    @FXML
    public Label contactComboBoxLabel;
    @FXML
    public ComboBox<String> contactComboBox;
    @FXML
    public RadioButton scheduleByCustomerRadio;
    @FXML
    public Label scheduleByCustomerLabel;
    @FXML
    public Label customerComboBoxLabel;
    @FXML
    public ComboBox<Integer> customerComboBox;
    @FXML
    public Button generateReportButton;
    @FXML
    public TableView<Appointment> appointmentDataTable;
    @FXML
    public Label appointmentTablePlaceholder;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    public TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML
    public TableColumn<Appointment, String> appointmentDescriptionColumn;
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
    public TableColumn<Appointment, Integer> appointmentCustomerIDColumn;
    @FXML
    public Label errorLabel;

    //Create date and time formatter for display
    DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter usCanadaDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    DateTimeFormatter franceUKFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //Get the users country
    String country = Locale.getDefault().getCountry();

    //Variable for language resources
    public ResourceBundle resourceBundle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEventListeners();
        setCellFactories();
        createToggleGroup();
        setUserLanguage();
        populateComboBoxes();
    }

    /**Set the user language for all text in the view*/
    private void setUserLanguage(){
        //Get the resource for the users Locale
        resourceBundle = ResourceBundle.getBundle("resources.ReportView", Locale.getDefault());

        //Set labels
        reportTypeMonthLabel.setText(resourceBundle.getString("generateTypeMonthLabel"));
        typeComboBoxLabel.setText(resourceBundle.getString("typeLabel"));
        monthComboBoxLabel.setText(resourceBundle.getString("monthLabel"));
        scheduleByContactLabel.setText(resourceBundle.getString("generateContactScheduleLabel"));
        contactComboBoxLabel.setText(resourceBundle.getString("contactNameLabel"));
        scheduleByCustomerLabel.setText(resourceBundle.getString("generateCustomerLabel"));
        customerComboBoxLabel.setText(resourceBundle.getString("customerIDLabel"));

        //Set Button
        generateReportButton.setText(resourceBundle.getString("generateButton"));

        //Table Columns
        appointmentIDColumn.setText(resourceBundle.getString("appointmentIDColumn"));
        appointmentCustomerIDColumn.setText(resourceBundle.getString("customerIDColumn"));
        appointmentTitleColumn.setText(resourceBundle.getString("titleColumn"));
        appointmentTypeColumn.setText(resourceBundle.getString("typeColumn"));
        appointmentStartDateColumn.setText(resourceBundle.getString("startDateColumn"));
        appointmentStartTimeColumn.setText(resourceBundle.getString("startTimeColumn"));
        appointmentEndDateColumn.setText(resourceBundle.getString("endDateColumn"));
        appointmentEndTimeColumn.setText(resourceBundle.getString("endTimeColumn"));
        appointmentDescriptionColumn.setText(resourceBundle.getString("descriptionColumn"));
        appointmentTablePlaceholder.setText(resourceBundle.getString("tablePlaceholder"));

    }

    /**Creat toggle group for radio buttons*/
    private void createToggleGroup(){
        ToggleGroup reportSelectionRadioGroup = new ToggleGroup();
        reportTypeMonthRadio.setToggleGroup(reportSelectionRadioGroup);
        scheduleByCustomerRadio.setToggleGroup(reportSelectionRadioGroup);
        scheduleByContactRadio.setToggleGroup(reportSelectionRadioGroup);
        reportTypeMonthRadio.setSelected(true);

    }

    /**Set the event listeners for view*/
    private void setEventListeners(){

        reportTypeMonthRadio.setOnAction(actionEvent -> {
            contactComboBox.setDisable(true);
            customerComboBox.setDisable(true);
            typeComboBox.setDisable(false);
            monthComboBox.setDisable(false);
        });

        scheduleByCustomerRadio.setOnAction(actionEvent -> {
            contactComboBox.setDisable(true);
            typeComboBox.setDisable(true);
            monthComboBox.setDisable(true);
            customerComboBox.setDisable(false);
        });

        scheduleByContactRadio.setOnAction(actionEvent -> {
            typeComboBox.setDisable(true);
            monthComboBox.setDisable(true);
            customerComboBox.setDisable(true);
            contactComboBox.setDisable(false);
        });

        generateReportButton.setOnAction(actionEvent -> {
            if (scheduleByContactRadio.isSelected()){
                getContactAppointments();
            } else if (scheduleByCustomerRadio.isSelected()){
                getCustomerAppointments();
            } else if (reportTypeMonthRadio.isSelected()){
                getTypeMonthAppointments();
            }
        });


    }

    /**Populate the combo boxes in the view*/
    private void populateComboBoxes(){

        //Create list of months and set combo box
        ObservableList<String> monthsOfYear = FXCollections.observableArrayList(Arrays.asList(new DateFormatSymbols().getMonths()));

        //Remove the empty selection at the end of the list
        monthsOfYear.remove(monthsOfYear.size() - 1);

        //Set the months to the combo box
        monthComboBox.setItems(monthsOfYear);

        //Set type combo
        typeComboBox.setItems(FXCollections.observableArrayList(resourceBundle.getString("debriefType"),
                                                                resourceBundle.getString("newCustomerType"),
                                                                resourceBundle.getString("salesInformationType"),
                                                                resourceBundle.getString("productDesignType")));

        //Populate contacts combo box
        ObservableList<String> contacts = FXCollections.observableArrayList();
        for (Contact contact : Database.getContacts()){
            contacts.add(contact.getName());
        }

        contactComboBox.setItems(contacts);

        //Populate customer combo box
        ObservableList<Integer> availableCustomerIDs = FXCollections.observableArrayList();
        for (Customer customer : Database.getAllCustomers()){
            availableCustomerIDs.add(customer.getCustomerID());
        }

        //Set the combo box with the available customers
        customerComboBox.setItems(availableCustomerIDs);

    }

    /**Retrieve appointments by month and type and display in the appointment table*/
    private void getTypeMonthAppointments(){
        if (InputValidator.validateReportView(this)){
            appointmentDataTable.setItems(Database.getTypeMonthAppointments(typeComboBox.getValue(), monthComboBox.getSelectionModel().getSelectedIndex()));
        }
    }

    /**Retrieve appointments by Customer ID and display in the appointment table*/
    private void getCustomerAppointments(){
        if (InputValidator.validateReportView(this)){
            appointmentDataTable.setItems(Database.getCustomerAppointments(customerComboBox.getValue()));
        }
    }

    /**Retrieve appointments by Contact name and display in the appointment table*/
    private void getContactAppointments(){
        if (InputValidator.validateReportView(this)){
            appointmentDataTable.setItems(Database.getContactAppointments(contactComboBox.getValue()));
        }
    }

    /**Set the cell factories for the appointment table*/
    private void setCellFactories(){


        //Set row factory to change colors of table rows
        appointmentDataTable.setRowFactory(row -> new TableRow<>() {
            @Override
            protected void updateItem(Appointment appointment, boolean empty) {

                //No style if row is null
                super.updateItem(appointment, empty);
                if (appointment == null | empty) {
                    setStyle("");

                    //If the appointment start time has already passes, row background is red
                } else if (LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime()).compareTo(LocalDateTime.now()) < 0) {
                    setStyle("-fx-background-color: #fc3426;");
                    //If the appointment start time is within the next hour, row background is yellow
                } else if (LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime()).isAfter(LocalDateTime.now())
                        && LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime()).isBefore(LocalDateTime.now().plusHours(1))) {
                    setStyle("-fx-background-color: #fce803;");
                    //If appointment is on the same day, row background is green
                } else if (LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime()).isAfter(LocalDateTime.now())
                        && LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime()).isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0)))) {
                    setStyle("-fx-background-color: #00db16;");
                } else {
                    setStyle("");
                }
            }
        });

        //Set cell factories for appointment data table
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //Set start date factory and format date for display based on country
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
                    } else {
                        setText(date.format(franceUKFormat));
                    }
                }
            }
        });

        //Set end date factory and format date for display based on country
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
                    } else {
                        setText(date.format(franceUKFormat));
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
    }

}




