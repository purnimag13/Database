import java.util.ArrayList;

public class MemoryManager 
{
    ArrayList<Byte> massiveByteArr;
    ArrayList<Handle> handleArr;

    public MemoryManager()
    {
        massiveByteArr = new ArrayList<>();
        handleArr = new ArrayList<>();
    }
    /**
     * gives other things access to the list of handles
     * @return
     */
    public ArrayList<Handle> findHandle()
    {
        return handleArr;
    }
    /**
     * adds the bytes to byte arr
     * @param art
     * @param song
     * @return the handles that were just added to make it
     * easier to add the KVPairs and hash table handles
     * will return null if nothing was changed
     */
    public Handle[] add(String art, String song)
    {
        Handle[] pair = new Handle[2];
        Handle artistHandle;
        Handle songHandle;
        if (!searchArray(art))//if u cant find it in the array add
        {
            int off = addToArray(art);
            artistHandle = makeNewHandle(art, off);
            handleArr.add(artistHandle);
            pair[0] = artistHandle;
        }
        else // if you can find it, check the flag
        {
            if (!checkFlagValid(art))
            {
                int off = addToArray(art);
                artistHandle = makeNewHandle(art, off);
                handleArr.add(artistHandle); 
                pair[0] = artistHandle;
            }
        }
        if (!searchArray(song))
        {
            int off = addToArray(song);
            songHandle = makeNewHandle(song, off);
            handleArr.add(songHandle);
            pair[1] = songHandle;
        }
        else
        {
            if (!checkFlagValid(song))
            {
                int off = addToArray(song);
                songHandle = makeNewHandle(song, off);
                handleArr.add(songHandle); 
                pair[1] = songHandle;
            }
        }
        return pair;
    }
    
    public int findIndexOfHandle(String s)
    {
        byte[] stringAsBytes = s.getBytes();
        if (massiveByteArr.contains(stringAsBytes[0]))
        {
            for (int i = 0; i < massiveByteArr.size() - 2; i++)
            {
                if (i == stringAsBytes[0] && i + 1 == stringAsBytes[1] &&
                        i + 2 == stringAsBytes[2])
                {
                    return i - 2;
                }
            }
        }
        return -1;
    }
    /**
     * this works under the assumption that 
     * the handles are not used elsewhere
     * finds flag and changes it to zero
     * removes the old handle from the handle array 
     * to avoid confusion when a new one is added
     * @param art
     * @param song
     */
    public boolean remove(String s)
    {
        if (searchArray(s))
        {
            byte[] stringAsBytes = s.getBytes();
            int index = massiveByteArr.indexOf(stringAsBytes[0]);//we may need to check for duplicate string bytes
            massiveByteArr.set(massiveByteArr.get(index - 2), (byte) 0);
            Handle temp = new Handle(massiveByteArr.indexOf(massiveByteArr.get(index - 2)),
                    stringAsBytes.length + 2);
            if (handleArr.contains(temp))
            {
                handleArr.remove(temp);
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
        byte[] stringAsBytes = s.getBytes();
        int index = massiveByteArr.indexOf(stringAsBytes[0]);//we may need to check for duplicate string bytes
        if (massiveByteArr.get(index - 2) == 0)
        {
            return false;
        }
        return true;

    }
    /**
     * makes a new handle with the information provided
     * aka the string that is the artist or song 
     * @param s
     * @param offset
     * @return
     */
    public Handle makeNewHandle(String s, int offset)
    {
        byte[] stringAsBytes = s.getBytes();
        int lengthOfByteArr = stringAsBytes.length;
        Handle temp = new Handle(offset, lengthOfByteArr + 2);
        return temp;
    }
    /**
     * returns the handle of the string that
     * is being searched for
     * @param s
     * @return
     */
    public Handle searchAndReturn(String s)
    {
        byte[] stringAsBytes = s.getBytes();
        int lengthOfByteArr = stringAsBytes.length;
        int off = massiveByteArr.indexOf(stringAsBytes[0]) - 2;
        Handle temp = new Handle(off, lengthOfByteArr + 2);
        return handleArr.get(handleArr.indexOf(temp));
    }
    /**
     * searches the entire array
     * to see if the handle exists in it
     * @param s
     * @return
     */
    public boolean searchArray(String s)
    {
        byte[] stringAsBytes = s.getBytes();
        int lengthOfByteArr = stringAsBytes.length;
        if (massiveByteArr.contains(stringAsBytes[0]))
        {
            for (int w = massiveByteArr.indexOf(stringAsBytes[0]); w < lengthOfByteArr; w++)
            {
                int k = 0;
                if (massiveByteArr.get(w) != stringAsBytes[k])
                {
                    break;
                }
                k++;
            }
            return true;
        }
        return false;
    }
    /**
     * returns the offset of what was just added to the byte array
     * @param s
     * @return
     */
    public int addToArray(String s)
    {
        byte[] stringAsBytes = s.getBytes();
        int lengthOfByteArr = stringAsBytes.length;
        int flag = 1; 
        int lengthOfRecord = lengthOfByteArr + 2; 
        massiveByteArr.add((byte) flag);
        massiveByteArr.add((byte) lengthOfRecord);
        for (int i = 0; i < lengthOfByteArr; i++)
        {
            massiveByteArr.add(stringAsBytes[i]);
        }
        return massiveByteArr.indexOf(flag);
    }

}
