// Coursera Algorithms, Part I Assignment 2

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            randQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randQueue.dequeue());
        }
    }

}
