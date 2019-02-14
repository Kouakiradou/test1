package uk.ac.ucl.bag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapBag<T extends Comparable> extends AbstractBag<T>
{
    private int maxSize;
    private Map<T, Integer> contents;

    public MapBag(int maxSize) throws BagException
    {
        if (maxSize > MAX_SIZE)
        {
            throw new BagException("Attempting to create a Bag with size greater than maximum");
        }
        if (maxSize < 1)
        {
            throw new BagException("Attempting to create a Bag with size less than 1");
        }
        this.maxSize = maxSize;
        this.contents = new HashMap<>();
    }

    public void add(T value) throws BagException
    {
        if (this.contents.containsKey(value))
        {
            this.contents.put(value, this.contents.get(value) + 1);
            return;
        }
        if (this.contents.size() < maxSize)
        {
            this.contents.put(value, 1);
        }
        else
        {
            throw new BagException("Bag is full");
        }
    }

    public void addWithOccurrences(T value, int occurrences) throws BagException
    {
        for (int i = 0 ; i < occurrences ; i++)
        {
            add(value);
        }
    }

    public boolean contains(T value)
    {
        return this.contents.containsKey(value);
    }

    public int countOf(T value)
    {
        return this.contents.get(value);
    }

    public void remove(T value)
    {
        this.contents.put(value, this.contents.get(value) - 1);
        if (this.contents.get(value) == 0)
        {
            this.contents.remove(value);
        }
    }

    public boolean isEmpty()
    {
        return this.contents.size() == 0;
    }

    public int size()
    {
        return contents.size();
    }

    public Iterator<T> iterator()
    {
        return this.contents.keySet().iterator();
    }

    private class MapBagIterator implements Iterator<T>
    {
        Iterator<T> myIterator = contents.keySet().iterator();
        T thisvalue = myIterator.next();
        int thiscount = contents.get(thisvalue);

        public boolean hasNext()
        {
            if (myIterator.hasNext() || thiscount != 0)
            {
                return true;
            }
            else return false;
        }

        public T next()
        {
            if (thiscount == 0)
            {
                thisvalue = myIterator.next();
                thiscount = contents.get(thisvalue) - 1;
                return thisvalue;
            }
            else
            {
                thiscount--;
                return thisvalue;
            }
        }
    }

    public Iterator<T> allOccurrencesIterator()
    {
        return new MapBagIterator();
    }
}
