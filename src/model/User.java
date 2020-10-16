package model;

import javafx.beans.property.SimpleStringProperty;

public class User {

    private int userID;
    private String userName;
    private String password;

    /**
     * @param userID user id to set
     * @param userName user name to set
     * @param password user password to set
     */
    public User(int userID, String userName, String password){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID user id to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    public SimpleStringProperty getUserNameProperty(){
        return new SimpleStringProperty(userName);
    }

    /**
     * @param userName user name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password user password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
