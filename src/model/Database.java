package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**Class for handling database operations*/
public class Database {

    private static Connection connection = null;

    /**Connect to the database*/
    private static void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://wgudb.ucertify.com:3306/WJ07XAQ", "U07XAQ", "53689156266");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**Disconnect from the database*/
    private static void disconnect(){
        try{
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return list of contacts
     */
    public static ObservableList<Contact> getContacts(){
        //Create connection
        connect();
        //List to store contacts
       ObservableList<Contact> contactList = FXCollections.observableArrayList();

        //Try statement
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet queryResult = statement.executeQuery("SELECT * FROM contacts;");

            //Loop through query result and append Contact objects to list
            while(queryResult.next()){
                contactList.add(new Contact(queryResult.getInt("Contact_ID"),
                                            queryResult.getString("Contact_Name"),
                                            queryResult.getString("Email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Close connection
        disconnect();

        //Return the contact list
        return contactList;
    }

    /**
     * @return list of countries
     */
    public static ObservableList<String> getCountries(){
        //Create connection
        connect();
        //List to store the countries
        ObservableList<String> countryList = FXCollections.observableArrayList();

        //Try statement
        try(Statement statement = connection.createStatement()){

            //Get result set
            ResultSet queryResult = statement.executeQuery("SELECT * FROM countries;");

            //Loop through query result and store country names in list
            while(queryResult.next()){
                countryList.add(queryResult.getString("Country"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Return the list of countries
        return countryList;
    }

    /**
     * @return list of divisions
     */
    public static ObservableList<Division> getDivisions(){
        //Create connection
        connect();
        //List to store divisions
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        //Try statement
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet queryResult = statement.executeQuery("SELECT * FROM divisions;");

            //Loop through query result and append division objects to list
            while(queryResult.next()){
                divisionList.add(new Division(queryResult.getInt("Division_ID"),
                        queryResult.getString("Division"),
                        queryResult.getInt("COUNTRY_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Close connection
        disconnect();

        //Return the division list
        return divisionList;
    }

    /**
     * @return list of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments(){
        //Create connection
        connect();
        //List to store appointments
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        //Try statement
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet queryResult = statement.executeQuery("SELECT * FROM appointments;");

            //Loop through query result and append Contact objects to list
            while(queryResult.next()){

                //Get the contact name with the contact id associated with the appointment
                String contactName = "";
                try(Statement statement1 = connection.createStatement()){
                    ResultSet queryResult1 = statement1.executeQuery("SELECT Contact_Name FROM contacts WHERE Contact_ID ='"
                            + queryResult.getInt("Contact_ID" + "';"));
                    queryResult1.next();
                    contactName = queryResult.getString("Contact_Name");
                }

                //Create appointment and add to list
                appointmentList.add(new Appointment(contactName,
                                                    queryResult.getInt("Appointment_ID"),
                                                    queryResult.getString("Title"),
                                                    queryResult.getString("Description"),
                                                    queryResult.getString("Location"),
                                                    queryResult.getString("Type"),
                                                    queryResult.getDate("Start"),
                                                    queryResult.getDate("End"),
                                                    queryResult.getInt("Customer_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Close connection
        disconnect();

        //Return the appointment list
        return appointmentList;
    }

    /**
     * @param customerID the customer id to search the table
     * @return list of customers appointments
     */
    public static ObservableList<Appointment> getCustomerAppointments(int customerID){
        //Create connection
        connect();
        //List to store appointments
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        //Try statement
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet queryResult = statement.executeQuery("SELECT * FROM appointments WHERE Customer_ID = '" + customerID + "';");

            //Loop through query result and append Contact objects to list
            while(queryResult.next()){

                //Get the contact name with the contact id associated with the appointment
                String contactName = "";
                try(Statement statement1 = connection.createStatement()){
                    ResultSet queryResult1 = statement1.executeQuery("SELECT Contact_Name FROM contacts WHERE Contact_ID ='"
                            + queryResult.getInt("Contact_ID" + "';"));
                    queryResult1.next();
                    contactName = queryResult.getString("Contact_Name");
                }

                //Create appointment and add to list
                appointmentList.add(new Appointment(contactName,
                        queryResult.getInt("Appointment_ID"),
                        queryResult.getString("Title"),
                        queryResult.getString("Description"),
                        queryResult.getString("Location"),
                        queryResult.getString("Type"),
                        queryResult.getDate("Start"),
                        queryResult.getDate("End"),
                        queryResult.getInt("Customer_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Close connection
        disconnect();

        //Return the appointment list
        return appointmentList;
    }

    /**
     * @param contactID the contact id to search the table
     * @return list of contacts appointments
     */
    public static ObservableList<Appointment> getContactAppointments(int contactID){
        //Create connection
        connect();
        //List to store appointments
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        //Try statement
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet queryResult = statement.executeQuery("SELECT * FROM appointments WHERE Contact_ID = '" + contactID + "';");

            //Loop through query result and append Contact objects to list
            while(queryResult.next()){

                //Get the contact name with the contact id associated with the appointment
                String contactName = "";
                try(Statement statement1 = connection.createStatement()){
                    ResultSet queryResult1 = statement1.executeQuery("SELECT Contact_Name FROM contacts WHERE Contact_ID ='"
                            + queryResult.getInt("Contact_ID" + "';"));
                    queryResult1.next();
                    contactName = queryResult.getString("Contact_Name");
                }

                //Create appointment and add to list
                appointmentList.add(new Appointment(contactName,
                        queryResult.getInt("Appointment_ID"),
                        queryResult.getString("Title"),
                        queryResult.getString("Description"),
                        queryResult.getString("Location"),
                        queryResult.getString("Type"),
                        queryResult.getDate("Start"),
                        queryResult.getDate("End"),
                        queryResult.getInt("Customer_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Close connection
        disconnect();

        //Return the appointment list
        return appointmentList;
    }

    /**
     * @return list of customers
     */
    //Find out how to get the country for customer object
    public static ObservableList<Customer> getAllCustomers(){
        //Create connection
        connect();
        //List to store customers
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        //Try statement
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet queryResult = statement.executeQuery("SELECT * FROM customers;");

            //Loop through query result and append Customer objects to list
            while(queryResult.next()){

                //Create variables for division name and country name
                String divisionName = "";
                String countryName = "";

                //Try statement
                try(Statement statement1 = connection.createStatement()){

                    //Get division name and country name
                    ResultSet resultSet = statement1.executeQuery("SELECT first_level_divisions.Division, countries.Country " +
                            "FROM first_level_divisions join countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID " +
                            "WHERE Division_ID = '" + queryResult.getInt("Division_ID") + "';");

                    //Move cursor to first row of result set
                    resultSet.next();

                    //Get division name and country name from result set
                    divisionName = resultSet.getString("Division");
                    countryName = resultSet.getString("Country");
                }

                //Create Customer and add to list
                customerList.add(new Customer(queryResult.getString("Customer_Name"),
                        queryResult.getString("Address"),
                        queryResult.getInt("Postal_Code"),
                        queryResult.getString("Phone"),
                        divisionName,
                        countryName,
                        queryResult.getInt("Customer_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Close connection
        disconnect();

        //Return the Customer list
        return customerList;
    }

    /**
     * @return list of users
     */
    public static ObservableList<User> getAllUsers(){
        //Create connection
        connect();
        //List to store users
        ObservableList<User> userList = FXCollections.observableArrayList();

        //Try statement
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet queryResult = statement.executeQuery("SELECT * FROM users;");

            //Loop through query result and append user objects to list
            while(queryResult.next()){
                userList.add(new User(queryResult.getInt("User_ID"),
                        queryResult.getString("User_Name"),
                        queryResult.getString("Password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Close connection
        disconnect();

        //Return the user list
        return userList;
    }

    /**
     * @param user the current application user
     * @param customer the customer to add
     */
    public static void addCustomer(Customer customer, User user) throws ParseException {

        //Get current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = formatter.parse(formatter.format(System.currentTimeMillis()));
        Timestamp currentDateTimestamp = new Timestamp(date.getTime());

        //Variable to store division id
        int divisionID = 0;

        //Create connection
        connect();

        //Get division name
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet resultSet = statement.executeQuery("SELECT Division_ID FROM first_level_divisions WHERE Division = '" +
                    customer.getDivision() + "';");

            //Move cursor to first row of result set
            resultSet.next();

            //Store the division id
            divisionID = resultSet.getInt("Division_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Insert customer into customers table
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO customers (Customer_ID, Customer_Name, " +
                    "Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "Values ('" + customer.getCustomerID() + "' , '" + customer.getName() + "' , '" + customer.getAddress() + "' , '" +
                    customer.getPostalCode() + "' , '" + customer.getPhoneNumber() + "' , '" + currentDateTimestamp + "' , '" +
                    user.getUserName() + "' , '" + currentDateTimestamp + "' , '" + user.getUserName() + "' , '" + divisionID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Close connection
        disconnect();
    }

    /**
     * @param customer the customer to remove
     */
    public static void removeCustomer(Customer customer){

        //Create connection
        connect();

        //Remove customer from customer table
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM customers WHERE Customer_ID = '" + customer.getCustomerID() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Close connection
        disconnect();

    }

    /**
     * @param user the current application user
     * @param customer the customer to update
     */
    public static void updateCustomer(Customer customer, User user) throws ParseException {

        //Get current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = formatter.parse(formatter.format(System.currentTimeMillis()));
        Timestamp currentDateTimestamp = new Timestamp(date.getTime());

        //Variable to store division id
        int divisionID = 0;

        //Create connection
        connect();

        //Get division name
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet resultSet = statement.executeQuery("SELECT Division_ID FROM first_level_divisions WHERE Division = '" +
                    customer.getDivision() + "';");

            //Move cursor to first row of result set
            resultSet.next();

            //Store the division id
            divisionID = resultSet.getInt("Division_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Update customer in customers table
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("UPDATE customers SET Customer_Name = '" + customer.getName() + "' , " +
                    "Address = '" + customer.getAddress() + "' , Postal_Code = '" + customer.getPostalCode() + "' , Phone = '" + customer.getPhoneNumber() +
                    "' , Last_Update = '" + currentDateTimestamp + "' , Last_Updated_By = '" + user.getUserName() +
                    "', Division_ID = '" + divisionID + "' WHERE Customer_ID = '" + customer.getCustomerID() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Close connection
        disconnect();
    }

    /**
     * @param user the current application user
     * @param appointment the appointment to add
     */
    public static void addAppointment(Appointment appointment, User user) throws ParseException {

        //Get current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = formatter.parse(formatter.format(System.currentTimeMillis()));
        Timestamp currentDateTimestamp = new Timestamp(date.getTime());

        //Variable to store contact id
        int contactID = 0;

        //Create connection
        connect();

        //Get contact id name
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet resultSet = statement.executeQuery("SELECT Contact_ID FROM contacts WHERE Contact_Name = '" +
                    appointment.getContactName() + "';");

            //Move cursor to first row of result set
            resultSet.next();

            //Store the division id
            contactID = resultSet.getInt("Contact_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Insert customer into customers table
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO appointments (Appointment_ID, Title, " +
                    "Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "Values ('" + appointment.getAppointmentID() + "' , '" + appointment.getTitle() + "' , '" + appointment.getDescription() + "' , '" +
                    appointment.getLocation() + "' , '" + appointment.getType() + "' , '" + appointment.getStartTimeDate() + "' , '" +
                    appointment.getEndTimeDate() + "' , '" + currentDateTimestamp + "' , '" + user.getUserName() + "' , '" + currentDateTimestamp + "' , '" + user.getUserName() + "' , '" +
                    appointment.getCustomerID() + "' , '" + user.getUserID() + "' , '" + contactID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Close connection
        disconnect();
    }

    /**
     * @param appointment the appointment to remove
     */
    public static void removeAppointment(Appointment appointment){
        //Create connection
        connect();

        //Remove customer from customer table
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM appointments WHERE Appointment_ID = '" + appointment.getAppointmentID() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Close connection
        disconnect();
    }

    /**
     * @param user the current application user
     * @param appointment the appointment to update
     */
    public static void updateAppontment(Appointment appointment, User user) throws ParseException {

        //Get current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = formatter.parse(formatter.format(System.currentTimeMillis()));
        Timestamp currentDateTimestamp = new Timestamp(date.getTime());

        //Variable to store contact id
        int contactID = 0;

        //Create connection
        connect();

        //Get contact id
        try(Statement statement = connection.createStatement()){
            //Get result set
            ResultSet resultSet = statement.executeQuery("SELECT Contact_ID FROM contacts WHERE Contact_Name = '" +
                    appointment.getContactName() + "';");

            //Move cursor to first row of result set
            resultSet.next();

            //Store the division id
            contactID = resultSet.getInt("Contact_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Update appointment in appointments table
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("UPDATE appointments SET Title = '" + appointment.getTitle() + "' , " +
                    "Description = '" + appointment.getDescription() + "' , Location = '" + appointment.getLocation() + "' , Type = '" + appointment.getType() +
                    "' , Start = '" + appointment.getStartTimeDate() + "' , End = '" + appointment.getEndTimeDate() +
                    "', Last_Update = '" + currentDateTimestamp + "' , Last_Updated_By = '" + user + "' , Customer_ID = '" + appointment.getCustomerID() +
                    "' , User_ID = '" + user.getUserID() + "' , Contact_ID = '" + contactID + " WHERE Appointment_ID = '" + appointment.getAppointmentID() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Close connection
        disconnect();
    }
}
