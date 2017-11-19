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
    private Entry[] table;
    
    private int capacity;
    
    private int size;
    
    public HashTable(int initSize)
    {
        table = new Entry[initSize];
        capacity = initSize;
        size = 0;
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
     * Gets the handle when provided a key value
     * @param key the key being searched for
     * @return the handle stored for that key value
     */
    public Handle get(String key)
    {
        Handle h = null;
        
        for (int i = 0; i < capacity; i++)
        {
            if (table[i].getKey() == key)
            {
                return table[i].getValue();
            }
        }
        
        return h;
    }
    /**
     * Method to perform the quadratic 
     * probing to resolve collision errors
     * @param k the key of the record being inserted
     * @return the slot where the value should be hashed
     */
    public int quadProbing(String k)
    {
        //initial hash slot found by the hashing function
        int hashSlot = hashFunc(k, capacity);
        //number of times probing has occurred
        int probeCount = 0;
        
        while(table[hashSlot] != null)
        {
            probeCount++;
            // h = h(x) + i^2 % hash table size
            hashSlot = (hashSlot + (probeCount * probeCount)) % capacity;
        }

        return hashSlot;
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
        if (size >= (size/capacity))
        {
            Entry[] temp = this.table;
            this.table = new Entry[capacity * 2];
            this.capacity = capacity * 2;
            //idk if this works because its recursive and a for loop
            // but I also don't know how else to reinsert
            for (int i = 0; i < temp.length; i++)
            {
                insert(temp[i].getKey(), temp[i].getValue());
            }
        }
        int slot = quadProbing(k);
        table[slot] = new Entry(k, v);
        size++;
        return table[slot] != null;
    }
    /**
     * The entry class that takes a generic key and generic value
     * @author Purnima Ghosh and Tara Laughlin
     * @version Nov 19, 2017
     *
     * @param <K> the generic key type
     * @param <V> the generic value type
     */
    private class Entry
    {
        private String key;
        private Handle value;
        
        public Entry(String k, Handle v)
        {
            this.key = k;
            this.value = v;
        }
        
        public String getKey() 
        {
            return key;
        }

        public void setKey(String key) 
        {
            this.key = key;
        }

        public Handle getValue() 
        {
            return value;
        }

        public void setValue(Handle value) 
        {
            this.value = value;
        }

        
    }
    

}