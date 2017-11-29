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
                if (fileScanner.next() == "artist")
                {
                    removeArtist(fileScanner.next());
                }
                else if (fileScanner.next() == "song")
                {
                    removeSong(fileScanner.next());
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
                    StringBuilder artist = new StringBuilder();
                    artist.append(fileScanner.next());
                    String artistString = artist.toString();
                    byte[] artistBytes = artistString.getBytes();
                    listArtist(artistBytes);
                }
                else if (fileScanner.next() == "song")
                {
                    
                }
            }
            else if (instruction.equals("delete"))
            {
                //Delete the specific record for this particular song by this particular artist from tree
            }

            
        }
        
    }
    public void listArtist(String s)
    {
        ArrayList<Handle> arrHandle = new ArrayList<>();
        int i = 0; 
        while (i < hashArtist.getTable().length)
        {
            if (s == hashArtist.getTable()[i].getKey())
            {
                arrHandle.add(hashArtist.getTable()[i].getValue());
            }
            i++;
        }
        
    }
    /**
     * removes all artists from everything
     * @param obj artist to be removed
     */
    public void removeArtist(String obj)
    {
        while (hashArtist.find(obj))
        {
           hashArtist.remove(obj);
           massiveByteArr.remove(hashArtist.get(obj).getOff());
           artistTree.remove(hashArtist.get(obj));
        }
    }
    /**
     * removes all songs from everything
     * @param obj song to be removed
     */
    public void removeSong(String obj)
    {
        while (hashSong.find(obj))
        {
           hashSong.remove(obj);
           massiveByteArr.remove(hashSong.get(obj).getOff());
           songTree.remove(hashSong.get(obj));
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
        massiveByteArr.add((byte) s1); // need to byt buffer this
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
