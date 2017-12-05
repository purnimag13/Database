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
    Scanner scan;
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
    public DataBase(int hashSize, int blockSize)
    {
        massiveByteArr = new ArrayList<>();
        arr = new MemoryManager();
        handArr = arr.findHandle();
        hashArtist = new HashTable(hashSize);
        hashSong = new HashTable(hashSize);
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
        scan = new Scanner(file);
        while (scan.hasNextLine() && scan.hasNext())
        {
            if (!scan.hasNext()) 
            {
                break;
            }
            String lineScanner = scan.nextLine();
            Scanner fileScanner = new Scanner(lineScanner);
            String instruction = fileScanner.next();
            
            if (instruction.equals("insert"))
            {
                StringBuilder artist = new StringBuilder();
                StringBuilder songTitle = new StringBuilder();
                StringBuilder tempString = new StringBuilder();
                
                while (!fileScanner.next().contains("<SEP>"))
                {
                    artist.append(fileScanner.next());
                }
                while (fileScanner.hasNext())
                {
                    tempString.append(fileScanner.next()); 
                }
                String temp = tempString.toString();
                
                temp.replace("<SEP>", " ");
                
                String sub = temp.substring(0, temp.indexOf(" "));
                artist.append(sub);
                String sub2 = temp.substring(temp.indexOf(" ") + 1, temp.length());
                String artistString = artist.toString();
                songTitle.append(sub2);
                String songTitleString = songTitle.toString();
                insert(artistString, songTitleString);
            }
            else if (instruction.equals("remove"))
            {
                String next = fileScanner.next();
                if (next.equals("artist"))
                {
                    removeArtist(fileScanner.next());
                }
                else if (next.equals("song"))
                {
                    removeSong(fileScanner.next());
                }
            }
            else if (instruction.equals("print"))
            {
                String next = fileScanner.next();
                if (next.equals("artist"))
                {
                    printArtist();
                }
                else if (next.equals("song"))
                {
                    printSong();
                }
                else if (next.equals("tree"))
                {
                    //in order traversal of tree
                    System.out.println("Printing artist tree: ");
                    artistTree.printTree();
                    System.out.println("Printing song tree: ");
                    songTree.printTree();
                }
            }
            else if (instruction.equals("list"))
            {
                String next = fileScanner.next();
                if (next.equals("artist"))
                {
                    HashTable.Entry[] temp = hashSong.getTable();
                    String artist = fileScanner.next();
                    ArrayList<Handle> songs = listArtist(artist);//list of song handles
                    if (songs.size() == 0)
                    {
                        System.out.println("|" + artist + "| does not exist in the Artist database.");
                    }
                    else
                    {
                        for (int i = 0; i < temp.length; i++)
                        {
                            if (songs.get(i) == temp[i].getValue())
                            {
                                System.out.println(temp[i].toString());
                            }
                        }
                    }
                }
                else if (next.equals("song"))
                {
                    HashTable.Entry[] temp = hashArtist.getTable();
                    String songs = fileScanner.next();
                    ArrayList<Handle> artists = listSong(songs);
                    if (artists.size() == 0)
                    {
                        System.out.println("|" + songs + "| does not exist in the Song database."); 
                    }
                    else
                    {
                        for (int i = 0; i < temp.length; i++)
                        {
                            if (artists.get(i) == temp[i].getValue())
                            {
                                System.out.println(temp[i].toString());
                            }
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
        scan.close();
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
        System.out.println("total songs: " + hashSong.getSize());
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
        System.out.println("total artists: " + hashArtist.getSize());
    }
    /**
     * lists all the songs by this artist
     * @param s artist searched
     * @return ArrayList<Handle> to find the songs
     */
    public ArrayList<Handle> listArtist(String s)
    {
        Handle temp = arr.searchAndReturn(s);
        if (temp.equals(null))
        {
            System.out.println("|" + s + "| does not exist in the Artist database.");  
        }
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

            boolean checkArt = artistTree.delete(tempArtist, tempSong);
            boolean checkSong = songTree.delete(tempSong, tempArtist);

            if (!checkArt && !checkSong)
            {
                System.out.println("The KVPair (|" + tempArtist + "|,|" + tempSong + "|) was not found in the database.");
                System.out.println("The KVPair (|" + tempSong + "|,|" + tempArtist + "|) was not found in the database.");
            }
            else
            {
                System.out.println("The KVPair (|" + tempArtist + "|,|" + tempSong + "|) was deleted from the database.");
                System.out.println("The KVPair (|" + tempSong + "|,|" + tempArtist + "|) was deleted from the database."); 
            }

            if (artistTree.countHandles(tempArtist) == 0)
            {
                arr.remove(art);
                hashArtist.remove(art);
                System.out.println("|" + art + "| is deleted from the Artist database.");
            }
            if (songTree.countHandles(tempSong) == 0)
            {
                arr.remove(title);
                hashSong.remove(title);
                System.out.println("|" + title + "| is deleted from the Song database.");
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
