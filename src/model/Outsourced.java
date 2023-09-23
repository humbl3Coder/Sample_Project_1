package model;

public class Outsourced extends Part{

    private String companyName;

    /**
     * Constructor for Outsourced part
     * @param id this is the id of the object
     * @param name this is the name of the object
     * @param price this is the price of the object
     * @param stock this is the stock level of the object
     * @param min this is maximum of the stock level
     * @param max this is minimum of the stock level
     * @param companyName this is the companyName of the object
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

}
