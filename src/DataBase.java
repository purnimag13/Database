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
    ArrayList<Handle> handArr;
    int resize = 0;

    MemoryManager arr;

    HashTable hashArtist;
    HashTable hashSong;

    KVTree artistTree;
    KVTree songTree;


    public DataBase()
    {
        massiveByteArr = new ArrayList<>();
        handArr = arr.findHandle();
        arr = new MemoryManager();

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
                StringBuilder artist = new StringBuilder();
                StringBuilder songTitle = new StringBuilder();
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
                insert(artistString, songTitleString);
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
                    HashTable.Entry[] temp = hashSong.getTable();
                    ArrayList<Handle> songs = listArtist(fileScanner.next());//list of song handles
                    for (int i = 0; i < temp.length; i++)
                    {
                        if (songs.get(i) == temp[i].getValue())
                        {
                            System.out.println(temp[i].toString());
                        }
                    }
                }
                else if (fileScanner.next() == "song")
                {
                    HashTable.Entry[] temp = hashArtist.getTable();
                    ArrayList<Handle> artists = listSong(fileScanner.next());
                    for (int i = 0; i < temp.length; i++)
                    {
                        if (artists.get(i) == temp[i].getValue())
                        {
                            System.out.println(temp[i].toString());
                        }
                    }
                }
            }
            else if (instruction.equals("delete"))
            {
                String artistName = fileScanner.next();
                fileScanner.skip("<SEP>");
                String songName = fileScanner.next();
                delete(artistName, songName);
            }
        }
    }
    public void printSong()
    {
        int length = hashSong.getTable().length;
        for (int i = 0; i < length; i++)
        {
            hashSong.print();
        }
        System.out.println(hashSong.getTable().length);
    }
    public void printArtist()
    {
        int length = hashArtist.getTable().length;
        for (int i = 0; i < length; i++)
        {
            hashArtist.print();
        }
        System.out.println(hashArtist.getTable().length);
    }
    /**
     * lists all the songs by this artist
     * @param s artist searched
     * @return ArrayList<Handle> to find the songs
     */
    public ArrayList<Handle> listArtist(String s)
    {
        Handle temp = arr.searchAndReturn(s);
        ArrayList<Handle> arrHandle = artistTree.findHandlePair(temp);
        return artistTree.orderTree(arrHandle);
    }
    /**
     * lists all the artists who sing this song
     * @param s song searched
     * @return ArrayList<Handle> to find the artists
     */
    public ArrayList<Handle> listSong(String s)
    {
        Handle temp = arr.searchAndReturn(s);
        ArrayList<Handle> arrHandle = songTree.findHandlePair(temp);
        return songTree.orderTree(arrHandle);
    }
    /**
     * removes all artists from everything
     * @param obj artist to be removed
     */
    public void removeArtist(String obj)
    {
        Handle temp = arr.searchAndReturn(obj);
        if (!hashArtist.find(obj))
        {
            System.out.println(hashArtist.get(obj).toString() + " does not exist in the artist database.");
        }
        else
        {
            arr.remove(obj);
            hashArtist.remove(obj);
            artistTree.remove(temp);
        }
    }
    /**
     * deletes one instance of a record
     * @param art artist to be deleted
     * @param title song to be deleted
     */
    public boolean delete(String art, String title)
    {
        Handle tempArtist;
        Handle tempSong;
        if (!hashArtist.find(art))
        {
            System.out.println("|" + art + "| does not exist in the artist database.");
            return false;
        }
        if (!hashSong.find(title))
        {
            System.out.println("|" + title + "| does not exist in the song database.");
            return false;
        }
        if (arr.searchAndReturn(art) != null && arr.searchAndReturn(title) != null)
        {
            tempArtist = arr.searchAndReturn(art);
            tempSong = arr.searchAndReturn(title);
            artistTree.delete(tempArtist, tempSong);
            songTree.delete(tempSong, tempArtist);
        }
        if (artistTree.countHandles(tempArtist) == 0)
        {
            arr.remove(art);
            hashArtist.remove(art);
        }
        if (songTree.countHandles(tempSong) == 0)
        {
            arr.remove(title);
            hashSong.remove(title);
        }
        return true;
    }
    /**
     * removes all songs from everything
     * @param obj song to be removed
     */
    public void removeSong(String obj)
    {
        Handle temp = arr.searchAndReturn(obj);
        if (!hashSong.find(obj))
        {
            System.out.println(hashSong.get(obj).toString() +
                    " does not exist in the song database.");
        }
        else
        {
          hashSong.remove(obj); 
          arr.remove(obj);
          songTree.remove(temp);
        }
    }
    public void insert(String art, String song)
    {
        Handle[] temp = arr.add(art, song); 
        if (temp[0] != null && temp[1] != null)//artist and song both inserted
        {
            hashArtist.insert(art, temp[0]);
            hashSong.insert(song, temp[1]);
            artistTree.insert(temp[0], temp[1]);
            songTree.insert(temp[1], temp[0]);
        }
        else if (temp[0] != null && temp[1] == null)//artist inserted - song duplicate
        {
            hashArtist.insert(art, temp[0]);
            artistTree.insert(temp[0], arr.searchAndReturn(song));
            songTree.insert(arr.searchAndReturn(song), temp[0]);
        }
        else if (temp[0] != null && temp[1] == null)//artist duplicate - song inserted
        {
            hashSong.insert(song, temp[1]);
            artistTree.insert(arr.searchAndReturn(art), temp[1]);
            songTree.insert(temp[1], arr.searchAndReturn(art));
        }
        else //both duplicates
        {
            artistTree.insert(arr.searchAndReturn(art), arr.searchAndReturn(song));
            songTree.insert(arr.searchAndReturn(song), arr.searchAndReturn(art));
        }

    }
}
