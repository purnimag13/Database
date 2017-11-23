/**
 * 
 */

/**
 * @author Purnima Ghosh and Tara Laughlin
 * @version Nov 16, 2017
 *
 */
public class KVPair implements Comparable<KVPair>
{
    private Handle key;
    private Handle value;

    public KVPair(Handle k, Handle v)
    {
        key = k;
        value = v;
    }

    /**
     * This method compares two KVPair objects and returns
     * the proper ordering
     * @param object the object being compared
     * @return an int that represents the correct ordering
     */
    @Override
    public int compareTo(KVPair object) 
    {
        if (this.key.getOff() < object.key.getOff())
        {
            return -1;
        }
        else if (this.key.getOff() > object.key.getOff())
        {
            return 1;
        }
        else if (this.value.getOff() < object.value.getOff())
        {
            return -1;
        }
        else if (this.value.getOff() > object.value.getOff())
        {
            return 1;
        }
        //might be unnecessary because we can't have duplicates right?
        else
        {
            return 0;
        }
    }

}
