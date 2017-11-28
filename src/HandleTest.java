
public class HandleTest extends student.TestCase
{
    Handle hand;
    Handle hand1;
    Handle hand2;
    /**
     * constructor for handle class
     */
    public void setUp()
    {
        hand = new Handle(5, 10);
        hand1 = new Handle(5, 10);
        hand2 = new Handle(2,20);
    }
    /**
     * tests getters and setters and equals
     */
    public void testGetSet()
    {
        assertEquals(hand.getOff(), 5);
        assertEquals(hand.getLen(), 10);
        hand.setLen(20);
        hand.setOff(2);
        assertTrue(hand.equals(hand2));
        assertFalse(hand.equals(hand1));
        
    }

}
