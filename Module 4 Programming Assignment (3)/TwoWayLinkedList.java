import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayLinkedList<E> implements MyList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    /** Create an empty list */
    public TwoWayLinkedList() {
        head = tail = null;
        size = 0;
    }

    /** Create a list from an array of objects */
    public TwoWayLinkedList(E[] objects) {
        this();
        for (E obj : objects) {
            addLast(obj);
        }
    }

    /** Return the first element in the list */
    public E getFirst() {
        return (size == 0) ? null : head.element;
    }

    /** Return the last element in the list */
    public E getLast() {
        return (size == 0) ? null : tail.element;
    }

    /** Add an element to the beginning of the list */
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.next = head;
        newNode.prev = null;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null) { // List was empty
            tail = newNode;
        }
        size++;
    }

    /** Add an element to the end of the list */
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.next = null;
        newNode.prev = tail;
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) { // List was empty
            head = newNode;
        }
        size++;
    }

    /**
     * Insert an element at the specified index.
     * If index <= 0, inserts at the beginning.
     * If index >= size, inserts at the end.
     */
    @Override
    public void add(int index, E e) {
        if (index <= 0) {
            addFirst(e);
        } else if (index >= size) {
            addLast(e);
        } else {
            Node<E> nextNode = getNode(index);
            Node<E> newNode = new Node<>(e);
            newNode.next = nextNode;
            newNode.prev = nextNode.prev;
            nextNode.prev.next = newNode;
            nextNode.prev = newNode;
            size++;
        }
    }

    /** Remove and return the first element in the list */
    public E removeFirst() {
        if (size == 0) return null;
        E element = head.element;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null; // List is now empty
        }
        size--;
        return element;
    }

    /** Remove and return the last element in the list */
    public E removeLast() {
        if (size == 0) return null;
        if (size == 1) {
            return removeFirst();
        }
        E element = tail.element;
        tail = tail.prev;
        tail.next = null;
        size--;
        return element;
    }

    /**
     * Remove the element at the specified index.
     * Returns the removed element, or null if index is out of bounds.
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) return null;
        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();
        Node<E> node = getNode(index);
        E element = node.element;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return element;
    }

    /** Override toString() to return the content of the list in [a, b, c] style */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while(current != null) {
            sb.append(current.element);
            current = current.next;
            if(current != null)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /** Clear the list */
    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    /** Return true if the list contains element e */
    @Override
    public boolean contains(Object e) {
        Node<E> current = head;
        while(current != null) {
            if(current.element.equals(e))
                return true;
            current = current.next;
        }
        return false;
    }

    /** Return the element at the specified index */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        return getNode(index).element;
    }

    /** Return the index of the first occurrence of the element e, or -1 if not found */
    @Override
    public int indexOf(Object e) {
        Node<E> current = head;
        int index = 0;
        while(current != null) {
            if(current.element.equals(e))
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    /** Return the index of the last occurrence of the element e, or -1 if not found */
    @Override
    public int lastIndexOf(E e) {
        Node<E> current = tail;
        int index = size - 1;
        while(current != null) {
            if(current.element.equals(e))
                return index;
            current = current.prev;
            index--;
        }
        return -1;
    }

    /**
     * Replace the element at the specified index with e.
     * Returns the old element.
     */
    @Override
    public E set(int index, E e) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        Node<E> node = getNode(index);
        E old = node.element;
        node.element = e;
        return old;
    }

    /** Return the number of elements in the list */
    @Override
    public int size() {
        return size;
    }

    /**
     * Helper method to return the node at a given index.
     * Uses forward traversal if index is in the first half,
     * and backward traversal if index is in the second half.
     */
    private Node<E> getNode(int index) {
        if (index < (size >> 1)) {
            Node<E> current = head;
            for (int i = 0; i < index; i++)
                current = current.next;
            return current;
        } else {
            Node<E> current = tail;
            for (int i = size - 1; i > index; i--)
                current = current.prev;
            return current;
        }
    }

    // ---------------- Iterator Methods ----------------

    /**
     * Return an iterator over the elements in this list.
     * This iterator is a ListIterator starting at index 0.
     */
    @Override
    public java.util.Iterator<E> iterator() {
        return listIterator();
    }

    /**
     * Return a ListIterator starting at the beginning of the list.
     */
    public ListIterator<E> listIterator() {
        return new TwoWayLinkedListIterator(0);
    }

    /**
     * Return a ListIterator starting at the specified index.
     */
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        return new TwoWayLinkedListIterator(index);
    }

    // ---------------- Inner Classes ----------------

    /** The Node class for the doubly linked list. */
    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;
        Node(E element) {
            this.element = element;
        }
    }

    /**
     * The ListIterator implementation for TwoWayLinkedList.
     * It provides next, previous, add, remove, and set operations.
     */
    private class TwoWayLinkedListIterator implements ListIterator<E> {
        private Node<E> next;         // The next node to return in a call to next()
        private Node<E> lastReturned; // The last node returned by next() or previous()
        private int nextIndex;        // Index of next element

        public TwoWayLinkedListIterator(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
            next = (index == size) ? null : getNode(index);
            nextIndex = index;
            lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.element;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            if (next == null) { // Iterator is at the end.
                next = tail;
            } else {
                next = next.prev;
            }
            lastReturned = next;
            nextIndex--;
            return lastReturned.element;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();
            Node<E> lastNext = lastReturned.next;
            
            // Unlink lastReturned
            if (lastReturned.prev == null) {
                head = lastNext;
            } else {
                lastReturned.prev.next = lastNext;
            }
            if (lastNext == null) {
                tail = lastReturned.prev;
            } else {
                lastNext.prev = lastReturned.prev;
            }
            // Update iterator state: if lastReturned is next, then set next = lastNext;
            // otherwise, a call to next() has already advanced the index, so adjust nextIndex.
            if (next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            size--;
            lastReturned = null;
        }

        @Override
        public void set(E e) {
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.element = e;
        }

        @Override
        public void add(E e) {
            lastReturned = null;
            if (next == null) {
                // Inserting at the end
                addLast(e);
            } else if (next.prev == null) {
                // Inserting at the beginning
                addFirst(e);
            } else {
                // Inserting into the middle: before next
                Node<E> newNode = new Node<>(e);
                newNode.prev = next.prev;
                newNode.next = next;
                next.prev.next = newNode;
                next.prev = newNode;
                size++;
            }
            nextIndex++;
        }
    }
}