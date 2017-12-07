/**
 * This is the hash table class
 * @author Purnima Ghosh and Tara Laughlin
 * @version Nov 19, 2017
 *
 * @param <string> the key type
 * @param <Handle> the value type
 */
public class HashTable
{
    public Handle[] table;

    private int capacity;

    private int size;
    
    private DataBase data;


    public HashTable(int initSize, DataBase d)
    {
        data = d;
        table = new Handle[initSize];
        capacity = initSize;
        size = 0;
    } 
    /**
     * getter method for table
     * @return table which is an entry[]
     */
    public Handle[] getTable()
    {
        return table;
    }
    /**
     * Hashing function to get the location for the 
     * record to be inserted
     * @param s the key of the record being inserted
     * @param m the capacity of the hash table
     * @return the slot where the value should be hashed
     */
    public int hashFunc(String s, int m)
    {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++)
        {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++)
            {
                sum += c[k] * mult;
                mult *= 256;
            }
        }
        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++)
        {
            sum += c[k] * mult;
            mult *= 256;
        }
        return (int)(Math.abs(sum) % m);
    }
    /**
     * returns true if the key exists in the hash table
     * @param key key being searched for
     * @return true or false depending 
     */
    public boolean find(String key)
    {   

        int hashSlot = hashFunc(key, capacity);
        int probeCount = 0;
        while (table[hashSlot] != null)
        {
            probeCount++;
            if (data.findSong(table[hashSlot].getOff() + 3, 
                    table[hashSlot].getLen()).equals(key))
            {
                return true;
            }
            hashSlot = (hashSlot + (probeCount * probeCount)) % capacity;
        }
        return false;
    }

    /**
     * Gets the handle when provided a key value
     * @param key the key being searched for
     * @return the handle stored for that key value
     */
    public Handle get(String key)
    {
        Handle h = null;
        int hashSlot = hashFunc(key, capacity);
        int probeCount = 0;
        while(table[hashSlot] != null)
        {
            probeCount++;
            if (data.findSong(table[hashSlot].getOff() + 3, 
                    table[hashSlot].getLen()).equals(key))
            {
                h = table[hashSlot];
            }
            hashSlot = (hashSlot + (probeCount * probeCount)) % capacity;
        }
        return h;
    }
    /**
     * Inserts a new entry into the hash map and returns
     * whether the slot has an entry
     * @param k the string key of the new entry
     * @param v the handle value of the new entry
     * @return true if the slot is not null
     */
    public boolean insert(String k, Handle v)
    {
        //if we exceed 50% of the hash table used then
        //we double the size of the hash table and reinsert
        //all previous items
        if (size >= (capacity/2))
        {
           expand();
        }
        int hashSlot = hashFunc(k, capacity);
        int probeCount = 0;
        while (table[hashSlot] != null && table[hashSlot].getOff() != -1)
        {
            hashSlot = (hashSlot + (probeCount * probeCount)) % capacity;
            probeCount++;
        }    
        table[hashSlot] = v;
        size++;
        return table[hashSlot] != null;
    }
    /**
     * expands the hashTable
     * puts the values back in
     */
    public void expand()
    {
        Handle[] temp = this.table;
        this.table = new Handle[capacity * 2];
        size = 0;
        this.capacity = capacity * 2;
        for (int i = 0; i < temp.length; i++)
        {
            if (temp[i] != null && temp[i].getOff() != -1)
            {
                insert(data.findSong(temp[i].getOff() + 3, temp[i].getLen()), temp[i]);
            }
        }
    }
    /**
     * Removes items from the hash table if their key matches the key being
     * searched for
     * @param key the string that you're searching for
     * @return the value that was removed
     */
    public Handle remove(String key)
    {
        Handle h = null;
        Handle tombstone = new Handle(-1, -1);

        int hashSlot = hashFunc(key, capacity);
        int probeCount = 0;
        while (table[hashSlot] != null)
        {
            probeCount++;
            if (table[hashSlot].getOff() != -1 && data.findSong(table[hashSlot].getOff() + 3, 
                    table[hashSlot].getLen()).equals(key))
            {
                h = table[hashSlot];
                table[hashSlot] = tombstone;
                size--; 
                break;
            }
            hashSlot = (hashSlot + (probeCount * probeCount)) % capacity;
        }
        return h;
    }

    /**
     * This method prints every non null item in the hash table
     */
    public void print()
    {
        for (int i = 0; i < this.capacity; i++)
        {
            if (table[i] != null && table[i].getOff() != -1)
            {
                System.out.println("|" + data.findSong(table[i].getOff() + 3, table[i].getLen()) + "|"
                        + " " + i); 
            }
        }
    }
    /**
     * checks if the table is empty or not
     * @return boolean true if nothing in table
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    /**
     * This method returns the size of the hash table where size
     * is the number of elements
     * @return size or number of elements in the table
     */
    public int getSize()
    {
        return this.size;
    }
    
}