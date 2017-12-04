import java.util.ArrayList;
import java.util.Arrays;

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
        if (findIndexOfHandle(art) == -1)//if u cant find it in the array add
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
        if (findIndexOfHandle(song) == -1)
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
            //int x = massiveByteArr.indexOf(offsets.get(i));
            int x = offsets.get(i);
            test = true;
            byte[] temp = Arrays.copyOfRange(tempArr, x, len + x);
            for (int k = 0; k < temp.length - 1; k++)
            {
                if (temp[k] != stringAsBytes[k])
                {
                    test = false;
                }
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
     * @param obj
     * @param list
     * @return
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
     * @param art
     * @param song
     */
    public boolean remove(String s)
    {
        if (findIndexOfHandle(s) != -1)
        {
            byte[] stringAsBytes = s.getBytes();
            int index = findIndexOfHandle(s);//we may need to check for duplicate string bytes
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
        int index = findIndexOfHandle(s);//we may need to check for duplicate string bytes
        if (massiveByteArr.get(index) == 0)
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
        int off = findIndexOfHandle(s);
        Handle temp = new Handle(off, lengthOfByteArr + 2);
        return handleArr.get(handleArr.indexOf(temp));
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
        int end = massiveByteArr.size();
        massiveByteArr.add((byte) flag);
        massiveByteArr.add((byte) lengthOfRecord);
        for (int i = 0; i < lengthOfByteArr; i++)
        {
            massiveByteArr.add(stringAsBytes[i]);
        }
        return end;
    }

}
