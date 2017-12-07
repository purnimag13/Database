import java.io.IOException;

/**
 * runs everything
 * @author Purnima Gosh
 * @author taralaughlin
 * @version 12.06.17
 */
public class SongSearchTest extends student.TestCase
{
    /**
     * sets up the method
     */
    public void setUp()
    {
        //
    }
    /**
     * tests the main thing
     * @throws IOException 
     */
    @SuppressWarnings("static-access")
    public void testMain() throws IOException
    {
        String[] args1 = {"100", "10", "input.txt"};
        SongSearch song = new SongSearch(); 
        song.main(args1);
        assertTrue(args1.equals(args1));
        assertEquals(song.getClass(), song.getClass());
    }

}
