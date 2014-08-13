// Coursera Algorithms, Part I Assignment 2

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 随机队列
 *
 * 为了保证在O(1)的复杂度内完成入队及出队操作，使用动态数组作为内部数据结构
 *
 * @author ericzhang <ericzhang.buaa@gmail.com>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /**
     * 迭代器
     *
     * 迭代过程中顺序随机
     *
     * @author ericzhang <ericzhang.buaa@gmail.com>
     */
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

        @SuppressWarnings("unchecked")
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

    /**
     * 默认构造函数
     */
    public RandomizedQueue() {
        arr = new Object[8];
        sz = 0;
    }

    /**
     * 队列是否为空
     *
     * @return 空队列返回true，否则返回false
     */
    public boolean isEmpty() {
        return sz == 0;
    }

    /**
     * 队列元素数量
     *
     * @return 元素数量
     */
    public int size() {
        return sz;
    }

    /**
     * 入队
     *
     * 数据总插入队尾
     *
     * @param item
     *            新元素
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (sz >= arr.length / 4 * 3) {
            Object[] old = arr;
            arr = new Object[old.length * 2];
            for (int i = 0; i < sz; i++) {
                arr[i] = old[i];
            }
        }

        arr[sz++] = item;
    }

    /**
     * 出队
     *
     * 从所有元素中随机选择一个出队
     *
     * @return 出队元素
     */
    @SuppressWarnings("unchecked")
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(0, sz);

        Item r = (Item) arr[idx];
        arr[idx] = arr[sz - 1];
        arr[sz - 1] = null;
        sz--;

        if (sz >= 8 && sz <= arr.length / 4) {
            Object[] old = arr;
            arr = new Object[old.length / 2];
            for (int i = 0; i < sz; i++) {
                arr[i] = old[i];
            }
        }

        return r;
    }

    /**
     * 抽样
     *
     * 随机返回一个队列中的元素，但不删除
     *
     * @return 随机元素
     */
    @SuppressWarnings("unchecked")
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return (Item) arr[StdRandom.uniform(0, sz)];
    }

    /**
     * 返回一个迭代器
     *
     * @return 迭代器
     */
    public Iterator<Item> iterator() {
        return new RandIterator(arr, sz);
    }

}
