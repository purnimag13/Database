/**
 * This test class tests the BinarySearchTree class.
 * 
 * @author Purnima Ghosh and Tara Laughlin
 * @version 9.21.2017
 * 
 */
public class BinarySearchTreeTest extends student.TestCase {

    private BinarySearchTree<String> tree;
    private String x;
	private String y; 
	private String z; 

	/**
	 * This sets up the environment for the test. 
	 */
	public void setUp()
	{ 
		x = "Item";
		y = "AnItem";
		z = "ItemThree"; 

		tree = new BinarySearchTree<String>();
		tree.insert(x);
		tree.insert(y);
		tree.insert(z);
	}
	/**
	 * This ensures that make empty completely removes objects from the tree. 
	 */
	public void testMakeEmpty()
	{
		tree.makeEmpty();
		assertTrue(tree.isEmpty());
	}

	/** 
	 * This ensures that isEmpty correctly reports when the tree is empty. 
	 */
	public void testIsEmpty()
	{
		assertFalse(tree.isEmpty());
		tree.makeEmpty();
		assertTrue(tree.isEmpty());
	}

	/**
	 * This method ensures the find method works when the root is null. 
	 */
	public void testFindNull()
	{
		tree.makeEmpty();
		assertNull(tree.find("Item"));
	}

	/**
	 * This test ensures that the find method of 
	 * BinarySearchTree works correctly. 
	 */
	public void testFind()
	{
		assertEquals("AnItem", tree.find(y));
		assertEquals("ItemThree", tree.find(z));
	}

	/**
	 * This test checks whether or not insert is working correctly. 
	 */
	public void testInsert()
	{
		// Insert returns false when it works correctly. 
		assertFalse(tree.insert(x));   
		assertFalse(tree.insert(y));
		assertFalse(tree.insert(z)); 
		assertFalse(tree.insert(x));   
		assertFalse(tree.insert(y));
		assertFalse(tree.insert(z)); 
		tree.makeEmpty();
		assertFalse(tree.insert(x));
	} 
	/**
     * Tests another case of insert
     */
    public void testInsert2()
    {
        BinarySearchTree<String> empty = new BinarySearchTree<>();
        boolean hello = empty.insert(null);
        assertFalse(hello);
    }
	/**
	 * Tests the delete method
	 */
	public void testDelete()
	{   
		tree.delete(y);
		tree.delete(z);
		tree.delete(x);

		assertTrue(tree.isEmpty());

		tree.insert(x);
		tree.insert(x);
		tree.insert(y);
		tree.insert(y);
		tree.insert(z);
		tree.insert(z);
		tree.delete(x);
		tree.delete(x);
		tree.delete(z);
		tree.delete(z);
		tree.delete(y);
		tree.delete(y);

		assertTrue(tree.isEmpty());
	}
	/**
	 * tests another part of delete 
	 */
	public void testDelete1()
	{
		tree.delete(z);
		tree.delete(y);
		tree.delete(x);

		assertTrue(tree.isEmpty());
	}
}
