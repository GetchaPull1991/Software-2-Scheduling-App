package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**Class to create and manage Appointments*/
public class Appointment {
    private String contactName;
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int customerID;
    private String user;

    /**
     * @param contactName The contact name to set
     * @param appointmentID The appointment id to set
     * @param title The title to set
     * @param description Thge description to set
     * @param location The location to set
     * @param type The type to set
     * @param startDate The start date to set (Combines with start time)
     * @param startTime The start time to set
     * @param endDate The end date to set (Combines with end time)
     * @param endTime The end time to set
     * @param customerID The customer id to set
     */
    public Appointment(String contactName, int appointmentID, String title, String description, String location,
                       String type, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int customerID, String user){
        this.contactName = contactName;
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.endDate = endDate;
        this.customerID = customerID;
        this.user = user;
    }

    //Contact name
    /**
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    //Appointment ID
    /**
     * @return appointment id
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @param appointmentID the appointment id to set
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    //Title
    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    //Description
    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    //Location
    /**
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    //Type
    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    //Start date
    /**
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the start date to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    //Start time
    /**
     * @return the start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the start time to set
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    //End date
    /**
     * @return the end time and date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the end date to set (Combines with end time)
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    //End time
    /**
     * @return end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the end time to set
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    //Customer ID
    /**
     * @return customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customer id to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
}
