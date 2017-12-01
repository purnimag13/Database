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
        
        hashArtist = new HashTable(100);
        hashSong = new HashTable(100);
        
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
                    listArtist(fileScanner.next());
                    
                }
                else if (fileScanner.next() == "song")
                {
                    listSong(fileScanner.next());
                }
            }
            else if (instruction.equals("delete"))
            {
                //Delete the specific record for this particular song by this particular artist from tree
            }

            
        }
        
    }
    /**
     * lists all the songs by this artist
     * @param s artist searched
     * @return ArrayList<Handle> to find the songs
     */
    public ArrayList<Handle> listArtist(String s)
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
        return artistTree.orderTree(arrHandle);
    }
    /**
     * lists all the artists who sing this song
     * @param s song searched
     * @return ArrayList<Handle> to find the artists
     */
    public ArrayList<Handle> listSong(String s)
    {
        ArrayList<Handle> arrHandle = new ArrayList<>();
        int i = 0; 
        while (i < hashSong.getTable().length)
        {
            if (s == hashSong.getTable()[i].getKey())
            {
                arrHandle.add(hashSong.getTable()[i].getValue());
            }
            i++;
        }
        return songTree.orderTree(arrHandle);
    }
    /**
     * removes all artists from everything
     * @param obj artist to be removed
     */
    public void removeArtist(String obj)
    {
        if (!hashArtist.find(obj))
        {
            System.out.println(hashArtist.get(obj).toString() + " does not exist in the artist database.");
        }
        while (hashArtist.find(obj))
        {
           hashArtist.remove(obj);
           massiveByteArr.set(hashArtist.get(obj).getOff(), (byte) 0);
           artistTree.remove(hashArtist.get(obj));
        }
    }
    public void delete(String art, String title)
    {
        if ()
        
    }
    /**
     * removes all songs from everything
     * @param obj song to be removed
     */
    public void removeSong(String obj)
    {
        if (!hashArtist.find(obj))
        {
            System.out.println(hashSong.get(obj).toString() + " does not exist in the song database.");
        }
        while (hashSong.find(obj))
        {
           hashSong.remove(obj);
           massiveByteArr.set(hashSong.get(obj).getOff(), (byte) 0);
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
        Handle handArtist = new Handle(massiveByteArr.indexOf(s1), 
                (lengthOfArtist + 2));
        Handle handSong = new Handle(massiveByteArr.indexOf(s2), 
                (lengthOfSong + 2));
        
        artistTree.insert(handArtist, handSong);
        songTree.insert(handSong, handArtist);
        
        System.out.println(handArtist.toString() + " is added to the Artist database.");
        System.out.println(handSong.toString() + " is added to the Song database.");
        
        return hashArtist.insert(artistString, handArtist) ||
                hashSong.insert(songTitleString, handSong);
        
    }
}
