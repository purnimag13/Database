import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class DataBase 
{
    Scanner fileScanner;
    byte[] massiveByteArr;
    int resize = 0;
    
    HashTable hash;
    //this class is gonna take care of array stuff like making it and all dat
    public DataBase()
    {
        massiveByteArr = new byte[resize];
        hash = new HashTable();
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
                resize += 3;
                massiveByteArr = Arrays.copyOf(massiveByteArr, resize);
                for (int i = 0; i < massiveByteArr.length; i++)
                {
                    
                }
            }

            
        }
        
    }
    public void makeByteArray()
    {
        
    }
}
