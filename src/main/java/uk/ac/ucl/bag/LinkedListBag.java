package uk.ac.ucl.bag;

import java.util.Iterator;

public class LinkedListBag<T extends Comparable> extends AbstractBag<T>
{
    private static class Element<E>
    {
        public E value;
        public int occurrences;
        public Element<E> next;
        public Element(E value, int occurrences, Element<E> next)
        {
            this.value = value;
            this.occurrences = occurrences;
            this.next = next;
        }
    }

    private int maxSize;
    private Element<T> head;

    public LinkedListBag() throws BagException
    {
        this(MAX_SIZE);
    }

    public LinkedListBag(int maxSize) throws BagException
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
        this.head = null;
    }

    public Element<T> getTail()
    {
        Element<T> currentNode = head;
        while(currentNode.next != null)
        {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public Element<T> search(Element<T> node, T value)
    {
        if (node == null)
        {
            //node = new Element<>(value, 1, null);
            return null;
        }
        else if (node.value.compareTo(value) != 0)
        {
           return search(node.next, value);
        }
        else
        {
            return node;
        }
      //  return null;
    }

    public Element<T> searchPrevious(Element<T> node, T value)
    {
        if (node == null)
        {
            //node = new Element<>(value, 1, null);
            return null;
        }
        if (node.next.value.compareTo(value) != 0)
        {
            search(node.next, value);
        }
        else
        {
            return node;
        }
        return null;
    }

    public void add(T value) throws BagException
    {
        Element<T> result = this.search(this.head, value);
        if (result == null && this.size() < maxSize)
        {
            if (head == null)
            {
                head = new Element<>(value, 1, null);
            }
            else
            {
                this.getTail().next = new Element<>(value, 1, null);
            }
        }
        else if (result != null)
        {
            result.occurrences++;
        }
        else if (this.size() >= maxSize)
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
        if (this.search(this.head, value) == null)
        {
            return false;
        }
        else return true;
    }

    public int countOf(T value)
    {
        return this.search(this.head, value).occurrences;
    }

    public void remove(T value)
    {
        if (this.head.value.compareTo(value) == 0)
        {
            this.head = this.head.next;
        }
        else if (this.getTail().value.compareTo(value) == 0)
        {
            this.searchPrevious(this.head, value).next = null;
        }
        else
        {
            Element<T> previousNode = this.searchPrevious(this.head, value);
            previousNode.next = previousNode.next.next;
        }
    }

    public int size()
    {
        Element<T> currentNode = head;
        int count = 0;
        while(currentNode != null)
        {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }

    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    private class LinkedListBagUniqueIterator implements Iterator<T>
    {
        private Element<T> currentnode = head;
        public boolean hasNext()
        {
            if (currentnode != null)
            {
                return true;
            }
            else return false;
        }

        public T next()
        {
            T thisvalue = currentnode.value;
            currentnode = currentnode.next;
            return thisvalue;
        }
    }

    public Iterator<T> iterator()
    {
        return new LinkedListBagUniqueIterator();
    }

    private class LinkedListBagIterator implements Iterator<T>
    {
        private Element<T> currentnode = head;
        int count = currentnode.occurrences;
        public boolean hasNext()
        {
            if (currentnode.next != null || count != 0)
            {
                return true;
            }
            else return false;
        }

        public T next()
        {
            if (count == 0)
            {
                currentnode = currentnode.next;
                count = currentnode.occurrences - 1;
                return currentnode.value;
            }
            else
            {
                count--;
                return currentnode.value;
            }
        }
    }

    public Iterator<T> allOccurrencesIterator()
    {
        return new LinkedListBagIterator();
    }
}
