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


        hashArtist = new HashTable(hashSize);
        hashSong = new HashTable(hashSize);
        arr = new MemoryManager(hashArtist, hashSong);
        handArr = arr.findHandle();
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

                String next = fileScanner.next();
                while (!next.contains("<SEP>"))
                {
                    artist.append(next);
                    if (fileScanner.hasNext())
                    {
                        artist.append(" ");
                    }
                    next = fileScanner.next();
                }
                tempString.append(next);
                tempString.append(" ");
                while (fileScanner.hasNext())
                {
                    tempString.append(fileScanner.next());
                    if (fileScanner.hasNext())
                    {
                        tempString.append(" ");
                    }
                }
                String str = tempString.toString();

                String temp = str.replace("<SEP>", "  ");

                String sub = temp.substring(0, temp.indexOf("  "));
                artist.append(sub);
                String sub2 = temp.substring(temp.indexOf("  ") + 2, temp.length());
                String artistString = artist.toString();
                songTitle.append(sub2);
                String songTitleString = songTitle.toString();
                artistString = artistString.trim();
                songTitleString = songTitleString.trim();
                insert(artistString, songTitleString);
            }
            else if (instruction.equals("remove"))
            {
                String next = fileScanner.next();
                if (next.equals("artist"))
                {
                    StringBuilder str = new StringBuilder();
                    while (fileScanner.hasNext())
                    { 
                        String next1 = fileScanner.next();
                        str.append(next1);
                        if (fileScanner.hasNext())
                        {
                            str.append(" ");
                        }
                    }
                    String obj = str.toString();
                    obj = obj.trim();
                    removeArtist(obj);
                }
                else if (next.equals("song"))
                {
                    StringBuilder str = new StringBuilder();
                    while (fileScanner.hasNext())
                    {     
                        String next1 = fileScanner.next();
                        str.append(next1);
                        if (fileScanner.hasNext())
                        {
                            str.append(" ");
                        }
                    }
                    String obj = str.toString();
                    obj = obj.trim();
                    removeSong(obj);
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
                    System.out.println("Printing artist tree:");
                    artistTree.printTree();
                    System.out.println("Printing song tree:");
                    songTree.printTree();
                }
            }
            else if (instruction.equals("list"))
            {
                String next = fileScanner.next();
                if (next.equals("artist"))
                {
                    StringBuilder str = new StringBuilder();
                    while (fileScanner.hasNext())
                    {                        
                        str.append(fileScanner.next());
                        if (fileScanner.hasNext())
                        {
                            str.append(" ");
                        }
                    }
                    String artist = str.toString(); 
                    artist = artist.trim();
                    ArrayList<Handle> songs = listArtist(artist);//list of song handles
                    if (songs.size() == 0)
                    {
                        System.out.println("|" + artist + "| does not exist in the artist database.");
                    }
                    else
                    {
                        for (int i = 0; i < songs.size(); i++)
                        {
                            //get the string associated w handle
                            String songy = this.findSong(songs.get(i).getOff() + 3, songs.get(i).getLen());
                            System.out.println("|" + songy + "|");
                        }
                    }
                }
                else if (next.equals("song"))
                {
                    StringBuilder str = new StringBuilder();
                    while (fileScanner.hasNext())
                    {                        
                        str.append(fileScanner.next());
                        if (fileScanner.hasNext())
                        {
                            str.append(" ");
                        }
                    }
                    String songs = str.toString();  
                    songs = songs.trim();
                    ArrayList<Handle> artists = listSong(songs);
                    if (artists.size() == 0)
                    {
                        System.out.println("|" + songs + "| does not exist in the song database."); 
                    }
                    else
                    {
                        for (int i = 0; i < artists.size(); i++)
                        {
                            //get the string associated w handle
                            String songy = this.findSong(artists.get(i).getOff() + 3, artists.get(i).getLen());
                            System.out.println("|" + songy + "|");
                        }
                    }
                }
            }
            else if (instruction.equals("delete"))
            {
                StringBuilder artist = new StringBuilder();
                StringBuilder songTitle = new StringBuilder();
                StringBuilder tempString = new StringBuilder();

                String next = fileScanner.next();
                while (!next.contains("<SEP>"))
                {
                    artist.append(next);
                    if (fileScanner.hasNext())
                    {
                        artist.append(" ");
                    }
                    next = fileScanner.next();
                }
                tempString.append(next);
                tempString.append(" ");
                while (fileScanner.hasNext())
                {
                    tempString.append(fileScanner.next());
                    if (fileScanner.hasNext())
                    {
                        tempString.append(" ");
                    }
                }
                String str = tempString.toString();

                String temp = str.replace("<SEP>", "  ");

                String sub = temp.substring(0, temp.indexOf("  "));
                artist.append(sub);
                String sub2 = temp.substring(temp.indexOf("  ") + 2, temp.length());
                String artistString = artist.toString();
                songTitle.append(sub2);
                String songTitleString = songTitle.toString();
                artistString = artistString.trim();
                songTitleString = songTitleString.trim();
                delete(artistString, songTitleString);
                //                StringBuilder str = new StringBuilder();
                //                str.append(fileScanner.next());
                //                str.append(" ");
                //                str.append(fileScanner.next());
                //                String artistName = str.toString();
                //                fileScanner.skip("<SEP>");
                //                String songName = fileScanner.next();
                //                delete(artistName, songName);
            }
            fileScanner.close();
        }
        scan.close();
    }
    /**
     * prints all of the songs in the hash table
     */
    public void printSong()
    {
        hashSong.print();        
        System.out.println("total songs: " + hashSong.getSize());
    }
    /**
     * prints all the artists in the hash table
     */
    public void printArtist()
    {
        hashArtist.print();
        System.out.println("total artists: " + hashArtist.getSize());
    }
    /**
     * lists all the songs by this artist
     * @param s artist searched
     * @return ArrayList<Handle> to find the songs
     */
    public ArrayList<Handle> listArtist(String s)
    {
        Handle temp = hashArtist.get(s);
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
        Handle temp = hashSong.get(s);
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
        //Handle temp = arr.searchAndReturn(obj);//handle of artist looked for
        Handle temp = hashArtist.get(obj);
        if (!hashArtist.find(obj))
        {
            System.out.println("|" + obj + "| does not exist in the artist database.");
        }
        else
        {
            arr.remove(obj);
            hashArtist.remove(obj);

            ArrayList<KVPair> deletedArt =  artistTree.remove(temp);//every KV pair associated
            ArrayList<KVPair> deletedSong = songTree.removeAlternate(temp); // needs to compare values here

            for (int i = 0; i < deletedArt.size(); i++)
            {
                offArtK = deletedArt.get(i).getKey();
                offArtV = deletedArt.get(i).getValue();

                offSongK = deletedSong.get(i).getKey();
                offSongV = deletedSong.get(i).getValue();

                int artKeyOffset = offArtK.getOff() + 3;
                int artKeyLength = offArtK.getLen();
                
                String artistName = this.findSong(artKeyOffset, artKeyLength);
                
                int songValueOffset = offArtV.getOff() + 3;
                int songValueLength = offArtV.getLen();
                
                String songTitle = this.findSong(songValueOffset, songValueLength);

                System.out.println("The KVPair (|" + artistName + "|,|" + songTitle + "|) is deleted from the tree.");
                System.out.println("The KVPair (|" + songTitle + "|,|" + artistName + "|) is deleted from the tree.");
            }
            System.out.println("|" + obj + "| is deleted from the Artist database.");
            for (int i = 0; i < deletedArt.size(); i++)
            {
                if (artistTree.countHandles(deletedArt.get(i).getValue()) == 0)
                {
                    int x = deletedArt.get(i).getValue().getOff() + 3;
                    int y = deletedArt.get(i).getValue().getLen();
                    String songDeleted = this.findSong(x, y);
                    arr.remove(songDeleted);
                    hashSong.remove(songDeleted);
                    System.out.println("|" + songDeleted + "| is deleted from the Song database.");
                }
            }
        }
    }
    public String findSong(int offset, int len)
    {
        String songName = "";
        for (int i = 0; i < len; i++)
        {
            songName = songName + (char)(this.arr.massiveByteArr.get(i + offset) & 0xFF);
        }
        return songName;
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
        Handle temp = hashSong.get(obj);
        if (!hashSong.find(obj))
        {
            System.out.println("|" + obj + "| does not exist in the song database.");
        }
        else
        {
            arr.remove(obj);
            hashSong.remove(obj);

            ArrayList<KVPair> deletedArt =  artistTree.removeAlternate(temp);//every KV pair associated
            ArrayList<KVPair> deletedSong = songTree.remove(temp);

            for (int i = 0; i < deletedSong.size(); i++)
            {
                offArtK = deletedArt.get(i).getKey();
                offArtV = deletedArt.get(i).getValue();
                offSongK = deletedSong.get(i).getKey();
                offSongV = deletedSong.get(i).getValue();
                
                int artKeyOffset = offArtK.getOff() + 3;
                int artKeyLength = offArtK.getLen();
                
                String artistName = this.findSong(artKeyOffset, artKeyLength);
                
                int songValueOffset = offArtV.getOff() + 3;
                int songValueLength = offArtV.getLen();
                
                String songTitle = this.findSong(songValueOffset, songValueLength);
                System.out.println("The KVPair (|" + songTitle + "|,|" + artistName + "|) is deleted from the tree.");
                System.out.println("The KVPair (|" + artistName + "|,|" + songTitle + "|) is deleted from the tree.");
            }
            System.out.println("|" + obj + "| is deleted from the Song database.");
            for (int i = 0; i < deletedSong.size(); i++)
            {
                if (songTree.countHandles(deletedSong.get(i).getValue()) == 0)
                {
                    int x = deletedSong.get(i).getValue().getOff() + 3;
                    int y = deletedSong.get(i).getValue().getLen();
                    String songDeleted = this.findSong(x, y);
                    arr.remove(songDeleted);
                    hashArtist.remove(songDeleted);
                    System.out.println("|" + songDeleted + "| is deleted from the Artist database.");
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
        boolean hasArt = true;
        boolean hasSong = true;
        if (!hashArtist.find(art))
        {
            System.out.println("|" + art + "| does not exist in the artist database.");
            hasArt = false;
        }
        if (!hashSong.find(title))
        {
            System.out.println("|" + title + "| does not exist in the song database.");
            hasSong = false;
        }
        if (!hasArt || !hasSong)
        {
            return false;
        }
        //        if (arr.searchAndReturn(art) != null && arr.searchAndReturn(title) != null)
        //        {
        tempArtist = hashArtist.get(art);
        tempSong = hashSong.get(title);
        //tempArtist = arr.searchAndReturn(art);
        //tempSong = arr.searchAndReturn(title);

        boolean checkArt = artistTree.delete(tempArtist, tempSong);
        boolean checkSong = songTree.delete(tempSong, tempArtist);

        if (!checkArt && !checkSong)
        {
            System.out.println("The KVPair (|" + art + "|,|" + title + "|) was not found in the database.");
            System.out.println("The KVPair (|" + title + "|,|" + art + "|) was not found in the database.");
        }
        else
        {
            System.out.println("The KVPair (|" + art + "|,|" + title + "|) is deleted from the tree.");
            System.out.println("The KVPair (|" + title + "|,|" + art + "|) is deleted from the tree."); 
        }

        if (artistTree.countHandles(tempArtist) == 0)
        {
            arr.remove(art);
            hashArtist.remove(art);
            System.out.println("|" + art + "| is deleted from the artist database.");
        }
        if (songTree.countHandles(tempSong) == 0)
        {
            arr.remove(title);
            hashSong.remove(title);
            System.out.println("|" + title + "| is deleted from the song database.");
        }
        //        }
        return true;
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
        if (art.equals(song))
        {
            temp[1] = temp[0];
        }
        if (temp[0] == null)
        {
            temp[0] = hashSong.get(art);
        }
        if (temp[1] == null)
        {
            temp[1] = hashArtist.get(song);
        }
        if (temp[0] != null && temp[1] != null)//artist and song both inserted
        {
            hashArtist.insert(art, temp[0]);
            System.out.println("|" + art + "| is added to the Artist database.");

            hashSong.insert(song, temp[1]);
            System.out.println("|" + song + "| is added to the Song database.");

            artistTree.insert(temp[0], temp[1]);
            songTree.insert(temp[1], temp[0]);
            System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                    + temp[0].getOff() + "," + temp[1].getOff() + ") is added to the tree.");

            System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                    + temp[1].getOff() + "," + temp[0].getOff() + ") is added to the tree.");


        }
        else if (temp[0] != null && temp[1] == null)//artist inserted - song duplicate (null)
        {
            hashArtist.insert(art, temp[0]);
            System.out.println("|" + art + "| is added to the Artist database.");
            System.out.println("|" + song + "| duplicates a record already in the Song database.");
            Handle oldSong = hashSong.get(song);

            artistTree.insert(temp[0], oldSong);
            songTree.insert(oldSong, temp[0]);
            System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                    + temp[0].getOff() + "," + oldSong.getOff() + ") is added to the tree.");

            System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                    + oldSong.getOff() + "," + temp[0].getOff() + ") is added to the tree.");

        }
        else if (temp[0] == null && temp[1] != null)//artist duplicate - song inserted
        {
            hashSong.insert(song, temp[1]);
            Handle oldArtist = hashArtist.get(art);

            System.out.println("|" + art + "| duplicates a record already in the Artist database.");
            System.out.println("|" + song + "| is added to the Song database.");

            artistTree.insert(oldArtist, temp[1]);
            songTree.insert(temp[1], oldArtist);
            System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                    + oldArtist.getOff() + "," + temp[1].getOff() + ") is added to the tree.");

            System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                    + temp[1].getOff() + "," + oldArtist.getOff() + ") is added to the tree.");

        }
        else //both duplicates
        {
            Handle songIndex = hashSong.get(song);
            Handle artistIndex = hashArtist.get(art);
            System.out.println("|" + art + "| duplicates a record already in the Artist database.");
            System.out.println("|" + song + "| duplicates a record already in the Song database.");                       

            if (artistTree.hasKVPair(artistIndex, songIndex))
            {
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + artistIndex.getOff() + "," + songIndex.getOff() + ") duplicates a record already in the tree.");
                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + songIndex.getOff() + "," + artistIndex.getOff() + ") duplicates a record already in the tree.");
            }
            else
            {
                artistTree.insert(artistIndex, songIndex);
                songTree.insert(songIndex, artistIndex);
                System.out.println("The KVPair (|" + art + "|,|" + song + "|),(" 
                        + artistIndex.getOff() + "," + songIndex.getOff() + ") is added to the tree.");

                System.out.println("The KVPair (|" + song + "|,|" + art + "|),(" 
                        + songIndex.getOff() + "," + artistIndex.getOff() + ") is added to the tree.");
            }

        }
    }
}
