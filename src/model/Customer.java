package model;

/**Class to create and manage customers*/
public class Customer {
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String division;
    private String country;
    private int customerID;

    /**
     * @param name The name of the customer
     * @param address The address of the customer
     * @param postalCode The postal code of the customer
     * @param phoneNumber The phone number of the customer
     * @param division The division of the customer
     * @param country The country of the customer
     * @param customerID The ID of the customer
     */
    public Customer(String name, String address, String postalCode, String phoneNumber, String division, String country, int customerID){
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.division = division;
        this.country = country;
        this.customerID = customerID;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}

