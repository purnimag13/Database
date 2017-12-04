import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Purnima Ghosh
 * @version Dec 3, 2017
 *
 */
public class MemoryManagerTest extends student.TestCase
{
    MemoryManager testMM;
    String artist;
    String song;
    
    public void setUp()
    {
        testMM = new MemoryManager();
        artist = "artist";
        song = "song";
    }
    /**
     * Tests adding a new song to the database
     */
    public void testAdd()
    {
        Handle[] test = testMM.add(artist, song);
        ArrayList<Handle> arr = testMM.findHandle();
        assertTrue(arr.contains(test[0]));
    }
    /**
     * Tests add when the song exists in the database
     * Makes sure a second item isn't added
     */
    public void testAddDupArtist()
    {
        Handle[] test1 = testMM.add(artist, song);
        Handle[] test2 = testMM.add(artist, "otherSong");
        ArrayList<Handle> arr = testMM.findHandle();
        
    }
    
}
