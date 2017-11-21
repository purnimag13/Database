import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataBase 
{
    Scanner fileScanner;
    //byte[] massiveByteArr;
    ArrayList<Byte> massiveByteArr;
    int resize = 0;
    
    HashTable hashArtist;
    HashTable hashSong;
    //this class is gonna take care of array stuff like making it and all dat
    public DataBase()
    {
        //massiveByteArr = new byte[resize];
        massiveByteArr = new ArrayList<>();
        hashArtist = new HashTable();
        hashSong = new HashTable();
    }
    
    public void readFile(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine())
        {
            if (!fileScanner.hasNext()) {
                break;
            }
            String instruction = fileScanner.next();
            if (instruction.equals("insert"))
            {
                
            }

            
        }
        
    }
    public void insert()
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
        //these strings can be turned into bytes which can be
        String artistString = artist.toString();
        String songTitleString = songTitle.toString();
        //inserted into our byte array
        byte[] songBytes = songTitleString.getBytes();
        byte[] artistBytes = artistString.getBytes();
        
        int lengthOfArtist = artistBytes.length;
        int lengthOfSong = songBytes.length;
        
//        resize += (3 + lengthOfArtist + lengthOfSong);
//        massiveByteArr = Arrays.copyOf(massiveByteArr, massiveByteArr.length + resize);
//        for (int i = 0; i < resize; i++)
//        {
//            
//        }
        int s = 1;
        massiveByteArr.add((byte) s);
        
        massiveByteArr.add((byte) (lengthOfArtist + lengthOfSong));
        for (int k = 0; k < artistBytes.length; k++)
        {
            massiveByteArr.add(artistBytes[k]);
        }
        for (int k = 0; k < songBytes.length; k++)
        {
            massiveByteArr.add(songBytes[k]);
        }
        Handle hand = new Handle(massiveByteArr.indexOf(s), (lengthOfArtist + lengthOfSong));
        
    }
}
