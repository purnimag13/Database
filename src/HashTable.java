
public class HashTable 
{
    Entry[] hashValue;
    
    public HashTable()
    {
        
        
        
    }
    
    
    
    
    
    
    
    private class Entry<T>
    {
        private T key;
        private T value;
        
        public Entry(T k, T v)
        {
            this.key = k;
            this.value = v;
        }
        
        public T getKey() 
        {
            return key;
        }

        public void setKey(T key) 
        {
            this.key = key;
        }

        public T getValue() 
        {
            return value;
        }

        public void setValue(T value) 
        {
            this.value = value;
        }

        
    }
    

}
