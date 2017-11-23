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
     * @param o
     * @return
     */
    @Override
    public int compareTo(KVPair o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
