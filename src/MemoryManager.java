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

    public Handle add(byte a, byte c, byte[] b)
    {
        boolean check = true;
        massiveByteArr.add(a);
        massiveByteArr.add(c);

        if (massiveByteArr.contains(b[0]))
        {
            int k = 0;
            for (int i = massiveByteArr.indexOf(b[1]); i < b.length; i++)
            {
                if (massiveByteArr.get(i) != b[k])
                {
                    break; 
                }
                else
                {
                    check = true;
                }
                k++;
            }
            if (check)
            {
                Handle newHand = new Handle(massiveByteArr.get(massiveByteArr.indexOf(a)), b.length + 2);
                handleArr.add(newHand);
                for (byte bytes : b)
                {
                    massiveByteArr.add(bytes);
                }
                return newHand;
            }
        }
        else
        {
            Handle newHand = new Handle(massiveByteArr.get(massiveByteArr.indexOf(a)), b.length + 2);
            handleArr.add(newHand);
            for (byte bytes : b)
            {
                massiveByteArr.add(bytes);
            }
            return newHand;
        }
        return this.find(a, c, b);
    }

    public void remove(KVTree art, KVTree song)
    {

    }
    public Handle find(byte a, byte c, byte[] b)
    {
        if (massiveByteArr.contains(b[0]))
        {
            int k = 0;
            for (int i = massiveByteArr.indexOf(b[1]); i < b.length; i++)
            {
                if (massiveByteArr.get(i) != b[k])
                {
                    break; 
                }
                k++;
            }
            Handle newHand = new Handle(massiveByteArr.get(massiveByteArr.indexOf(a)), b.length + 2);
            return handleArr.get(handleArr.indexOf(newHand));
        }
        return null;

    }
    public Handle findHandle(byte[] b)
    {

    }
}
