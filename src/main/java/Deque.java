// Coursera Algorithms, Part I Assignment 2

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 双向队列
 * 
 * 为了保证在O(1)的复杂度内完成双向插入及删除操作，使用双向链表作为内部数据结构
 * @author ericzhang <ericzhang.buaa@gmail.com>
 */
public class Deque<Item> implements Iterable<Item> {

    /**
     * 链表节点
     * 
     * @author ericzhang <ericzhang.buaa@gmail.com>
     */
    @SuppressWarnings("hiding")
    private class Node<Item> {
        private Item value;
        private Node<Item> prev;
        private Node<Item> next;

        public Node(Item value, Node<Item> prev, Node<Item> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public Node(Item value) {
            this(value, null, null);
        }

        public Item getValue() {
            return value;
        }
    }

    /**
     * 迭代器
     * 
     * @author ericzhang <ericzhang.buaa@gmail.com>
     */
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> current) {
            this.current = current;
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

    /**
     * 默认构造函数
     */
    public Deque() {
        head = null;
        tail = null;
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
     * 在队头插入新元素
     * 
     * @param item 新元素
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            head = new Node<Item>(item);
            tail = head;
        } else {
            Node<Item> newNode = new Node<Item>(item, null, head);
            head.prev = newNode;
            head = newNode;
        }
        sz += 1;
    }

    /**
     * 在队尾插入新元素
     * 
     * @param item 新元素
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            head = new Node<Item>(item);
            tail = head;
        } else {
            Node<Item> newNode = new Node<Item>(item, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        sz += 1;
    }

    /**
     * 移除队头元素
     * 
     * @return 队头元素
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item r = head.getValue();
        if (sz == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        sz -= 1;
        
        return r;
    }

    /**
     * 移除队尾元素
     * 
     * @return 队尾元素
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item r = tail.getValue();
        if (sz == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        sz -= 1;
        
        return r;
    }

    /**
     * 返回一个迭代器
     * 
     * @return 迭代器
     */
    public Iterator<Item> iterator() {
        return new DequeIterator(head);
    }

}
