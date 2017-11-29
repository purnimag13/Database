/**
 * 
 */

/**
 * Stores values for finding data in the array
 * @author Purnima Ghosh
 * @author ltara8
 * @version Nov 16, 2017
 *
 */
public class Handle implements Comparable<Handle> 
{
    private int off;
    private String ident;
    /**
     * constructor for handle class
     * @param offset locations in the array of the data
     * @param length length of the data
     */
    public Handle(int offset, String identifier)
    {
        this.off = offset;
        this.ident = identifier;
    }
    /**
     * gets the offset
     * @return int offset
     */
    public int getOff() 
    {
        return off;
    }
    /**
     * sets the offset
     * @param off the offset given
     */
    public void setOff(int off) 
    {
        this.off = off;
    }
    /**
     * gets length of offset
     * @return int offset
     */
    public String getIdent() 
    {
        return ident;
    }
    /**
     * sets the length of offset
     * @param len length
     */
    public void setLen(String length) 
    {
        this.ident = length;
    }
    /**
     * compares if two handles are equal
     * @param hand hangle to be compared
     * @return boolean true or false
     */
    public boolean equals(Handle hand)
    {
        return off == hand.getOff() && ident == hand.getIdent();
    }
    
    @Override
    public int compareTo(Handle hand) 
    {
        if (this.getOff() < hand.getOff())
        {
            return -1;
        }
        else if (this.getOff() > hand.getOff())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    

}
