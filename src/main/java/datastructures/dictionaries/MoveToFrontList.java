package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListNode front;

    private class ListNode {
        private Item<K, V> item;
        private ListNode next;

        public ListNode(Item<K, V> item) {
            this.item = item;
            this.next = null;
        }

        public ListNode(Item<K, V> item, ListNode next) {
            this.item = item;
            this.next = next;
        }
    }

    public MoveToFrontList() {
        front = null;
    }

    public MoveToFrontList(Item<K, V> item) {
        if (item == null || item.key == null || item.value == null) {
            return;
        }
        front = new ListNode(item);
        size++;
    }
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V prev = find(key);
        if (prev != null) {
            front.item.value = value;
        } else {
            //Previous mapping not exists, create a new node
            front = new ListNode(new Item(key, value), front);
            size++;
        }
        return prev;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (front == null) {
            return null;
        }
        if (front.item.key.equals(key)) {
            return front.item.value;
        }
        V ret = null;
        ListNode node = front;

        while (node.next != null && !node.next.item.key.equals(key)) {
            node = node.next;
        }

        if (node.next != null ) {
            ret = node.next.item.value;

            //Reset the front node
            ListNode temp = node.next;
            node.next = temp.next;
            temp.next = front;
            front = temp;
        }
        return ret;

    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontListIterator();
    }

    private class MoveToFrontListIterator extends SimpleIterator<Item<K, V>> {
        private ListNode curNode;

        public MoveToFrontListIterator() {
            curNode = MoveToFrontList.this.front;
        }

        public boolean hasNext() {
            return curNode != null;
        }

        public Item<K, V> next() {
            Item<K, V> ret = curNode.item;
            curNode = curNode.next;
            return ret;
        }
    }
}
