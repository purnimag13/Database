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
//    MemoryManager testMM;
//    DataBase data;
//    HashTable art;
//    HashTable song1;
//    String artist;
//    String song;
//    
//    public void setUp()
//    {
//        data = new DataBase(100, 100);
//        data.artistTree
//        song1 = new HashTable(100, data);
//        art = new HashTable(100, data);
//        testMM = new MemoryManager(art, song1);
//        artist = "artist";
//        song = "song";
//    }
//    /**
//     * Tests adding a new song to the database
//     */
//    public void testAdd()
//    {
//        Handle[] test = testMM.add(artist, song);
//        ArrayList<Handle> arr = testMM.findHandle();
//        assertTrue(arr.contains(test[0]));
//        assertEquals(2, arr.size());
//    }
//    /**
//     * Tests add when the song exists in the database
//     * Makes sure a second item isn't added
//     */
//    public void testAddDupArtist()
//    {
//        Handle[] test1 = testMM.add(artist, song);
//        art.insert(artist, test1[0]);
//        song1.insert(song, test1[1]);
//        Handle[] test2 = testMM.add(artist, "otherSong");
//        ArrayList<Handle> arr = testMM.findHandle();
//        assertTrue(arr.contains(test1[0]));
//        assertTrue(arr.contains(test2[1]));
//        assertNull(test2[0]);
//    }
//    /**
//     * Test add after a handle has been removed
//     */
//    public void testAddRemoveArtist()
//    {
//        testMM.add(artist, song);
//        testMM.remove(artist);
//        Handle[] test = testMM.add(artist, song);
//        ArrayList<Handle> arr = testMM.findHandle();
//        assertTrue(arr.contains(test[0]));
//    }
//    /**
//     * Test add after a handle has been removed
//     */
//    public void testAddRemoveSong()
//    {
//        testMM.add(artist, song);
//        testMM.remove(song);
//        Handle[] test = testMM.add(artist, song);
//        ArrayList<Handle> arr = testMM.findHandle();
//        assertTrue(arr.contains(test[1]));
//    }
//    /**
//     * Tests removing an artist from the database
//     */
//    public void testRemoveArtist()
//    {
//        testMM.add(artist, song);
//        boolean check = testMM.remove(artist);
//        ArrayList<Handle> arr = testMM.findHandle();
//        assertTrue(check);
//        assertEquals(1, arr.size());
//    }    
//    /**
//     * Tests removing an song from the database
//     */
//    public void testRemoveSong()
//    {
//        testMM.add(artist, song);
//        boolean check = testMM.remove(song);
//        ArrayList<Handle> arr = testMM.findHandle();
//        assertTrue(check);
//        assertEquals(1, arr.size());
//    }
//    /**
//     * Tests remove when there is nothing in the database
//     */
//    public void testRemoveEmpty()
//    {
//        boolean check = testMM.remove(artist);
//        assertFalse(check);
//    }
//    
//    public void testFindIndex()
//    {
//        testMM.add(artist, song);
//        int a = testMM.findIndexOfHandle(artist);
//        int s = testMM.findIndexOfHandle(song);
//        assertEquals(2, a);
//        assertEquals(10, s);
//    }
//    /**
//     * why is this not showig up
//     */
//    public void testRemoveMultipleDup()
//    {
//        testMM.add(artist, song);
//        testMM.add("Justin", "Baby");
//        testMM.add("Sam Smith", "Not the Only One");
//        testMM.add("Justin", "I love girls");
//        testMM.add("Jay", "Baby");
//        int a = testMM.findIndexOfHandle("Justin");
//        int s = testMM.findIndexOfHandle("Not the Only One");
//        int t = testMM.findIndexOfHandle("Jay");
//        ArrayList<Handle> arr = testMM.findHandle();
//        assertFalse(arr.isEmpty());
//        assertEquals(16, a);
//        assertEquals(41, s);
//        assertEquals(73, t);
//        testMM.remove("Justin");
//        assertEquals(7, arr.size());
//        
//    }
//    
//    public void testSearchAndReturn()
//    {
//        testMM.add("Justin Bieber", "One Time");
//        ArrayList<Handle> arr = testMM.findHandle();
//        Handle test = testMM.searchAndReturn("Justin Bieber");
//        
//    }
}
