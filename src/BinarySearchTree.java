import java.util.Iterator;
import java.util.Stack;

/**
 * This is the generic version of the binary search tree. 
 * 
 * @author Purnima Ghosh and Tara Laughlin
 * @version 10/29/2017
 * @param <T> The generic parameter. 
 */
public class BinarySearchTree<T extends Comparable<? super T>>
    implements Iterable<T> {

    private BinaryNode<T> root;

    /**
     * Constructor for the binary search tree. 
     */
    public BinarySearchTree()
    {
        root = null;
    }

    /**
     * Empties the binary search tree.  
     */
    public void makeEmpty()
    {
        root = null;
    }
    /**
     * root
     * @return BinaryNode<T> root
     */
    public BinaryNode<T> getRoot()
    {
        return root;
    }
    /**
     * Checks to see if the tree is empty.   
     * @return Whether or not the tree is empty. 
     */
    public boolean isEmpty()
    {
        return root == null;
    }

    /**
     * Searches for a specific item. 
     * @param x The node being looked for. 
     * @return A generic type that is found by the function. 
     */
    public T find(T x)
    {
        return find( x, root);
    }

    /**
     * Helps find specific item using the element and the root
     * @param x the node being searched for
     * @return an element of a node
     * @param elemRoot
     */
    private T find( T x, BinaryNode<T> elemRoot)
    {
        if (elemRoot == null)
        {
            return null;
        }

        int compareResult = x.compareTo(elemRoot.element);

        if (compareResult < 0)
        {
            return find(x, elemRoot.left);
        }
        else if (compareResult > 0)
        {
            return find(x, elemRoot.right);
        }
        else
        {
            return elemRoot.element;
        }
    }

    /**
     * Function to insert an element into the BST
     * @param x item to be inserted into the tree. 
     * @return If the insert was succesful. 
     */
    public boolean insert(T x)
    {
        root = insert( x, root, 0);
        return (root == null);
    }

    /**
     * Helper function to insert an element into the BST. 
     * @param x Item to insert
     * @param elemRoot Root of the tree
     * @param currDepth Depth of the tree at the current point
     * @return The node that is inserted. 
     */
    private BinaryNode<T> insert(T x, BinaryNode<T> elemRoot, int currDepth)
    {
        if (elemRoot == null) 
        {
            return new BinaryNode<T>(x, null, null, currDepth);
        }

        int compareResult = x.compareTo(elemRoot.element);

        if (compareResult < 0)
        {
            elemRoot.left = insert( x, elemRoot.left, currDepth + 1);
        }
        else if (compareResult >= 0)
        {
            elemRoot.right = insert(x, elemRoot.right, currDepth + 1);
        }
        return elemRoot;
    }

    /**
     * Function to delete an element into the BST
     * @param x Item to be deleted. 
     * @return If the item was deleted. 
     * @throws Exception 
     */
    public boolean delete(T x)
    {
        root = this.delete(x, root);
        return (root == null);
    }

    /**
     * This is the helper method for the insert function. It iteratively
     * moves through the tree in order to find the item that needs to be 
     * deleted. 
     * @param x
     * @param node
     * @return
     */
    private BinaryNode<T> delete(T x, BinaryNode<T> node)
    {
        BinaryNode<T> result = node;
        if (node == null)
        {
            return node;
        }
        if (x.compareTo(node.getElement()) < 0)
        {
            node.setLeft(delete(x, node.getLeft()));
        }
        else if (x.compareTo(node.getElement()) > 0)
        {
            node.setRight(delete(x, node.getRight()));
        }
        else
        {
            if (node.getLeft() != null && node.getRight() != null)
            {
                node.setElement(findMin(node.getRight()).getElement());
                node.setRight(delete(node.getElement(), node.getRight()));
                //result = findMin(node.getRight());
            }
            else if (node.getLeft() != null)
            {
                result = node.getLeft();
            }
            else
            {
                result = node.getRight();
            }
        }
        return result;
    }
    /**
     * This method finds the minimum entry in the BST. 
     * @param node Starting point for the search. 
     * @return The node that will be checked next. 
     */
    private BinaryNode<T> findMin(BinaryNode<T> node)
    {
        if (node.getLeft() == null)
        {
            return node;
        }
        else
        {
            return findMin(node.getLeft());
        }

    }

    /**
     * Private binary node class. 
     * 
     * @author Alexander James Bochel and Purnima Ghosh
     *
     * @param <T> The generic parameter. 
     */
    static class BinaryNode<T>
    {
        private BinaryNode<T> left;
        private BinaryNode<T> right;
        private T element;
        private int depth;

        /**
         * Constructor. 
         * 
         * @param elem Element. 
         * @param rightChild Right node. 
         * @param leftChild Left node. 
         * @param depth depth of the tree. 
         */
        public BinaryNode(T elem, BinaryNode<T> rightChild, 
                BinaryNode<T> leftChild, int depth)
        {
            element = elem;
            right = rightChild;
            left = leftChild;
            depth = 0;
        }

        /**
         * Sets the element in the node. 
         * 
         * @param element2 The element being set in the node. 
         */
        public void setElement(T element2) {
            element = element2;
        }

        /**
         * Gets the element inside of the node. 
         * 
         * @return The element held by the node. 
         */
        public T getElement() {
            return this.element;
        }

        /**
         * Sets the left node. 
         * @param leftNode Node to the left of the root. 
         */
        public void setLeft(BinaryNode<T> leftNode)
        {
            left = leftNode;
        }

        /**
         * Sets the right node. 
         * @param rightNode Node to the right of the root. 
         */
        public void setRight(BinaryNode<T> rightNode)
        {
            right = rightNode;
        }

        /**
         * Gets the left node. 
         * @return Left node. 
         */
        public BinaryNode<T> getLeft()
        {
            return left;
        }

        /**
         * Gets the right node. 
         * @return Right node. 
         */
        public BinaryNode<T> getRight()
        {
            return right;
        }

        /**
         * Finds the depth of the tree. 
         * @return The depth of the tree. 
         */
        @SuppressWarnings("unused")
        public int getDepth()
        {
            return depth;
        }
    }

    /**
     * Gets the height of the tree. 
     * @param x The node
     * @return Height of the tree
     */
    public int getHeight(T x)
    {
        return getNodeHeight(root, x, 0);
    }


    /**
     * This method gets the height of the node. 
     * @param root
     * @param x
     * @param height
     * @return The height of the node. 
     */
    private int getNodeHeight(BinaryNode<T> sRoot, T x, int height)
    {
        if (sRoot == null)
        {
            return 0;
        }
        if (sRoot.element.equals(x))
        {
            return height;
        }

        int level = getNodeHeight(sRoot.left, x, height + 1);

        if (level != 0)
        {
            return level;
        }

        //check if the node is present in the right sub tree
        return getNodeHeight(sRoot.right, x, height + 1);
    }

    @Override
    /**
     * Automatic iterator method. 
     */
    public Iterator<T> iterator() {
        return new BSTIterator(this.root);
    }

    /**
     * Iterator class for traversing the binary search tree. 
     * @author Purnima Ghosh
     * @version 9.21.2017
     *
     */
    private class BSTIterator implements Iterator<T> 
    {

        private Stack<BinaryNode<T>> stack;

        /**
         * This is the constructor for the iterator class. 
         * @param root Starting point of the iterator. 
         */
        public BSTIterator(BinaryNode<T> root) {
            stack = new Stack<BinaryNode<T>>();
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        /**
         * Checks to see whether or not the iterator has a next object . 
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Moves the iterator to the next object. 
         * In-order: left, root, right
         */
        @Override
        public T next() {
            BinaryNode<T> node = stack.pop();
            T result = node.element;
            if (node.right != null) 
            {
                node = node.right;
                while (node != null) 
                {
                    stack.push(node);
                    node = node.left;
                }
            }
            return result;
        }
    }
}
