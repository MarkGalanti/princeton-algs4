public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String s = StdIn.readString();
        String a[] = s.split(" ");

        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
        for (int i = 0; i < a.length; i++) {
            randQueue.enqueue(a[i]);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randQueue.dequeue());
        }
    }

}
