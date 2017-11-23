import java.util.ArrayList;
import java.util.Iterator;

public class KVTree extends BinarySearchTree<KVPair>
{
    /**
     * Constructor for KVTree
     * a binary search tree of type KVPair
     */
    public KVTree()
    {
        super();
    }
    /**
     * Method that takes two handles and creates a KVPair
     * and inserts them into the tree
     * @param k the key handle
     * @param v the value handle
     * @return true if the KVPair was inserted
     */
    public boolean insert(Handle k, Handle v)
    {
        KVPair newKV = new KVPair(k, v);
        return super.insert(newKV);
    }
    /**
     * This method removes items of a certain name from the KVTree
     * @param name the name of what needs to be removed
     * @return true if the items have been removed
     */
    public boolean remove(Handle name)
    {
        Iterator<KVPair> itr = this.iterator();
        boolean removed = false;
        while (itr.hasNext())
        {
            KVPair temp = itr.next();
            if (temp.getKey().equals(name))
            {
                delete(temp);
                removed = true;
            }
        }
        return removed;
    }
    /**
     * This method deletes a particular KVPair from the tree
     * @param k the key handle
     * @param v the value handle
     * @return true if item is deleted
     */
    public boolean delete(Handle k, Handle v)
    {
        Iterator<KVPair> itr = this.iterator();
        while (itr.hasNext())
        {
            KVPair temp = itr.next();
            if (temp.getKey().equals(k) && temp.getValue().equals(v))
            {
                delete(temp);
                return true;
            }
        }
        return false;
    }
    /**
     * This method returns a list of all values associated with the searched
     * key. If the key was an artist this method would search through the tree
     * and add all songs that had that artist as a key to a list and then 
     * return that list
     * @param k the key being searched for
     * @return a list of value handles
     */
    public ArrayList<Handle> list(Handle k)
    {
        Iterator<KVPair> itr = this.iterator();
        ArrayList<Handle> list = new ArrayList<>();
        while (itr.hasNext())
        {
            KVPair temp = itr.next();
            if (temp.getKey().equals(k))
            {
                list.add(temp.getValue());
            }
        }
        return list;
    }
}
