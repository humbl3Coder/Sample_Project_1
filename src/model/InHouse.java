package model;

/**
 * The InHouse class extends the Part class.
 */
public class InHouse extends Part{

    private int machineID;

    /**
     * Constructor for InHouse class.
     * @param id this is the id of the object
     * @param name this is name of the object
     * @param price this is the price of the object
     * @param stock this is the stock level of the object
     * @param min this is the minimum level of the object
     * @param max this is the maximum level of the object
     * @param machineID this is the machine ID of object
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     *
     * @param machineID the machineID to set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     *
     * @return the machineID
     */
    public int getMachineID() {
        return machineID;
    }

}
