import java.util.ArrayList; 
import java.util.Iterator;
/**
 * a BST of KV pairs specifically
 * @author Purnima Gosh
 * @author taralaughlin
 *@version 12.05.2017
 */
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
    public ArrayList<KVPair> remove(Handle name)
    {
        Iterator<KVPair> itr = this.iterator();
        ArrayList<KVPair> list = new ArrayList<>();
        while (itr.hasNext())
        {
            KVPair temp = itr.next();
            if (temp.getKey().equals(name))
            {
                list.add(temp);
                delete(temp);
            }
        }
        return list;
    }
    /**
     * This method removes items of a certain name from the KVTree
     * @param name the name of what needs to be removed
     * @return true if the items have been removed
     */
    public ArrayList<KVPair> removeAlternate(Handle name)
    {
        Iterator<KVPair> itr = this.iterator();
        ArrayList<KVPair> list = new ArrayList<>();
        while (itr.hasNext())
        {
            KVPair temp = itr.next();
            if (temp.getValue().equals(name))
            {
                list.add(temp);
                delete(temp);
            }
        }
        return list;
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
     * This method counts the number of times a particular handle occurs
     * in the tree
     * @param h the handle being counted
     * @return the number of times the handle appears
     */
    public int countHandles(Handle h)
    {
        int count = 0;
        Iterator<KVPair> itr = this.iterator();

        while (itr.hasNext())
        {
            KVPair curr = itr.next();
            if (curr.getKey() == h)
            {
                count++;
            }
            if (curr.getValue() == h)
            {
                count++;
            }
        }

        return count;
    }
    /**
     * every time handle h appears in the tree
     * as the KEY
     * append the Value handle attached to it
     * @param h handle that you need d
     * @return ArrayList<Handle> of the pairs of ahndles
     */
    public ArrayList<Handle> findHandlePair(Handle h)
    {
        ArrayList<Handle> list = new ArrayList<>();

        Iterator<KVPair> itr = this.iterator();
        while (itr.hasNext())
        {
            KVPair curr = itr.next();
            if (curr.getKey() == h)
            {
                list.add(curr.getValue());
            }
        }

        return list;
    }

    /**
     * This method finds if the given kvpair exists in the tree
     * @param k the key handle
     * @param v the value handle
     * @return returns true if the kvpair is in the tree and false
     * if its not in the tree
     */
    public boolean hasKVPair(Handle k, Handle v)
    {
        Iterator<KVPair> itr = this.iterator();
        while (itr.hasNext())
        {
            KVPair curr = itr.next();
            if (curr.getKey().equals(k) && curr.getValue().equals(v))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * prints the tree
     */
    public void printTree() 
    {
        Iterator<KVPair> itr = this.iterator();

        while (itr.hasNext())
        {
            KVPair k = itr.next();
            for (int i = 0; i < super.getHeight(k); i++)
            {
                System.out.print("  ");
            }
            System.out.println("(" + k.getKey().getOff() + "," + 
                    k.getValue().getOff() + ")");
        }
    }
}
