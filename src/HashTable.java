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
    public Entry[] table;

    private int capacity;

    private int size;


    public HashTable(int initSize)
    {
        table = new Entry[initSize];
        capacity = initSize;
        size = 0;
    } 
    /**
     * getter method for table
     * @return table which is an entry[]
     */
    public Entry[] getTable()
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
        while(table[hashSlot] != null)
        {
            probeCount++;
            if (table[hashSlot].getKey().equals(key))
              {
                  return true;
              }
            hashSlot = (hashSlot + (probeCount * probeCount)) % capacity;
        }
        return false;
    }
    /**
     * Gets the string when provided a handle value
     * @param h the handle being searched for
     * @return the string matched with that handle
     */
    public String getName(Handle h)
    {       
        for (int i = 0; i < capacity; i++)
        {
            if (table[i].getValue() == h)
            {
                return table[i].getKey();
            }
        }
        return "none";
//        
//        
//        Handle h = null;
//        Handle blank = new Handle(-1, -1);
//        Entry tombstone = new Entry("Tombstone", blank);
//        
//        int hashSlot = hashFunc(key, capacity);
//        int probeCount = 0;
//        while (table[hashSlot] != null)
//        {
//            probeCount++;
//            if (table[hashSlot].equals(key))
//            {
//                h = table[hashSlot].getValue();
//                table[hashSlot] = tombstone;
//                size--; 
//            }
//            hashSlot = (hashSlot + (probeCount * probeCount)) % capacity;
//        }
//        return h;
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
            if (table[hashSlot].getKey().equals(key))
              {
                  h = table[hashSlot].getValue();
              }
            hashSlot = (hashSlot + (probeCount * probeCount)) % capacity;
        }
        return h;
    }
    /**
     * Gets the index of a specific key
     * @param key what you want to find the index of
     * @return the index or -1 if the string is not in the table
     */
    public int getIndex(String key)
    {
        for (int i = 0; i < capacity; i++)
        {
            if (table[i].getKey() == key)
            {
                return i;
            }
        }
        return -1;
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
        if (size >= (capacity/2))
        {
            Entry[] temp = this.table;
            this.table = new Entry[capacity * 2];
            this.capacity = capacity * 2;
            //idk if this works because its recursive and a for loop
            // but I also don't know how else to reinsert
            for (int i = 0; i < temp.length; i++)
            {
                if (temp[i] != null)
                {
                    insert(temp[i].getKey(), temp[i].getValue());
                }
            }
        }
        if (get(k) == null || get(k).getOff() == -1)
        {
            int slot = quadProbing(k);
            table[slot] = new Entry(k, v);
            size++;
            return table[slot] != null;
        }        
        return false;
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
        Handle blank = new Handle(-1, -1);
        Entry tombstone = new Entry("Tombstone", blank);
        
        int hashSlot = hashFunc(key, capacity);
        int probeCount = 0;
        while (table[hashSlot] != null)
        {
            probeCount++;
            if (table[hashSlot].getKey().equals(key))
            {
                h = table[hashSlot].getValue();
                table[hashSlot] = tombstone;
                size--; 
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
            if (table[i] != null && !table[i].getKey().equals("Tombstone"))
            {
                System.out.println("|" + this.getTable()[i].getKey() + "|"
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
    /**
     * The entry class that takes a generic key and generic value
     * @author Purnima Ghosh and Tara Laughlin
     * @version Nov 19, 2017
     *
     * @param <K> the generic key type
     * @param <V> the generic value type
     */
    public class Entry
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

        /**
         * @return string to String
         */
        public String suckADick()
        {
            return " |" + key + "| "; 
        }
    }
}