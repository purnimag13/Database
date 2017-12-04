import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Reads the input and outputs 
 * the proper things
 * @author Purnima Gosh
 * @author taralaughlin
 * @version 12.02.17
 *
 */
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
    /**
     * database constructor
     */
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
    /**
     * reads the file and does the proper actions
     * depending
     * @param fileName file to be read in
     * @throws FileNotFoundException exception thrown
     */
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
                    printArtist();
                }
                else if (fileScanner.next() == "song")
                {
                    printSong();
                }
                else if (fileScanner.next() == "tree")
                {
                    //in order traversal of tree
                    System.out.println("Printing artist tree: ");
                    System.out.println("Printing song tree: ");
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
    /**
     * prints in order traversal of tree
     * @param tree
     */
    public void printTree(KVTree tree)
    {

    }
    /**
     * prints all of the songs in the hash table
     */
    public void printSong()
    {
        int length = hashSong.getTable().length;
        for (int i = 0; i < length; i++)
        {
            hashSong.print();
        }
        System.out.println("total songs: " + hashSong.getTable().length);
    }
    /**
     * prints all the artists in the hash table
     */
    public void printArtist()
    {
        int length = hashArtist.getTable().length;
        for (int i = 0; i < length; i++)
        {
            hashArtist.print();
        }
        System.out.println("total artists: " + hashArtist.getTable().length);
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
        Handle offArtK;
        Handle offArtV;
        Handle offSongK;
        Handle offSongV;
        Handle temp = arr.searchAndReturn(obj);//handle of artist looked for
        if (!hashArtist.find(obj))
        {
            System.out.println(hashArtist.get(obj).toString() + " does not exist in the artist database.");
        }
        else
        {
            arr.remove(obj);
            hashArtist.remove(obj);

            ArrayList<KVPair> deletedArt =  artistTree.remove(temp);//every KV pair associated
            ArrayList<KVPair> deletedSong = songTree.remove(temp);

            for (int i = 0; i < deletedArt.size(); i++)
            {
                offArtK = deletedArt.get(i).getKey();
                offArtV = deletedArt.get(i).getValue();
                offSongK = deletedSong.get(i).getKey();
                offSongV = deletedSong.get(i).getValue();
                String a1 = hashArtist.getName(offArtK);
                String a2 = hashArtist.getName(offArtV);
                String a3 = hashSong.getName(offSongK);
                String a4 = hashSong.getName(offSongV);
                System.out.println("The KVPair (|" + a1 + "|,|" + a2 + "|) is deleted from the tree.");
                System.out.println("The KVPair (|" + a3 + "|,|" + a4 + "|) is deleted from the tree.");
            }
            System.out.println("|" + obj + "| is deleted from the Artist database.");
            for (int i = 0; i < deletedArt.size(); i++)
            {
                if (artistTree.countHandles(deletedArt.get(i).getValue()) == 0)
                {
                    String songDeleted = hashSong.getName(deletedArt.get(i).getValue());
                    arr.remove(songDeleted);
                    hashSong.remove(songDeleted);
                    System.out.println("|" + songDeleted + "| is deleted from the Song database.");
                }
            }
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
        }

        return true;
    }
    /**
     * removes all songs from everything
     * @param obj song to be removed
     */
    public void removeSong(String obj)
    {
        Handle offArtK;
        Handle offArtV;
        Handle offSongK;
        Handle offSongV;
        Handle temp = arr.searchAndReturn(obj);//handle of artist looked for
        if (!hashSong.find(obj))
        {
            System.out.println(hashSong.get(obj).toString() + " does not exist in the song database.");
        }
        else
        {
            arr.remove(obj);
            hashSong.remove(obj);

            ArrayList<KVPair> deletedArt =  artistTree.remove(temp);//every KV pair associated
            ArrayList<KVPair> deletedSong = songTree.remove(temp);

            for (int i = 0; i < deletedArt.size(); i++)
            {
                offArtK = deletedArt.get(i).getKey();
                offArtV = deletedArt.get(i).getValue();
                offSongK = deletedSong.get(i).getKey();
                offSongV = deletedSong.get(i).getValue();
                String a1 = hashArtist.getName(offArtK);
                String a2 = hashArtist.getName(offArtV);
                String a3 = hashSong.getName(offSongK);
                String a4 = hashSong.getName(offSongV);
                System.out.println("The KVPair (|" + a1 + "|,|" + a2 + "|) is deleted from the tree.");
                System.out.println("The KVPair (|" + a3 + "|,|" + a4 + "|) is deleted from the tree.");
            }
            System.out.println("|" + obj + "| is deleted from the Song database.");
            for (int i = 0; i < deletedArt.size(); i++)
            {
                if (songTree.countHandles(deletedArt.get(i).getValue()) == 0)
                {
                    String songDeleted = hashArtist.getName(deletedArt.get(i).getValue());
                    arr.remove(songDeleted);
                    hashArtist.remove(songDeleted);
                    System.out.println("|" + songDeleted + "| is deleted from the Artist database.");
                }
            }
        }
    }
    /**
     * inserts to the array
     * hash table and tree
     * @param art artist name
     * @param song song title
     */
    public void insert(String art, String song)
    {
        Handle[] temp = arr.add(art, song); 
        if (temp[0] != null && temp[1] != null)//artist and song both inserted
        {
            hashArtist.insert(art, temp[0]);
            System.out.println("|" + art + "| is added to the Artist database.");

            hashSong.insert(song, temp[1]);
            System.out.println("|" + song + "| is added to the Song database.");

            if (artistTree.hasKVPair(temp[0], temp[1]))
            {
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + temp[0].getOff() + "," + temp[1].getOff() + ") duplicates a record already in the tree.");
                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + temp[1].getOff() + "," + temp[0].getOff() + ") duplicates a record already in the tree.");
            }
            else
            {
                artistTree.insert(temp[0], temp[1]);
                songTree.insert(temp[1], temp[0]);
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + temp[0].getOff() + "," + temp[1].getOff() + ") is added to the tree.");

                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + temp[1].getOff() + "," + temp[0].getOff() + ") is added to the tree.");
            }

        }
        else if (temp[0] != null && temp[1] == null)//artist inserted - song duplicate
        {
            hashArtist.insert(art, temp[0]);
            System.out.println("|" + art + "| is added to the Artist database.");
            System.out.println("|" + song + "| duplicates a record already in the Song database");

            if (artistTree.hasKVPair(temp[0], temp[1]))
            {
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + temp[0].getOff() + "," + temp[1].getOff() + ") duplicates a record already in the tree.");
                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + temp[1].getOff() + "," + temp[0].getOff() + ") duplicates a record already in the tree.");
            }
            else
            {
                artistTree.insert(temp[0], temp[1]);
                songTree.insert(temp[1], temp[0]);
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + temp[0].getOff() + "," + temp[1].getOff() + ") is added to the tree.");

                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + temp[1].getOff() + "," + temp[0].getOff() + ") is added to the tree.");
            }
        }
        else if (temp[0] != null && temp[1] == null)//artist duplicate - song inserted
        {
            hashSong.insert(song, temp[1]);

            System.out.println("|" + art + "| duplicates a record already in the Artist database");
            System.out.println("|" + song + "| is added to the Song database.");

            if (artistTree.hasKVPair(temp[0], temp[1]))
            {
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + temp[0].getOff() + "," + temp[1].getOff() + ") duplicates a record already in the tree.");
                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + temp[1].getOff() + "," + temp[0].getOff() + ") duplicates a record already in the tree.");
            }
            else
            {
                artistTree.insert(temp[0], temp[1]);
                songTree.insert(temp[1], temp[0]);
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + temp[0].getOff() + "," + temp[1].getOff() + ") is added to the tree.");

                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + temp[1].getOff() + "," + temp[0].getOff() + ") is added to the tree.");
            }
        }
        else //both duplicates
        {
            System.out.println("|" + art + "| duplicates a record already in the Artist database");
            System.out.println("|" + song + "| duplicates a record already in the Song database");

            if (artistTree.hasKVPair(temp[0], temp[1]))
            {
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + temp[0].getOff() + "," + temp[1].getOff() + ") duplicates a record already in the tree.");
                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + temp[1].getOff() + "," + temp[0].getOff() + ") duplicates a record already in the tree.");
            }
            else
            {
                artistTree.insert(temp[0], temp[1]);
                songTree.insert(temp[1], temp[0]);
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + temp[0].getOff() + "," + temp[1].getOff() + ") is added to the tree.");

                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + temp[1].getOff() + "," + temp[0].getOff() + ") is added to the tree.");
            }
        }

    }
}
