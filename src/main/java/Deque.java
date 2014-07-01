import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    @SuppressWarnings("hiding")
    private class Node<Item> {
        private Item value;
        private Node<Item> prev;
        private Node<Item> next;

        public Node(Item _value, Node<Item> _prev, Node<Item> _next) {
            value = _value;
            prev = _prev;
            next = _next;
        }

        public Node(Item _value) {
            this(_value, null, null);
        }

        public Item getValue() {
            return value;
        }

        public void setValue(Item _value) {
            value = _value;
        }

        public Node<Item> getPrev() {
            return prev;
        }

        public void setPrev(Node<Item> _prev) {
            prev = _prev;
        }

        public Node<Item> getNext() {
            return next;
        }

        public void setNext(Node<Item> _next) {
            next = _next;
        }
    }

    private class dequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public dequeIterator(Node<Item> _current) {
            current = _current;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node<Item> prev = current;
            current = current.next;
            return prev.getValue();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node<Item> head;

    private Node<Item> tail;

    private int sz;

    public Deque() {
        head = tail = null;
        sz = 0;
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public int size() {
        return sz;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            head = tail = new Node<Item>(item);
        } else {
            Node<Item> newNode = new Node<Item>(item, null, head);
            head.prev = newNode;
            head = newNode;
        }
        sz += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            head = tail = new Node<Item>(item);
        } else {
            Node<Item> newNode = new Node<Item>(item, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        sz += 1;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item r = head.getValue();
        if (sz == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        sz -= 1;
        
        return r;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item r = tail.getValue();
        if (sz == 1) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        sz -= 1;
        
        return r;
    }

    public Iterator<Item> iterator() {
        return new dequeIterator(head);
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(5);
        deque.addFirst(4);
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(6);
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(9);
        deque.addLast(10);

        StdOut.println("1 to 10");
        for (Integer i : deque) {
            StdOut.println(i);
        }

        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();

        StdOut.println("3 to 8");
        for (Integer i : deque) {
            StdOut.println(i);
        }
    }

}
