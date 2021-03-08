
/**
 * This class represents the items that belong in each room.
 *
 * @author Robert Tallafer  
 * @version 2021.03.05
 */
public class Items
{
    
    private String itemsDescription;
    private double itemsWeight;

   
    /**
     * Constructor for Item objects
     * @param (description of the item, weight of the item)
     */
    public Items()
    {
     itemsDescription="";
     itemsWeight=0.0;
    }
    /**
     * Constructor that will create an item for a room from the parameters
     * @param (description of the item, weight of the item)
     */
    public Items(String description, int weight)
    {
     itemsDescription=description;
     itemsWeight=weight;
    }
    
    
    
    /**
     * gets the description of an item
     * @return itemInfo description plus weight returned as a string
     */
    public String getItemDescription()
    {
        String itemInfo ="Item name:"+this.itemsDescription+"\t Weight: " + this.itemsWeight;
        return itemInfo;
    }
}
