public class LinkedList<E> {

  private Node<E> mHead;
  private int mSize;

  public int size()
  {
    return mSize;
  }

  public void add(int index, E element)
  {
    if (index < 0 || index > size())
      throw new IndexOutOfBoundsException("Index must be between 0 and " + size());
    if (index == 0)
      mHead = new Node<>(element, mHead);
    else {
      Node<E> current = mHead;
      for (int i = 0; i < index - 1; i++)
        current = current.mNext;

      current.mNext = new Node<>(element, current.mNext);
    }
    mSize++;
  }

  public void add(E element)
  {
    add(mSize, element);
  }

  public void addLast(E element)
  {
    add(element);
  }

  public void addFirst(E element)
  {
    add(0, element);
  }

  public E get(int index)
  {

    Node<E> current = mHead;
    for (int i = 0; i < index; i++) {
      current = current.mNext;
    }
    return current.mData;
  }

  public E set(int index, E element)
  {
    checkIndex(index, 0, mSize-1);
    Node<E> current = mHead;
    for (int i = 0; i < index; i++)
      current = current.mNext;
    E data = (E) current.mData;
    current.mData = element;
    return data;
  }

  private void checkIndex(int index, int min, int max)
  {
    if (index < min || index > max)
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mSize);
  }

  @Override
  public String toString()
  {
    if (mSize == 0)
      return "[]";

    Node<E> current = mHead;
    StringBuilder sb = new StringBuilder("[").append(current.mData);
    while (current.mNext != null)
    {
      current = current.mNext;
      sb.append(", ").append(current.mData);
    }
    return sb.append("]").toString();
  }

  public void clear()
  {
    mHead = null; mSize = 0;
  }

  public int indexOf(Object element)
  {
    Node<E> current = mHead;
    for (int i = 0; i < mSize; i++) {
      if ((element == null && current.mData == null) || (element != null && element.equals(current.mData)))
        return i;
      current = current.mNext;
    }
    return -1;
  }

  public boolean contains(Object element)
  {
    return indexOf(element) != -1;
  }

  public E remove(int index)
  {
    checkIndex(index, 0, mSize-1);
    
    Node<E> current = mHead;
    if (index == 0) {
      mHead = mHead.mNext;
      return current.mData;
    }

    for (int i = 0; i < index-1; i++) 
      current = current.mNext;
    
    E data = current.mNext.mData;
    current.mNext = current.mNext.mNext;
	mSize--;
    return data;
  }

  public boolean remove(E element)
  {
    int index = indexOf(element);
    if (index == -1)
      return false;
    else
    {
      remove(index);
      return true;
    }

  }


  private class Node<E>
  {
    private E mData;
    private Node<E> mNext;

    public Node(E data, Node<E> next)
    {
      mData = data;
      mNext = next;
    }
    public Node(E data)
    {
      this(data, null);
    }

    @Override
    public String toString()
    {
      return "| " + mData + " | " + Integer.toHexString(mNext.hashCode()) + " |";
    }
  }

///Inner Class for the List Iterator

  public class ListIterator
  {
    //3 fields
    private Node<E> mPrevious;
    private Node<E> mNext;
    private int mCursor;

    public ListIterator()
    {
      mCursor = 0;
      mNext = mHead;        //mPrev(null)<------[0]------>mNext(mHead)
      mPrevious = null;
    }

    //hasNext
    public boolean hasNext()
    {
      return (mNext != null);
    }
    //hasPrevious
    public boolean hasPrevious()
    {
      return (mPrevious != null);
    }
    // next
    public E next() // moves and returns
    {
     mCursor++;
     mPrevious = mNext;
     mNext = mNext.mNext;
     return mPrevious.mData;
    }
    // previous
    public E previous()
    {
      mCursor--;
      mNext = mPrevious;
      Node<E> temp = mHead; // cant do mPrev.mPrev, loop from the head to get to the position you want to return
      for(int i = 0; i < mCursor-1; i ++)
      {
        temp = temp.mNext;
      }
        mPrevious = temp;

      return mNext.mData;
    }
    // nextIndex
    public int nextIndex()
    {
      return mCursor+1;
    }
    // prevIndex
    public int previousIndex()
    {
      return mCursor-1;
    }
    // add
    public void add(E element)
    {
      LinkedList.this.add(element);
    }
    // remove
    public void remove()
    {
      LinkedList.this.remove(mCursor); //position of cursor gets removed
    }
    public void set(E element)
    {
      LinkedList.this.set(mCursor, element);
    }




  }

}
