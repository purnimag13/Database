import java.io.FileNotFoundException;

public class DataBaseTest extends student.TestCase
{
    DataBase data;
    public void setUp()
    {
        data = new DataBase(100, 100);
    }
    public void testEverything() throws FileNotFoundException
    {
        //data.readFile("input.txt");
    }

}
