import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandIterator implements Iterator<Item> {
        private Object[] arr;

        private int[] randIndex;

        private int count;

        private int size;

        public RandIterator(Object[] _arr, int _size) {
            randIndex = new int[_size];
            for (int i = 0; i < _size; i++) {
                randIndex[i] = i;
            }
            StdRandom.shuffle(randIndex);
            count = 0;
            size = _size;
            arr = _arr;
        }

        public boolean hasNext() {
            return count < size;
        }

        @SuppressWarnings("unchecked")
        public Item next() {
            Item r = (Item) arr[randIndex[count]];
            count++;
            return r;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Object[] arr;

    private int sz;

    public RandomizedQueue() {
        arr = new Object[32];
        sz = 0;
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public int size() {
        return sz;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (sz == arr.length) {
            Object[] old = arr;
            arr = new Object[old.length * 2];
            for (int i = 0; i < old.length; i++) {
                arr[i] = old[i];
            }
        }

        arr[sz++] = item;
    }

    @SuppressWarnings("unchecked")
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(0, sz);

        Item r = (Item) arr[idx];
        arr[idx] = arr[sz - 1];
        sz--;

        return r;
    }

    @SuppressWarnings("unchecked")
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return (Item) arr[StdRandom.uniform(0, sz)];
    }

    public Iterator<Item> iterator() {
        return new RandIterator(arr, sz);
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<Integer>();
        randQueue.enqueue(1);
        randQueue.enqueue(2);
        randQueue.enqueue(3);
        randQueue.enqueue(4);
        randQueue.enqueue(5);
        
        StdOut.println("5 items");
        for (Integer i : randQueue) {
            StdOut.println(i);
        }
        
        randQueue.dequeue();
        randQueue.dequeue();
        
        StdOut.println("3 items");
        for (Integer i : randQueue) {
            StdOut.println(i);
        }
    }
    
}
