import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataBase 
{
    Scanner fileScanner;
    ArrayList<Byte> massiveByteArr;
    int resize = 0;
    
    HashTable hashArtist;
    HashTable hashSong;
    
    KVTree artistTree;
    KVTree songTree;
    
    
    public DataBase()
    {
        massiveByteArr = new ArrayList<>();
        hashArtist = new HashTable();
        hashSong = new HashTable();
        
        artistTree = new KVTree();
        songTree = new KVTree();
    }
    
    public void readFile(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine())
        {
            if (!fileScanner.hasNext()) 
            {
                break;
            }
            String instruction = fileScanner.next();
            if (instruction.equals("insert"))
            {
               insert(); 
            }
            else if (instruction.equals("remove"))
            {
                //Remove the specified artist or song name from the appropriate hash table, 2-3+ (or BST)
                //tree and make corresponding marks in the memory.
                if (fileScanner.next() == "artist")
                {
                    
                }
                else if (fileScanner.next() == "song")
                {
                    
                }
            }
            else if (instruction.equals("print"))
            {
                if (fileScanner.next() == "artist")
                {
                    
                }
                else if (fileScanner.next() == "song")
                {
                    
                }
                else if (fileScanner.next() == "tree")
                {
                    //in order traversal of tree
                }
            }
            else if (instruction.equals("list"))
            {
                if (fileScanner.next() == "artist")
                {
                    
                }
                else if (fileScanner.next() == "song")
                {
                    
                
            }
            else if (instruction.equals("delete"))
            {
                //Delete the specific record for this particular song by this particular artist
            }

            
        }
        
    }
    
    /**
     * unfinished - still need to insert KV into BST
     * @return
     */
    public boolean insert()
    {
        StringBuilder artist = new StringBuilder();
        StringBuilder songTitle = new StringBuilder();
        //creates a string so we can read in the artist
        while (fileScanner.next() != "<SEP>")
        {
           artist.append(fileScanner.next());
        }
        fileScanner.skip("<SEP>");
        while (fileScanner.hasNext())
        {
           songTitle.append(fileScanner.next()); 
        }
        
        String artistString = artist.toString();
        String songTitleString = songTitle.toString();
        
        byte[] songBytes = songTitleString.getBytes();
        byte[] artistBytes = artistString.getBytes();
        
        int lengthOfArtist = artistBytes.length;
        int lengthOfSong = songBytes.length;
        int s1 = 1;
        massiveByteArr.add((byte) s1);
        massiveByteArr.add((byte) lengthOfArtist);
        for (int k = 0; k < artistBytes.length; k++)
        {
            massiveByteArr.add(artistBytes[k]);
        }
        
        int s2 = 1;
        massiveByteArr.add((byte) s2);
        massiveByteArr.add((byte) lengthOfSong);
        for (int k = 0; k < songBytes.length; k++)
        {
            massiveByteArr.add(songBytes[k]);
        }
        Handle handArtist = new Handle(massiveByteArr.indexOf(s1), (lengthOfArtist + artistBytes.length + 1));
        Handle handSong = new Handle(massiveByteArr.indexOf(s2), (lengthOfSong + songBytes.length + 1));
        //STILL NEED TO INSERT KV PAIRS
        return hashArtist.insert(artistString, handArtist) ||
                hashSong.insert(songTitleString, handSong);
        
    }
}
