
public class HandleTest extends student.TestCase
{
    Handle hand;
    Handle empty;
    Handle hand2;
    Handle hand3;
    Handle hand4;
    
    /**
     * constructor for handle class
     */
    public void setUp()
    {
        hand = new Handle(4, 7);
        empty = null;
        hand2 = new Handle(4, 7);
        hand3 = new Handle(3, 6);
        hand4 = new Handle(5, 8);
    }
    /**
     * Tests equals method
     */
    public void testEquals()
    {
        assertFalse(hand.equals(empty));
        assertTrue(hand.equals(hand2));        
        hand3.setLen(7);
        assertFalse(hand.equals(hand3));
        hand4.setOff(4);
        assertFalse(hand.equals(hand4));
    }
    /**
     * Tests comapreTo method when the values are the same
     */
    public void testCompareToSame()
    {
        assertEquals(0, hand.compareTo(hand2));
        assertEquals(1, hand.compareTo(hand3));
        assertEquals(-1, hand.compareTo(hand4));
    }
    /**
     * Tests the getters and setters
     */
    public void testGetSet()
    {
        assertEquals(3, hand3.getOff());
        assertEquals(6, hand3.getLen());
        hand4.setLen(2);
        hand4.setOff(2);
        assertEquals(2, hand4.getOff());
        assertEquals(2, hand4.getLen());
    }
}
