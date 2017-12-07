import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * keeps track of the big byte array 
 * @author purnima Gosh
 * @author taralaughlin
 * @version 12.05.2017
 */
public class MemoryManager 
{
    ArrayList<Byte> massiveByteArr;
    ArrayList<Handle> handleArr;
    HashTable artHash;
    HashTable songHash;

    /**
     * memory manager constructor
     */
    public MemoryManager(HashTable art, HashTable song)
    {
        massiveByteArr = new ArrayList<>();
        handleArr = new ArrayList<>();
        artHash = art;
        songHash = song;
    }
    /**
     * gives other things access to the list of handles
     * @return ArrayList<Handle> of the handles
     */
    public ArrayList<Handle> findHandle()
    {
        return handleArr;
    }
    /**
     * adds the bytes to byte arr
     * @param art artists to add
     * @param song songs to add
     * @return the handles that were just added to make it
     * easier to add the KVPairs and hash table handles
     * will return null if nothing was changed
     */
    public Handle[] add(String art, String song)
    {
        Handle[] pair = new Handle[2];
        Handle artistHandle;
        Handle songHandle;
        if (!artHash.find(art))//if u cant find it in the array add
        {
            int off = addToArray(art);
            artistHandle = makeNewHandle(art, off);
            handleArr.add(artistHandle);
            pair[0] = artistHandle;
        }
        else // if you can find it, check the flag
        {
//            if (!checkFlagValid(art))
//            {
//                int off = addToArray(art);
//                artistHandle = makeNewHandle(art, off);
//                handleArr.add(artistHandle); 
//                pair[0] = artistHandle;
//            }
            pair[0] = null;
        }
        if (!songHash.find(song))
        {
            int off = addToArray(song);
            songHandle = makeNewHandle(song, off);
            handleArr.add(songHandle);
            pair[1] = songHandle;
        }
        else
        {
//            if (!checkFlagValid(song))
//            {
//                int off = addToArray(song);
//                songHandle = makeNewHandle(song, off);
//                handleArr.add(songHandle); 
//                pair[1] = songHandle;
//            }
            pair[1] = null;
        }
        return pair;
    }
    /**
     * finds the index of the string 
     * were looking for
     * @param s string to be searched
     * @return the int of the index
     */
    public int findIndexOfHandle(String s)
    {
        byte[] tempArr = new byte[massiveByteArr.size()];
        for (int j = 0; j < massiveByteArr.size(); j++)
        {
            tempArr[j] = massiveByteArr.get(j);
        }

        byte[] stringAsBytes = s.getBytes();
        int len = stringAsBytes.length;
        boolean test = true;
        ArrayList<Integer> offsets = indexOfAll(stringAsBytes[0], massiveByteArr);

        for (int i = 0; i < offsets.size(); i++)
        {
            int x = offsets.get(i);
            test = true;
            byte[] temp = Arrays.copyOfRange(tempArr, x, len + x);
            for (int k = 0; k < temp.length; k++)
            {
                if (temp[k] != stringAsBytes[k])
                {
                    test = false;
                }
            }
            int nameLen = (int) tempArr[x - 1];
            if (nameLen != stringAsBytes.length)
            {
                test = false;
            }
            if (test)
            {
                return offsets.get(i);
            }
        }
        return -1;
    }
    /**
     * index of everything
     * @param obj object looked for
     * @param list list searched through
     * @return ArrayList<Integer> of the index of the elements
     */
    private ArrayList<Integer> indexOfAll(Object obj, ArrayList<Byte> list)
    {
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++)
            if(obj.equals(list.get(i)))
                indexList.add(i);
        return indexList;
    }
    /**
     * this works under the assumption that 
     * the handles are not used elsewhere
     * finds flag and changes it to zero
     * removes the old handle from the handle array 
     * to avoid confusion when a new one is added
     * @param art artist to be removed
     * @param song song to be looked at
     * @return boolean true or false
     */
    public boolean remove(String s)
    {
        if (findIndexOfHandle(s) != -1)
        {
            byte[] stringAsBytes = s.getBytes();
            
            int index = findIndexOfHandle(s);//we may need to check for duplicate string bytes
            massiveByteArr.set(index - 2, (byte) 0);
            Handle temp = new Handle(index - 2, stringAsBytes.length + 2);
            for (int i = 0; i < handleArr.size(); i++)
            {
                if (handleArr.get(i).equals(temp))
                {
                    handleArr.remove(i);
                }
            }
            return true;
        }
        return false;
    }
    /**
     * if the handle exists in the array
     * this finds its flag and checks if its valid
     * @param s string searched for
     * @return true if 1 false if 0
     */
    public boolean checkFlagValid(String s)//under assumption we already know handle exists
    {
        int index = findIndexOfHandle(s);//we may need to check for duplicate string bytes
        if (massiveByteArr.get(index - 2) == 0)
        {
            return false;
        }
        return true;
    }
    /**
     * makes a new handle with the information provided
     * aka the string that is the artist or song 
     * @param s string looked at
     * @param offset offset of handle
     * @return the handle record
     */
    public Handle makeNewHandle(String s, int offset)
    {
        byte[] stringAsBytes = s.getBytes();
        int lengthOfByteArr = stringAsBytes.length;
        Handle temp = new Handle(offset, lengthOfByteArr);
        return temp;
    }
    /**
     * returns the handle of the string that
     * is being searched for
     * @param s string searched
     * @return Handle looked for
     */
    public Handle searchAndReturn(String s)
    {
        byte[] stringAsBytes = s.getBytes();
        int lengthOfByteArr = stringAsBytes.length;
        int off = findIndexOfHandle(s);
        if (off != -1)
        {
            Handle temp = new Handle(off - 3, lengthOfByteArr);
            for (int i = 0; i < handleArr.size(); i++)
            {
                if (handleArr.get(i).getLen() == temp.getLen() && handleArr.get(i).getOff() == temp.getOff())
                {
                    return handleArr.get(i); 
                }
            }
        }
        return null;

    }
    /**
     * returns the offset of what was just added to the byte array
     * @param s string to be added
     * @return int of the offset
     */
    public int addToArray(String s)
    {
        byte[] stringAsBytes = s.getBytes();
        int lengthOfByteArr = stringAsBytes.length;
        int flag = 1; 
        int end = massiveByteArr.size();
        massiveByteArr.add((byte) flag);
        byte[] b = new byte[2];
        b[0] = (byte) lengthOfByteArr;
        b[1] = (byte) (lengthOfByteArr >> 8);
        for (int i = 0; i < b.length; i++)
        {
            massiveByteArr.add(b[i]);
        }
        for (int i = 0; i < lengthOfByteArr; i++)
        {
            massiveByteArr.add(stringAsBytes[i]);
        }
        return end;
    }

}
