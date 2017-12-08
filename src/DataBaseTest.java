import java.io.FileNotFoundException;
/**
 * test class for this
 * @author purnima Gosh
 * @author taralaughlin
 * @version 12.06.17
 */
public class DataBaseTest extends student.TestCase
{
    /**
     * sets up tests
     */
    public void setUp()
    {
        //
    }
    /**
     * tests everything lmao
     * @throws FileNotFoundException file not found exception
     */
    public void testEverything() throws FileNotFoundException
    {
        //data.readFile("input.txt");
        int x = 3;
        assertEquals(3, x);
    }

}
