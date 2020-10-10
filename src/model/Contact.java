package model;

/**Class to create and manage contacts*/
public class Contact {

    private int contactID;
    private String name;
    private String email;

    /**
     * @param contactID contact id to be set
     * @param name contact name to be set
     * @param email contact email to be set
     */
    public Contact(int contactID, String name, String email){
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    /**
     * @return contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param contactID contact id to be set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
