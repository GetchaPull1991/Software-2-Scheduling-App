package view;

import javafx.application.Platform;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

/** Class to manage all Appointment Form UI Functionality*/
public class AppointmentFormController implements Initializable {
    @FXML
    public Label weeklyTablePlaceholderLabel;
    @FXML
    public Label monthlyTablePlaceholderLabel;
    @FXML
    public Label appointmentDescriptionLabel;
    @FXML
    public Label appointmentEndTimeLabel;
    @FXML
    public Label appointmentEndDateLabel;
    @FXML
    public Label appointmentStartTimeLabel;
    @FXML
    public Label appointmentStartDateLabel;
    @FXML
    public Label appointmentTypeLabel;
    @FXML
    public Label appointmentLocationLabel;
    @FXML
    public Label appointmentUserLabel;
    @FXML
    public Label appointmentTitleLabel;
    @FXML
    public Label appointmentCustomerIDLabel;
    @FXML
    public Label appointmentContactLabel;
    @FXML
    public Label appointmentIDLabel;
    @FXML
    public TextField appointmentIDTextField;
    @FXML
    public ComboBox<String> appointmentContactComboBox;
    @FXML
    public TextField appointmentTitleTextField;
    @FXML
    public  TextField appointmentUserTextField;
    @FXML
    public TextField appointmentLocationTextField;
    @FXML
    public DatePicker appointmentStartDatepicker;
    @FXML
    public DatePicker appointmentEndDatepicker;
    @FXML
    public TextArea appointmentDescriptionTextArea;
    @FXML
    public  Label errorLabel;
    @FXML
    public Button appointmentDeleteButton;
    @FXML
    public Button appointmentUpdateButton;
    @FXML
    public Button addAppointmentButton;
    @FXML
    public ComboBox<String> appointmentTypeComboBox;
    @FXML
    public ComboBox<String> appointmentCustomerIDComboBox;
    @FXML
    public ComboBox<String> startTimeComboBox;
    @FXML
    public ComboBox<String> endTimeComboBox;
    @FXML
    public GridPane appointmentFormGridPane;
    @FXML
    public BorderPane mainUIBorderPane;
    @FXML
    public VBox sideMenuVBox;
    @FXML
    public Button appointmentViewMenuButton;
    @FXML
    public Button customerViewMenuButton;
    @FXML
    public Button reportViewMenuButton;
    @FXML
    public Label currentTimeZoneLabel;
    @FXML
    public Label currentLocalTimeLabel;
    @FXML
    public Label currentUserLabel;
    @FXML
    public Button logoutMenuButton;
    @FXML
    public BorderPane contentBorderPane;
    @FXML
    public Label formLabel;
    @FXML
    public Button menuControlButton;
    @FXML
    public Label currentESTLabel;
    @FXML
    public TableView<Appointment> allAppointmentDataTable;
    @FXML
    public Label allTablePlaceholderLabel;
    @FXML
    public TableColumn<Appointment, Integer> allAppointmentIDColumn;
    @FXML
    public TableColumn<Appointment, String> allAppointmentTitleColumn;
    @FXML
    public TableColumn<Appointment, String> allAppointmentDescriptionColumn;
    @FXML
    public TableColumn<Appointment, String> allAppointmentLocationColumn;
    @FXML
    public TableColumn<Appointment, String> allAppointmentContactColumn;
    @FXML
    public TableColumn<Appointment, LocalDate> allAppointmentStartDateColumn;
    @FXML
    public TableColumn<Appointment, LocalTime> allAppointmentStartTimeColumn;
    @FXML
    public TableColumn<Appointment, LocalDate> allAppointmentEndDateColumn;
    @FXML
    public TableColumn<Appointment, LocalTime> allAppointmentEndTimeColumn;
    @FXML
    public TableColumn<Appointment, String> allAppointmentTypeColumn;
    @FXML
    public TableColumn<Appointment, Integer> allAppointmentCustomerIDColumn;
    @FXML
    public TableView<Appointment> monthlyAppointmentDataTable;
    @FXML
    public TableColumn<Appointment, Integer> monthlyAppointmentIDColumn;
    @FXML
    public TableColumn<Appointment, String> monthlyAppointmentTitleColumn;
    @FXML
    public TableColumn<Appointment, String> monthlyAppointmentDescriptionColumn;
    @FXML
    public TableColumn<Appointment, String> monthlyAppointmentLocationColumn;
    @FXML
    public TableColumn<Appointment, String> monthlyAppointmentContactColumn;
    @FXML
    public TableColumn<Appointment, LocalDate> monthlyAppointmentStartDateColumn;
    @FXML
    public TableColumn<Appointment, LocalTime> monthlyAppointmentStartTimeColumn;
    @FXML
    public TableColumn<Appointment, LocalDate> monthlyAppointmentEndDateColumn;
    @FXML
    public TableColumn<Appointment, LocalTime> monthlyAppointmentEndTimeColumn;
    @FXML
    public TableColumn<Appointment, String> monthlyAppointmentTypeColumn;
    @FXML
    public TableColumn<Appointment, Integer> monthlyAppointmentCustomerIDColumn;
    @FXML
    public TableView<Appointment> weeklyAppointmentDataTable;
    @FXML
    public TableColumn<Appointment, Integer> weeklyAppointmentIDColumn;
    @FXML
    public TableColumn<Appointment, String> weeklyAppointmentTitleColumn;
    @FXML
    public TableColumn<Appointment, String> weeklyAppointmentDescriptionColumn;
    @FXML
    public TableColumn<Appointment, String> weeklyAppointmentLocationColumn;
    @FXML
    public TableColumn<Appointment, String> weeklyAppointmentContactColumn;
    @FXML
    public TableColumn<Appointment, LocalDate> weeklyAppointmentStartDateColumn;
    @FXML
    public TableColumn<Appointment, LocalTime> weeklyAppointmentStartTimeColumn;
    @FXML
    public TableColumn<Appointment, LocalDate> weeklyAppointmentEndDateColumn;
    @FXML
    public TableColumn<Appointment, LocalTime> weeklyAppointmentEndTimeColumn;
    @FXML
    public TableColumn<Appointment, String> weeklyAppointmentTypeColumn;
    @FXML
    public TableColumn<Appointment, Integer> weeklyAppointmentCustomerIDColumn;
    @FXML
    public TabPane appointmentTabPane;
    @FXML
    public Tab weeklyTab;
    @FXML
    public Tab monthlyTab;
    @FXML
    public Tab allAppointmentsTab;
    @FXML
    public Label legendLabel;
    @FXML
    public Label overdueAppointmentLabel;
    @FXML
    public Label nextHourAppointmentLabel;
    @FXML
    public Label appointmentTodayLabel;

    //Store Customer form view
    GridPane customerFormView;

    //Store the report view
    VBox reportView;

    //Store Current User and allow access in all controllers
    public static User currentUser;

    //Store the primary stage of the application
    public static Stage primaryStage;

    //Resource bundle for language
    public ResourceBundle resourceBundle;


    //Information alert
    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);

    //Confirmation alert
    Alert confirmationAlert = new Alert (Alert.AlertType.CONFIRMATION);

    //Store selected appointments for updating and deleting
    Appointment selectedAppointment;

    //Create date and time formatters for display
    DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter usCanadaDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    DateTimeFormatter franceUkFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dateHourMinuteFormatter;

    //Get the users country
    String country = Locale.getDefault().getCountry();

    //Create lists to store the start and end times for the appointment
    ObservableList<String> startTimes = FXCollections.observableArrayList();
    ObservableList<String> endTimes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserLanguage();
        populateStartTimes();
        populateEndTimes();
        populateComboBoxes();
        setEventListeners();
        setCellFactories();
        setTableData();

        //Check for upcoming appointments after the scene has been shown with method reference
        Platform.runLater(this::checkUpcomingAppointments);
    }

    /** Populate the tables with data*/
    private void setTableData(){
        allAppointmentDataTable.setItems(Database.getAllAppointments());
        monthlyAppointmentDataTable.setItems(Database.getMonthAppointments());
        weeklyAppointmentDataTable.setItems(Database.getWeekAppointments());
    }

    /** Set the application language according to the users locale*/
    private void setUserLanguage(){

        //Get resource bundle
        resourceBundle = ResourceBundle.getBundle("resources.AppointmentForm", Locale.getDefault());

        //Menu Buttons
        appointmentViewMenuButton.setText(resourceBundle.getString("appointmentsButtonLabel"));
        customerViewMenuButton.setText(resourceBundle.getString("customersButtonLabel"));
        reportViewMenuButton.setText(resourceBundle.getString("reportsButtonLabel"));
        logoutMenuButton.setText(resourceBundle.getString("logoutButtonLabel"));
        menuControlButton.setText(resourceBundle.getString("hideMenuButtonLabel"));

        //Legend Labels
        legendLabel.setText(resourceBundle.getString("legendLabel"));
        overdueAppointmentLabel.setText(resourceBundle.getString("overdueAppointmentLabel"));
        nextHourAppointmentLabel.setText(resourceBundle.getString("nextHourAppointmentLabel"));
        appointmentTodayLabel.setText(resourceBundle.getString("appointmentTodayLabel"));

        //Form Labels
        formLabel.setText(resourceBundle.getString("formLabelAppointments"));
        appointmentIDLabel.setText(resourceBundle.getString("appointmentIDFieldLabel") + ":");
        appointmentIDTextField.setText(resourceBundle.getString("appointmentIDFieldDefault"));
        appointmentContactLabel.setText(resourceBundle.getString("contactComboLabel") + ":");
        appointmentCustomerIDLabel.setText(resourceBundle.getString("customerIDComboLabel") + ":");
        appointmentTitleLabel.setText(resourceBundle.getString("titleFieldLabel") + ":");
        appointmentUserLabel.setText(resourceBundle.getString("userFieldLabel") + ":");
        appointmentLocationLabel.setText(resourceBundle.getString("locationFieldLabel") + ":");
        appointmentTypeLabel.setText(resourceBundle.getString("typeFieldLabel") + ":");
        appointmentStartDateLabel.setText(resourceBundle.getString("startDatePickerLabel") + ":");
        appointmentStartTimeLabel.setText(resourceBundle.getString("startTimeComboLabel") + ":");
        appointmentEndDateLabel.setText(resourceBundle.getString("endDatePickerLabel") + ":");
        appointmentEndTimeLabel.setText(resourceBundle.getString("endTimeComboLabel") + ":");
        appointmentDescriptionLabel.setText(resourceBundle.getString("descriptionFieldLabel") + ":");

        //Form Button Labels
        addAppointmentButton.setText(resourceBundle.getString("addAppointmentButtonLabel"));
        appointmentDeleteButton.setText(resourceBundle.getString("deleteAppointmentButtonLabel"));
        appointmentUpdateButton.setText(resourceBundle.getString("updateAppointmentButtonLabel"));

        //Tabpane Labels
        monthlyTab.setText(resourceBundle.getString("monthlyTabLabel"));
        weeklyTab.setText(resourceBundle.getString("weeklyTabLabel"));

        //Table Column labels

        //Monthly Appointment Table
        monthlyAppointmentIDColumn.setText(resourceBundle.getString("appointmentIDFieldLabel"));
        monthlyAppointmentContactColumn.setText(resourceBundle.getString("contactComboLabel"));
        monthlyAppointmentCustomerIDColumn.setText(resourceBundle.getString("customerIDComboLabel"));
        monthlyAppointmentTitleColumn.setText(resourceBundle.getString("titleFieldLabel"));
        monthlyAppointmentLocationColumn.setText(resourceBundle.getString("locationFieldLabel"));
        monthlyAppointmentTypeColumn.setText(resourceBundle.getString("typeFieldLabel"));
        monthlyAppointmentStartDateColumn.setText(resourceBundle.getString("startDatePickerLabel"));
        monthlyAppointmentStartTimeColumn.setText(resourceBundle.getString("startTimeComboLabel"));
        monthlyAppointmentEndDateColumn.setText(resourceBundle.getString("endDatePickerLabel"));
        monthlyAppointmentEndTimeColumn.setText(resourceBundle.getString("endTimeComboLabel"));
        monthlyAppointmentDescriptionColumn.setText(resourceBundle.getString("descriptionFieldLabel"));
        monthlyTablePlaceholderLabel.setText(resourceBundle.getString("appointmentTablePlaceholder"));

        //Weekly Appointment Table
        weeklyAppointmentIDColumn.setText(resourceBundle.getString("appointmentIDFieldLabel"));
        weeklyAppointmentContactColumn.setText(resourceBundle.getString("contactComboLabel"));
        weeklyAppointmentCustomerIDColumn.setText(resourceBundle.getString("customerIDComboLabel"));
        weeklyAppointmentTitleColumn.setText(resourceBundle.getString("titleFieldLabel"));
        weeklyAppointmentLocationColumn.setText(resourceBundle.getString("locationFieldLabel"));
        weeklyAppointmentTypeColumn.setText(resourceBundle.getString("typeFieldLabel"));
        weeklyAppointmentStartDateColumn.setText(resourceBundle.getString("startDatePickerLabel"));
        weeklyAppointmentStartTimeColumn.setText(resourceBundle.getString("startTimeComboLabel"));
        weeklyAppointmentEndDateColumn.setText(resourceBundle.getString("endDatePickerLabel"));
        weeklyAppointmentEndTimeColumn.setText(resourceBundle.getString("endTimeComboLabel"));
        weeklyAppointmentDescriptionColumn.setText(resourceBundle.getString("descriptionFieldLabel"));
        weeklyTablePlaceholderLabel.setText(resourceBundle.getString("appointmentTablePlaceholder"));

        //All Appointment Table
        allAppointmentIDColumn.setText(resourceBundle.getString("appointmentIDFieldLabel"));
        allAppointmentContactColumn.setText(resourceBundle.getString("contactComboLabel"));
        allAppointmentCustomerIDColumn.setText(resourceBundle.getString("customerIDComboLabel"));
        allAppointmentTitleColumn.setText(resourceBundle.getString("titleFieldLabel"));
        allAppointmentLocationColumn.setText(resourceBundle.getString("locationFieldLabel"));
        allAppointmentTypeColumn.setText(resourceBundle.getString("typeFieldLabel"));
        allAppointmentStartDateColumn.setText(resourceBundle.getString("startDatePickerLabel"));
        allAppointmentStartTimeColumn.setText(resourceBundle.getString("startTimeComboLabel"));
        allAppointmentEndDateColumn.setText(resourceBundle.getString("endDatePickerLabel"));
        allAppointmentEndTimeColumn.setText(resourceBundle.getString("endTimeComboLabel"));
        allAppointmentDescriptionColumn.setText(resourceBundle.getString("descriptionFieldLabel"));
        allTablePlaceholderLabel.setText(resourceBundle.getString("appointmentTablePlaceholder"));

        //Set date time formatter
        dateHourMinuteFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy '"+ resourceBundle.getString("atLabel") + "' hh:mm");
    }

    /** Add appointment to table and database*/
    private void addAppointment(){

        //Check if user input is valid
        if (InputValidator.validateAppointmentForm(this)){

            //Generate unique appointment id
            Random random = new Random();
            int id = random.nextInt(1000);
            while (Database.getAppointmentByID(id) != null) {
                id = random.nextInt();
            }

            //Get english resource bundle
            ResourceBundle resourceBundleEn = ResourceBundle.getBundle("resources.AppointmentForm", Locale.US);
            ObservableList<String> typesInEnglish = FXCollections.observableArrayList(resourceBundleEn.getString("debriefType"),
                                                                                    resourceBundleEn.getString("newCustomerType"),
                                                                                    resourceBundleEn.getString("salesInformationType"),
                                                                                    resourceBundleEn.getString("productDesignType"));

            /*
            Create new appointment
            Types converted to english for database storage
             */
            Appointment appointment = new Appointment(appointmentContactComboBox.getValue(),
                                                    id,
                                                    appointmentTitleTextField.getText(),
                                                    appointmentDescriptionTextArea.getText(),
                                                    appointmentLocationTextField.getText(),
                                                    typesInEnglish.get(appointmentTypeComboBox.getSelectionModel().getSelectedIndex()),
                                                    appointmentStartDatepicker.getValue(),
                                                    convertStringToLocalTime(startTimeComboBox.getValue()),
                                                    appointmentEndDatepicker.getValue(),
                                                    convertStringToLocalTime(endTimeComboBox.getValue()),
                                                    Integer.parseInt(appointmentCustomerIDComboBox.getValue()), currentUser.getUserName());

            //Reset the appointment form
            resetAppointmentForm();

            //Add new appointment to database
            Database.addAppointment(appointment, currentUser);

            //Update the appointment tables
            setTableData();

        }
    }

    /**
     * Update selected appointment
     * @throws ParseException if the customer id cannot be parsed
     */
    private void updateAppointment() throws ParseException {

        //Re-enable tab pane
        appointmentTabPane.setDisable(false);

        //Update appointment information
        selectedAppointment.setContactName(appointmentContactComboBox.getValue());
        selectedAppointment.setCustomerID(Integer.parseInt(appointmentCustomerIDComboBox.getValue()));
        selectedAppointment.setTitle(appointmentTitleTextField.getText());
        selectedAppointment.setUser(currentUser.getUserName());
        selectedAppointment.setLocation(appointmentLocationTextField.getText());
        selectedAppointment.setType(appointmentTypeComboBox.getValue());
        selectedAppointment.setDescription(appointmentDescriptionTextArea.getText());

        //Update appointment time information
        selectedAppointment.setStartDate(appointmentStartDatepicker.getValue());
        selectedAppointment.setStartTime(convertStringToLocalTime(startTimeComboBox.getValue()));
        selectedAppointment.setEndDate(appointmentEndDatepicker.getValue());
        selectedAppointment.setEndTime(convertStringToLocalTime(endTimeComboBox.getValue()));


        //Update appointment in database
        Database.updateAppointment(selectedAppointment, currentUser);

        //Reset form
        resetAppointmentForm();

        //Update the appointment tables
        setTableData();
    }

    /** Delete appointment from table and database
     *  Lambda used to handle the alert response without initializing ButtonType
     * */
    private void deleteAppointment(){

        //Check if the monthly or weekly tab is open
        if (appointmentTabPane.getSelectionModel().getSelectedItem().equals(monthlyTab)){
            selectedAppointment = monthlyAppointmentDataTable.getSelectionModel().getSelectedItem();
        } else if (appointmentTabPane.getSelectionModel().getSelectedItem().equals(weeklyTab)){
            selectedAppointment = weeklyAppointmentDataTable.getSelectionModel().getSelectedItem();
        } else {
            selectedAppointment = allAppointmentDataTable.getSelectionModel().getSelectedItem();
        }

        //Check if a selection made
        if (selectedAppointment != null) {

            //Set custom confirmation alert with Appointment ID and Appointment Type
            confirmationAlert.setContentText(resourceBundle.getString("deleteAppointmentConfirmationMessage") +
                                            "\n" + resourceBundle.getString("appointmentIDFieldLabel") + ": " + selectedAppointment.getAppointmentID() +
                                            "\n" + resourceBundle.getString("typeFieldLabel") + ": " + selectedAppointment.getType());

            //Set confirmation title and header
            confirmationAlert.setTitle(resourceBundle.getString("deleteAppointmentConfirmationHeader"));
            confirmationAlert.setHeaderText(resourceBundle.getString("deleteAppointmentConfirmationHeader"));

            //Wait for user response
            confirmationAlert.showAndWait().ifPresent(response ->{
                if (response.equals(ButtonType.OK)){

                    //Remove appointment from database
                    Database.removeAppointment(selectedAppointment);

                    //Update tables
                    setTableData();
                }
            });
        } else {

            //If no selection made, display error in UI
            errorLabel.setText(resourceBundle.getString("noAppointmentToDeleteError"));
        }
    }


    /** Check for appointments scheduled within 15 minutes of the current time and alert the user*/
    private void checkUpcomingAppointments(){

        //Get any appointments within 15 minutes
        Appointment upcomingAppointment = Database.getAppointmentWithin15Min();

        //Set information alert title and header
        informationAlert.setHeaderText(resourceBundle.getString("upcomingAppointmentInfoHeader"));
        informationAlert.setTitle(resourceBundle.getString("upcomingAppointmentInfoHeader"));

        //If there are any appointments
        if (upcomingAppointment != null){

            //Display an information alert containing the appointment information
            informationAlert.setContentText(
                            resourceBundle.getString("upcomingAppointmentInfoMessage") +
                            "\n\n" + resourceBundle.getString("appointmentIDFieldLabel") +
                            ": " + upcomingAppointment.getAppointmentID() +
                            "\n" + resourceBundle.getString("timeslotLabel") +
                             LocalDateTime.of(upcomingAppointment.getStartDate(), upcomingAppointment.getStartTime()).format(dateHourMinuteFormatter) +
                            resourceBundle.getString("untilLabel") +
                            LocalDateTime.of(upcomingAppointment.getEndDate(), upcomingAppointment.getEndTime()).format(dateHourMinuteFormatter));

        } else {
            //If there are no appointments within fifteen minutes display information alert indicating this
            informationAlert.setContentText(resourceBundle.getString("noUpcomingAppointmentsInfoMessage"));
            informationAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        }
        //Wait for user response
        informationAlert.showAndWait();
    }

    /** Set cell factories for appointment data table
     * Lambdas used for row factories to avoid instantiating Table Row Objects
     * Lambdas used for cell factories to avoid instantiating Table Cell Objects
     * Lambdas used for day cell factories to avoid instantiating Date Cell Objects
     */
    private void setCellFactories(){
        /*
        Monthly Appointment Table
         */
        //Set row factory to change colors of table rows
        monthlyAppointmentDataTable.setRowFactory(row -> new TableRow<>() {
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

        //Set cell factories for monthly appointment data table
        monthlyAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        monthlyAppointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        monthlyAppointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        monthlyAppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthlyAppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthlyAppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthlyAppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //Set start date factory and format date for display
        monthlyAppointmentStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        monthlyAppointmentStartDateColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty){
                    setText(null);
                } else {
                    if (country.equals("US") || country.equals("CA")) {
                        setText(date.format(usCanadaDateFormat));
                    } else {
                        setText(date.format(franceUkFormat));
                    }
                }
            }
        });

        //Set end date factory and format date for display
        monthlyAppointmentEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        monthlyAppointmentEndDateColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty){
                    setText(null);
                } else {
                    if (country.equals("US") || country.equals("CA")) {
                        setText(date.format(usCanadaDateFormat));
                    } else {
                        setText(date.format(franceUkFormat));
                    }
                }
            }
        });

        //Set start time factory and format time for display
        monthlyAppointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        monthlyAppointmentStartTimeColumn.setCellFactory(col -> new TableCell<>(){
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
        monthlyAppointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        monthlyAppointmentEndTimeColumn.setCellFactory(col -> new TableCell<>(){
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

        /*
        Weekly Appointment Table
         */
        //Set row factory to change colors of table rows
        weeklyAppointmentDataTable.setRowFactory(row -> new TableRow<>() {
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

        //Set cell factories for weekly appointment data table
        weeklyAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        weeklyAppointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        weeklyAppointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        weeklyAppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        weeklyAppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        weeklyAppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        weeklyAppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //Set start date factory and format date for display
        weeklyAppointmentStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        weeklyAppointmentStartDateColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty){
                    setText(null);
                } else {
                    if (country.equals("US") || country.equals("CA")) {
                        setText(date.format(usCanadaDateFormat));
                    } else {
                        setText(date.format(franceUkFormat));
                    }
                }
            }
        });

        //Set end date factory and format date for display
        weeklyAppointmentEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        weeklyAppointmentEndDateColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty){
                    setText(null);
                } else {
                    if (country.equals("US") || country.equals("CA")) {
                        setText(date.format(usCanadaDateFormat));
                    } else {
                        setText(date.format(franceUkFormat));
                    }
                }
            }
        });

        //Set start time factory and format time for display
        weeklyAppointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        weeklyAppointmentStartTimeColumn.setCellFactory(col -> new TableCell<>(){
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
        weeklyAppointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        weeklyAppointmentEndTimeColumn.setCellFactory(col -> new TableCell<>(){
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

        /*
        All appointments table
         */
        //Set row factory to change colors of table rows
        allAppointmentDataTable.setRowFactory(row -> new TableRow<>() {
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

        //Set cell factories for monthly appointment data table
        allAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        allAppointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        allAppointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        allAppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        allAppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        allAppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        allAppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //Set start date factory and format date for display
        allAppointmentStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        allAppointmentStartDateColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty){
                    setText(null);
                } else {
                    if (country.equals("US") || country.equals("CA")) {
                        setText(date.format(usCanadaDateFormat));
                    } else {
                        //Format for France/UK
                        setText(date.format(franceUkFormat));
                    }
                }
            }
        });

        //Set end date factory and format date for display
        allAppointmentEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        allAppointmentEndDateColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty){
                    setText(null);
                } else {
                    if (country.equals("US") || country.equals("CA")) {
                        setText(date.format(usCanadaDateFormat));
                    } else {
                        //Format for France/UK
                        setText(date.format(franceUkFormat));
                    }
                }
            }
        });

        //Set start time factory and format time for display
        allAppointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        allAppointmentStartTimeColumn.setCellFactory(col -> new TableCell<>(){
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
        allAppointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        allAppointmentEndTimeColumn.setCellFactory(col -> new TableCell<>(){
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

        /*
        Datepicker
         */
        //Set day cell factory for start date picker
        appointmentStartDatepicker.setDayCellFactory(cell -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);

                //Get the current local date
                LocalDate today = LocalDate.now();

                /*
                Disable cells that are before the current day or
                after 10pm/2300 of the current day
                 */
                setDisable(empty || date.compareTo(today) < 0
                                || LocalDateTime.of(date, LocalTime.of(22, 0 )).compareTo(LocalDateTime.now()) < 0);
            }
        });
    }

    /** Set the event listeners
     *  Lambdas used for action events to avoid instantiating Action Event Objects
     *  Lambdas used for alert response to avoid instantiating Button Type Objects
     * */
    private void setEventListeners(){

        /*
        Handle appointment changes
         */
        //Add new appointment
        addAppointmentButton.setOnAction(actionEvent -> addAppointment());

        //Cancel appointment
        appointmentDeleteButton.setOnAction(actionEvent -> {

            //Check Which tab selected in the tab pane
            if (appointmentTabPane.getSelectionModel().getSelectedItem().equals(monthlyTab)){

                //Get the monthly appointment table selection
                selectedAppointment = monthlyAppointmentDataTable.getSelectionModel().getSelectedItem();
            } else if (appointmentTabPane.getSelectionModel().getSelectedItem().equals(weeklyTab)){

                //Get the weekly appointment table selection
                selectedAppointment = weeklyAppointmentDataTable.getSelectionModel().getSelectedItem();
            } else {

                //Get the all appointment table selection
                selectedAppointment = allAppointmentDataTable.getSelectionModel().getSelectedItem();
            }

            //Display an error if no appointment selected
            if (selectedAppointment == null){
                errorLabel.setText(resourceBundle.getString("noAppointmentToDeleteError"));
                errorLabel.setVisible(true);
            } else {
                //Hide error label
                errorLabel.setVisible(false);

                //Delete the appointment
                deleteAppointment();
        }});

        //Update appointment
        appointmentUpdateButton.setOnAction(actionEvent -> {

            //Check Which tab selected in the tab pane
            if (appointmentTabPane.getSelectionModel().getSelectedItem().equals(monthlyTab)){

                //Get the monthly appointment table selection
                selectedAppointment = monthlyAppointmentDataTable.getSelectionModel().getSelectedItem();
            } else if (appointmentTabPane.getSelectionModel().getSelectedItem().equals(weeklyTab)){

                //Get the weekly appointment table selection
                selectedAppointment = weeklyAppointmentDataTable.getSelectionModel().getSelectedItem();
            } else {

                //Get the all appointment table selection
                selectedAppointment = allAppointmentDataTable.getSelectionModel().getSelectedItem();
            }

            //Display an error if no appointment selected
            if (selectedAppointment == null){
                errorLabel.setText(resourceBundle.getString("noAppointmentToUpdateError"));
                errorLabel.setVisible(true);
            } else {
                //Hide error label
                errorLabel.setVisible(false);

                //Populate the form with the selected Appointment information
                populateFormForUpdate();

                //Set the text and event for the add appointment button
                addAppointmentButton.setText(resourceBundle.getString("saveChangesButtonLabel"));
                addAppointmentButton.setOnAction(actionEvent1 -> {
                    try {
                        //Validate the user input
                        if (InputValidator.validateAppointmentForm(this)) {
                            updateAppointment();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        /*
        Handle View Changes
         */

        //Open appointment view
        appointmentViewMenuButton.setOnAction(actionEvent -> {
            formLabel.setText(resourceBundle.getString("formLabelAppointments"));
            contentBorderPane.setCenter(appointmentFormGridPane);
            setTableData();
            populateComboBoxes();
        });

        //Open customer view
        customerViewMenuButton.setOnAction(actionEvent -> {
            formLabel.setText(resourceBundle.getString("formLabelCustomer"));
            try {
                customerFormView = FXMLLoader.load(getClass().getResource("../view/CustomerFormView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            contentBorderPane.setCenter(customerFormView);
        });

        //Open report view
        reportViewMenuButton.setOnAction(actionEvent -> {
            formLabel.setText(resourceBundle.getString("formLabelReports"));
            try {
                reportView = FXMLLoader.load(getClass().getResource("../view/ReportView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            contentBorderPane.setCenter(reportView);
        });

        /*
        Handle Logout
         */

        //Return the user to the login screen
        logoutMenuButton.setOnAction(actionEvent -> {

            //Confirm the user wants to logout
            confirmationAlert.setContentText(resourceBundle.getString("logoutConfirmationMessage"));

            //Set the confirmation alert title and header
            confirmationAlert.setHeaderText(resourceBundle.getString("logoutConfirmationHeader"));
            confirmationAlert.setTitle(resourceBundle.getString("logoutConfirmationHeader"));

            //Wait for user response
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
                    primaryStage.setTitle(controller.resourceBundle.getString("windowTitle"));
                    primaryStage.setScene(loginForm);
                    primaryStage.setResizable(false);
                    Stage stage = (Stage) logoutMenuButton.getScene().getWindow();

                    //Close the current stage and show the primary stage
                    stage.close();
                    primaryStage.show();
                }
            });
        });

        /*
        Handle Side Menu
         */

        //Show or Hide menu when menuControlButton is clicked
        menuControlButton.setOnAction(actionEvent -> {
            if (mainUIBorderPane.getLeft() != null){
                mainUIBorderPane.setLeft(null);
                menuControlButton.setText(resourceBundle.getString("showMenuButtonLabel"));
            } else {
                mainUIBorderPane.setLeft(sideMenuVBox);
                menuControlButton.setText(resourceBundle.getString("hideMenuButtonLabel"));
            }
        });

        /*
        Handle Date and Time Fields
         */

        //Removes any times after the current time from the start time combo box
        appointmentStartDatepicker.setOnAction(actionEvent -> {

            //Repopulate start times
            populateStartTimes();

            //Check if there is a selected start date
            if (appointmentStartDatepicker.getValue() != null) {

                //Set cell factory for end date picker
                appointmentEndDatepicker.setDayCellFactory(cell -> new DateCell() {
                    public void updateItem(LocalDate date, boolean empty){
                        super.updateItem(date, empty);

                        //Disable empty cells
                        setDisable(empty);

                        //If there is a start date selection, disable day cells before start date
                        if (appointmentStartDatepicker.getValue() != null) {
                            setDisable(date.compareTo(appointmentStartDatepicker.getValue()) < 0);
                        }
                    }
                });

                //Store times to be removed
                ObservableList<String> timesToRemove = FXCollections.observableArrayList();

                //Remove all times from the start times list that are before the current time
                for (String time : startTimeComboBox.getItems()) {
                    if (LocalDateTime.of(appointmentStartDatepicker.getValue(), convertStringToLocalTime(time)).isBefore(LocalDateTime.now())) {
                        timesToRemove.add(time);
                    }
                }

                //Remove times
                startTimes.removeAll(timesToRemove);
            }

            //If there are no times available for the current date, display prompt and disable start time combo box
            if (startTimes.size() == 0){
                startTimeComboBox.setPromptText(resourceBundle.getString("noAvailableTimesLabel"));
                startTimeComboBox.setDisable(true);

                //If there are times available for the current date, display prompt and enable start time combo box
            } else {
                startTimeComboBox.setPromptText(resourceBundle.getString("selectStartTimeLabel"));
                startTimeComboBox.setDisable(false);
            }

            //Disable end date and end time if start date changed
            appointmentEndDatepicker.setDisable(true);
            endTimeComboBox.getSelectionModel().clearSelection();
            endTimeComboBox.setDisable(true);

        });

        //Enable end date picker when a selection made in start time combo box
        startTimeComboBox.setOnAction(actionEvent -> {
            appointmentEndDatepicker.setDisable(false);

            /*
            If there is a selection in the appointment end date picker when the start time changed
            the end date picker and end time combo box will be cleared
             */
            if (appointmentEndDatepicker.getValue() != null){
                appointmentEndDatepicker.setValue(null);
                endTimeComboBox.getSelectionModel().clearSelection();
            }
        });



        //Remove any times that are after the start time from the end time combo box
        appointmentEndDatepicker.setOnAction(actionEvent -> {

            //Repopulate end times
            populateEndTimes();

            //Times to be removed
            ObservableList<String> timesToRemove = FXCollections.observableArrayList();

            //Add any times from the combo box that are before the start time plus 15 minutes to the remove list
            if (appointmentEndDatepicker.getValue() != null ) {
                for (String time : endTimeComboBox.getItems()) {
                    if (LocalDateTime.of(appointmentEndDatepicker.getValue(), convertStringToLocalTime(time))
                            .isBefore(LocalDateTime.of(appointmentStartDatepicker.getValue(), convertStringToLocalTime(startTimeComboBox.getValue()).plusMinutes(15)))
                            ||
                            LocalDateTime.of(appointmentEndDatepicker.getValue(), convertStringToLocalTime(time))
                                    .isBefore(LocalDateTime.now())){
                        timesToRemove.add(time);
                    }
                }
            }

            //Remove times
            endTimes.removeAll(timesToRemove);

            //If there are no times available for the current date, display prompt and disable end time combo box
            if (endTimes.size() == 0){
                endTimeComboBox.setPromptText(resourceBundle.getString("noAvailableTimesLabel"));
                endTimeComboBox.setDisable(true);

                //If there are times available for the current date, display prompt and enable end time combo box
            } else {
                endTimeComboBox.setPromptText(resourceBundle.getString("selectEndTimeLabel"));
                endTimeComboBox.setDisable(false);
            }
        });
    }

    /** Reset the appointment form to default values*/
    private void resetAppointmentForm(){

        //Reset fields
        startTimeComboBox.getSelectionModel().clearSelection();
        endTimeComboBox.getSelectionModel().clearSelection();
        appointmentIDTextField.setText(resourceBundle.getString("appointmentIDFieldDefault"));
        appointmentContactComboBox.getSelectionModel().clearSelection();
        appointmentCustomerIDComboBox.getSelectionModel().clearSelection();
        appointmentTitleTextField.clear();
        appointmentLocationTextField.clear();
        appointmentUserTextField.setText(currentUser.getUserName());
        appointmentTypeComboBox.getSelectionModel().clearSelection();
        appointmentStartDatepicker.setValue(null);
        startTimeComboBox.setDisable(true);
        appointmentEndDatepicker.setValue(null);
        appointmentEndDatepicker.setDisable(true);
        endTimeComboBox.setDisable(true);
        appointmentDescriptionTextArea.clear();

        //Reset add appointment button
        addAppointmentButton.setText(resourceBundle.getString("addAppointmentButtonLabel"));
        addAppointmentButton.setOnAction(actionEvent -> addAppointment());

        //Enable the tab pane
        appointmentTabPane.disableProperty().setValue(false);
    }

    /** Populate the appointment form with appointment information for updating*/
    private void populateFormForUpdate() {

        //Check if the monthly or weekly tab is open
        if (appointmentTabPane.getSelectionModel().getSelectedItem().equals(monthlyTab)){
            selectedAppointment = monthlyAppointmentDataTable.getSelectionModel().getSelectedItem();
        } else if (appointmentTabPane.getSelectionModel().getSelectedItem().equals(monthlyTab)) {
            selectedAppointment = weeklyAppointmentDataTable.getSelectionModel().getSelectedItem();
        } else {
            selectedAppointment = allAppointmentDataTable.getSelectionModel().getSelectedItem();
        }


        //Check if selected appointment is null
        //Populate fields with selected appointment data
        if (selectedAppointment != null) {
            appointmentIDTextField.setText(Integer.toString(selectedAppointment.getAppointmentID()));
            appointmentContactComboBox.getSelectionModel().select(selectedAppointment.getContactName());
            appointmentCustomerIDComboBox.getSelectionModel().select(Integer.toString(selectedAppointment.getCustomerID()));
            appointmentTitleTextField.setText(selectedAppointment.getTitle());
            appointmentLocationTextField.setText(selectedAppointment.getLocation());
            appointmentTypeComboBox.getSelectionModel().select(selectedAppointment.getType());
            appointmentStartDatepicker.setValue(selectedAppointment.getStartDate());
            startTimeComboBox.getSelectionModel().select(formatUSTimeString(selectedAppointment.getStartTime()));
            appointmentEndDatepicker.setValue(selectedAppointment.getEndDate());
            endTimeComboBox.getSelectionModel().select(formatUSTimeString(selectedAppointment.getEndTime()));
            appointmentDescriptionTextArea.setText(selectedAppointment.getDescription());
        }

        //Disable tab pane until update is complete
        appointmentTabPane.setDisable(true);
    }

    /** Populate all the combo boxes in the view*/
    private void populateComboBoxes(){

        //Populate contacts combo box
        ObservableList<String> contacts = FXCollections.observableArrayList();
        for (Contact contact : Database.getContacts()){
            contacts.add(contact.getName());
        }

        //Set the combo box with the contacts
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

        //Set the combo box with the available customers
        appointmentCustomerIDComboBox.setItems(availableCustomerIDs);

        //Populate appointment types
        appointmentTypeComboBox.setItems(FXCollections.observableArrayList(resourceBundle.getString("debriefType"),
                                                                                    resourceBundle.getString("newCustomerType"),
                                                                                    resourceBundle.getString("salesInformationType"),
                                                                                    resourceBundle.getString("productDesignType")));
    }

    /**Populate the start time combo box with times within business hours*/
    public void populateStartTimes(){
        //Clear the start time list
        startTimes.clear();

        //Start the time at 8 am local time
        LocalTime businessHourStart = LocalTime.of(8, 0);

        //End the time at 10 pm local time
        LocalTime businessHourEnd = LocalTime.of(22,15);

        //Populate start times with 15 min slots during business hours
        while (!businessHourStart.equals(businessHourEnd)) {

            //Add time
          startTimes.add(formatUSTimeString(businessHourStart));

            //Increment start time
            businessHourStart = businessHourStart.plusMinutes(15);
        }

        //Set the combo box values
        startTimeComboBox.setItems(startTimes);
    }

    /**Populate the end time combo box with times within business hours*/
    public void populateEndTimes(){
        //Clear the end time list
        endTimes.clear();

        //Start the time at 8 am local time
        LocalTime businessHourStart = LocalTime.of(8, 0);

        //End the time at 10 pm local time
        LocalTime businessHourEnd = LocalTime.of(22,15);

        //Populate start times with 15 min slots during business hours
        while (!businessHourStart.equals(businessHourEnd)) {

            //Add time
            endTimes.add(formatUSTimeString(businessHourStart));

            //Increment start time
            businessHourStart = businessHourStart.plusMinutes(15);
        }

        //Set the combo box values
        endTimeComboBox.setItems(endTimes);
    }

    /**
     * Convert a string time value in HH:mm format to a LocalTime object
     * @param time the string time value to convert
     * @return the LocalTime object
     */
    public LocalTime convertStringToLocalTime (String time){
        return LocalTime.of(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)));
    }

    /**
     * Format the time with am and pm if in the us
     * @param time the time to format
     * @return the formatted time
     */
    private String formatUSTimeString(LocalTime time){

        //Store formatted time
        String formattedTime;

        //Add am and pm if country is us
        if (country.equals("US")){
            if (time.isBefore(LocalTime.of(13,0))){
                formattedTime = time.format(hourMinuteFormatter) + " AM";
            } else {
                formattedTime = time.format(hourMinuteFormatter) + " PM";
            }
        } else {
            //Return original time if other country
            formattedTime = time.format(hourMinuteFormatter);
        }

        //Return formatted time
        return formattedTime;
    }
}
