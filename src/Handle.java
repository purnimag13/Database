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
public class Handle implements Comparable 
{
    private int off;
    private int len;
    /**
     * constructor for handle class
     * @param offset locations in the array of the data
     * @param length length of the data
     */
    public Handle(int offset, int length)
    {
        this.off = offset;
        this.len = length;
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
    public int getLen() 
    {
        return len;
    }
    /**
     * sets the length of offset
     * @param len length
     */
    public void setLen(int len) 
    {
        this.len = len;
    }
    /**
     * compares if two handles are equal
     * @param hand hangle to be compared
     * @return boolean true or false
     */
    public boolean equals(Handle hand)
    {
        return off == hand.getOff() && len == hand.getLen();
    }
    
    
    // I DONT KNOW IF WE NEED THIS YET BUT WE CAN KEEP IT FOR NOW
    public int byteCalculation()
    {
        return (len + 3) / 4;   
    }
    
    //@Override
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
