package model;

/**Class to create and manage divisions*/
public class Division {
    private int divisionID;
    private String name;
    private int countryID;

    /**
     * @param divisionID division id to be set
     * @param name division name to be set
     * @param countryID division country id to be set
     */
    public Division(int divisionID, String name, int countryID){
        this.divisionID = divisionID;
        this.name = name;
        this.countryID = countryID;
    }

    /**
     * @return division id
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param divisionID division id to be set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * @return division name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name division name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return division country id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID division country id to be set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
