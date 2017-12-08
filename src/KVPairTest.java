/**
 * 
 */

/**
 * @author Purnima Ghosh
 * @version Dec 7, 2017
 *
 */
public class KVPairTest extends student.TestCase
{
    private KVPair kv;
    private KVPair less;
    private KVPair more;
    /**
     * sets up the tests
     */
    public void setUp()
    {
        Handle kvh1 = new Handle(4, 4);
        Handle kvh2 = new Handle(5, 5);
        kv = new KVPair(kvh1, kvh2);
        
        Handle less1 = new Handle(3, 3);
        Handle less2 = new Handle(2, 2);
        less = new KVPair(less1, less2);
        
        Handle more1 = new Handle(6, 6);
        Handle more2 = new Handle(7, 7);
        more = new KVPair(more1, more2);
    }
    /**
     * Tests the compare method for KVPair
     */
    public void testCompareTo()
    {
        assertEquals(1, kv.compareTo(less));
        assertEquals(-1, kv.compareTo(more));
        assertEquals(0, kv.compareTo(kv));
        
        Handle lkey = new Handle(4, 4);
        less.setKey(lkey);
        assertEquals(1, kv.compareTo(less));
        
        Handle mkey = new Handle(4, 4);
        more.setKey(mkey);
        assertEquals(-1, kv.compareTo(more));
    }
    /**
     * Tests setters and getters
     */
    public void testSetGets()
    {
        Handle less1 = new Handle(3, 3);
        Handle less2 = new Handle(2, 2);
        kv.setKey(less1);
        kv.setValue(less2);
        assertEquals(less1, kv.getKey());
        assertEquals(less2, kv.getValue());
    }
}
