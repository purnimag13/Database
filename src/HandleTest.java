
public class HandleTest extends student.TestCase
{
    Handle hand;
    Handle empty;
    Handle hand2;
    /**
     * constructor for handle class
     */
    public void setUp()
    {
        hand = new Handle(4, 7);
        empty = null;
        hand2 = new Handle(4, 7);
    }
    /**
     * tests getters and setters and equals
     */
    public void testEquals()
    {
        assertFalse(hand.equals(empty));
        assertFalse(hand.equals(null));
    }

}
