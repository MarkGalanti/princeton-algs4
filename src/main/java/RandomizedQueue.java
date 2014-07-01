import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandIterator implements Iterator<Item> {
        private Object[] arr;

        private int[] randIndex;

        private int count;

        private int size;

        public RandIterator(Object[] arr, int size) {
            randIndex = new int[size];
            for (int i = 0; i < size; i++) {
                randIndex[i] = i;
            }
            StdRandom.shuffle(randIndex);
            count = 0;
            this.size = size;
            this.arr = arr;
        }

        public boolean hasNext() {
            return count < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
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
        arr = new Object[8];
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
            for (int i = 0; i < sz; i++) {
                arr[i] = old[i];
            }
        }

        arr[sz++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(0, sz);

        Item r = (Item) arr[idx];
        arr[idx] = arr[sz - 1];
        sz--;

        if (sz >= 8 && sz <= arr.length / 2) {
            Object[] old = arr;
            arr = new Object[old.length / 2];
            for (int i = 0; i < sz; i++) {
                arr[i] = old[i];
            }
        }

        return r;
    }

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
        randQueue.enqueue(6);
        randQueue.enqueue(7);
        randQueue.enqueue(8);
        randQueue.enqueue(9);
        randQueue.enqueue(10);

        StdOut.println("10 items");
        for (int i : randQueue) {
            StdOut.println(i);
        }

        StdOut.println("del:" + randQueue.dequeue());
        StdOut.println("del:" + randQueue.dequeue());
        StdOut.println("del:" + randQueue.dequeue());
        StdOut.println("del:" + randQueue.dequeue());
        StdOut.println("del:" + randQueue.dequeue());

        StdOut.println("5 items");
        for (int i : randQueue) {
            StdOut.println(i);
        }
    }

}
