package model;

import java.sql.Date;

/**Class to create and manage Appointments*/
public class Appointment {
    private String contactName;
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Date startTimeDate;
    private Date endTimeDate;
    private int customerID;

    /**
     * @param contactName The contact name to set
     * @param appointmentID The appointment id to set
     * @param title The title to set
     * @param description Thge description to set
     * @param location The location to set
     * @param type The type to set
     * @param startTimeDate The start time and date to set
     * @param endTimeDate The end time and date to set
     * @param customerID The customer id to set
     */
    public Appointment(String contactName, int appointmentID, String title, String description, String location, String type, Date startTimeDate, Date endTimeDate, int customerID){
        this.contactName = contactName;
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTimeDate = startTimeDate;
        this.endTimeDate = endTimeDate;
        this.customerID = customerID;
    }

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

    /**
     * @return the start date and time
     */
    public Date getStartTimeDate() {
        return startTimeDate;
    }

    /**
     * @param startTimeDate the start time and date to set
     */
    public void setStartTimeDate(Date startTimeDate) {
        this.startTimeDate = startTimeDate;
    }

    /**
     * @return the end time and date
     */
    public Date getEndTimeDate() {
        return endTimeDate;
    }

    /**
     * @param endTimeDate the end time and date to set
     */
    public void setEndTimeDate(Date endTimeDate) {
        this.endTimeDate = endTimeDate;
    }

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
}
